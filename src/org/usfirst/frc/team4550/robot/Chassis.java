package org.usfirst.frc.team4550.robot;

import org.usfirst.frc.team4550.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;

public class Chassis {
	public Encoder _leftEncoder;

	CCTalon _frontLeft;
	CCTalon _frontRight;
	CCTalon _rearLeft;
	CCTalon _rearRight;
	AHRS _gyro;

	private static Chassis _instance;
	//Creates the Chassis
	private Chassis() {
		_frontLeft = new CCTalon(RobotMap.LEFT_FORWARD_PORT,
				RobotMap.LEFT_FORWARD_REVERSE);
		_frontRight = new CCTalon(RobotMap.RIGHT_FORWARD_PORT,
				RobotMap.RIGHT_FORWARD_REVERSE);
		_rearLeft = new CCTalon(RobotMap.LEFT_REAR_PORT,
				RobotMap.LEFT_REAR_REVERSE);
		_rearRight = new CCTalon(RobotMap.RIGHT_REAR_PORT,
				RobotMap.RIGHT_REAR_REVERSE);

		_leftEncoder = new Encoder(RobotMap.ENCODER_A_LEFT,
				RobotMap.ENCODER_B_LEFT);
		try {
			_gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			DriverStation.reportError(
					"Error instantiating navX-MXP:  " + e.getMessage(), true);
		}
	}
	//Drive Method(Drives Robot)
	public void drive(double xAxis, double yAxis) {
		// System.out.println("speedCheck: " + speedCheck(xAxis));
		xAxis += speedCheck(xAxis);
		// System.out.println("speedCheck(Y): " + speedCheck(yAxis));
		yAxis += speedCheck(yAxis);
		_frontLeft.set(OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_frontRight.set(OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));
		_rearLeft.set(OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_rearRight.set(OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));
		// System.out.println("xAxis: "+xAxis);
		// System.out.println("yAxis: "+yAxis);
	}
	//Tank Drive Method(Drives Robot, but better?)
	public void tankDrive(double xAxis, double yAxis) {
		_frontLeft.set(OI.deadBand((yAxis + xAxis), -1.0, 0, 1.0));
		_frontRight.set(OI.deadBand((yAxis - xAxis), -1.0, 0, 1.0));
		_rearLeft.set(OI.deadBand((yAxis + xAxis), -1.0, 0, 1.0));
		_rearRight.set(OI.deadBand((yAxis - xAxis), -1.0, 0, 1.0));

	}
	//Checks speed while turning, Corrects for over 1.0
	public double speedCheck(double Spd) {
		if (Spd > 1.0) {
			return -((Spd - 1.0) * 0.5);
		} else if (Spd < -1.0) {
			return -((Spd + 1.0) * 0.5);
		}
		return 0.0;
	}
	//Drives to a Distance 
	public void driveDist(double distance, double speed) {
		Timer t = new Timer();
		t.start();
		while (_leftEncoder.getDistance() <= distance) {
			if (t.get() < .5) {
				tankDrive(0, speed * (t.get() * 2));
			} else if (distance - _leftEncoder.getDistance() < 100) {
				tankDrive(0, speed / 2);
			} else {
				tankDrive(0, speed);
			}
		}
		tankDrive(0, 0);
	}
	//Gets instance for chassis 
	public static Chassis getInstance() {
		if (_instance == null) {
			_instance = new Chassis();
		}
		return _instance;
	}
	//Checks Encoder, and returns it 
	public double getLeftEncoder() {
		return _leftEncoder.getDistance();
	}
	//Resets encoder value
	public void reset() {
		_leftEncoder.reset();
		_gyro.reset();
	}
	//Checks and returns angle 
	public double getAngle() {
		return _gyro.getAngle();
	}
	//Stops the Robot
	public void stop() {
		tankDrive(0, 0);
	}
	//Turns to a specific angle(For Autonomous)
	public void turnToAngle(double angle, double speed) {
		boolean done = false;
		reset();

		// Default speed is at 0.7
		long maxTime = 1500;// 1.5 seconds
		double time = 0.0;

		// PID constants
		double Kp = 6.0;
		double Ki = 0.0;
		double Kd = 0.0;

		// PID variables
		double moveSpeed = speed / 2;
		double error = 0.0;
		double prevError = 0.0;
		double errorSum = 0.0;

		angle += this.getAngle();

		time = System.currentTimeMillis();

		// PID loop
		while (!done) {
			prevError = error;
			// System.out.println( " GYRO: " + this.getAngle() );
			error = (angle - this.getAngle()) / 100.0;
			errorSum += error;
			errorSum = OI.normalize(errorSum, -5, 0, 5);

			// System.out.println( "error: " + error + " errorSum: " + errorSum
			// );

			double p = error * Kp;
			double i = errorSum * Ki;
			double d = (error - prevError) * Kd;

			this.turn(OI.normalize(-1 * (p + i + d), -moveSpeed, 0, moveSpeed));

			if ((Math.abs(errorSum) < 0.01)
					|| (System.currentTimeMillis() > time + maxTime)) {
				// System.out.println("Time" + System.currentTimeMillis() );
				System.out.println("Angle " + this.getAngle());
				done = true;
				break;
			}
		}
		System.out.println("Angle turnt to: " + this.getAngle());
		this.stop();
		done = false;
	}
	//Turns the wheels(for teleop)
	public void turn(double speed) {
		_frontLeft.set(-speed);
		_frontRight.set(speed);
		_rearLeft.set(-speed);
		_rearRight.set(speed);
	}
}
