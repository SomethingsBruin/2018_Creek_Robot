package org.usfirst.frc.team4550.robot;

import org.usfirst.frc.team4550.robot.RobotMap;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Chassis {
	public Encoder _leftEncoder;
	public Encoder _rightEncoder;
	
	//	CCTalon _frontLeft;
	//	CCTalon _frontRight;
	//	CCTalon _rearLeft;
	//	CCTalon _rearRight;

	TalonSRX _frontLeft;
	TalonSRX _frontRight;
	TalonSRX _rearLeft;
	TalonSRX _rearRight;

	AHRS _gyro;
	AnalogOutput _ultra;

	private static Chassis _instance;
	//Creates the Chassis
	private Chassis() {
		_frontLeft = new TalonSRX(RobotMap.LEFT_FORWARD_PORT);
		_frontRight = new TalonSRX(RobotMap.RIGHT_FORWARD_PORT);
		_rearLeft = new TalonSRX(RobotMap.LEFT_REAR_PORT);
		_rearRight = new TalonSRX(RobotMap.RIGHT_REAR_PORT);

		_frontLeft.setInverted(RobotMap.LEFT_FORWARD_REVERSE);
		_frontRight.setInverted(RobotMap.RIGHT_FORWARD_REVERSE);
		_rearLeft.setInverted(RobotMap.LEFT_REAR_REVERSE);
		_rearRight.setInverted(RobotMap.RIGHT_REAR_REVERSE);


		_frontLeft.setNeutralMode(NeutralMode.Brake);
		_frontRight.setNeutralMode(NeutralMode.Brake);
		_rearLeft.setNeutralMode(NeutralMode.Brake);
		_rearRight.setNeutralMode(NeutralMode.Brake);

		_leftEncoder = new Encoder(RobotMap.ENCODER_A_LEFT,
				RobotMap.ENCODER_B_LEFT);
		_rightEncoder = new Encoder(RobotMap.ENCODER_A_RIGHT, RobotMap.ENCODER_B_RIGHT);
		//		_ultra  = new AnalogOutput(RobotMap.ULTRA_PORT);

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
		_frontLeft.set(ControlMode.PercentOutput, OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_frontRight.set(ControlMode.PercentOutput, OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));
		_rearLeft.set(ControlMode.PercentOutput, OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_rearRight.set(ControlMode.PercentOutput, OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));
		// System.out.println("xAxis: "+xAxis);
		// System.out.println("yAxis: "+yAxis);
	}
	//Tank Drive Method(Drives Robot, but better?)
	public void tankDrive(double xAxis, double yAxis) {
		_frontLeft.set(ControlMode.PercentOutput, OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_frontRight.set(ControlMode.PercentOutput, OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));
		_rearLeft.set(ControlMode.PercentOutput, OI.normalize((yAxis + xAxis), -1.0, 0, 1.0));
		_rearRight.set(ControlMode.PercentOutput, OI.normalize((yAxis - xAxis), -1.0, 0, 1.0));

	}
	//Sets intake Spd
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
		reset();
		Timer t = new Timer();
		t.start();
		while ( ( _leftEncoder.getDistance() + _rightEncoder.getDistance())/2 <= distance && t.get() < 15) {
			if (t.get() < .5) {
				tankDrive(0, speed * (t.get() * 2));
			} else if (distance - _leftEncoder.getDistance() < 100) {
				tankDrive(0, speed / 2);
			} else {
				tankDrive(0, speed);
			}
//			System.out.println("Encoder: " + (( _leftEncoder.getDistance() + _rightEncoder.getDistance())/2));
		}
		tankDrive(0, 0);
	}
	
	public void driveDist(double distance, double speed, Elevator elevator) {
		reset();
		Timer t = new Timer();
		t.start();
		while ( ( _leftEncoder.getDistance() + _rightEncoder.getDistance())/2 <= distance && t.get() < 15) {
			if (t.get() < .5) {
				tankDrive(0, speed * (t.get() * 2));
			} else if (distance - _leftEncoder.getDistance() < 100) {
				tankDrive(0, speed / 2);
			} else {
				tankDrive(0, speed);
			}
			
			if(t.get() < 2.75) {
				elevator.setElevator(0.8);
			}else {
				elevator.setElevator(0.0);
			}
			
//			System.out.println("Encoder: " + (( _leftEncoder.getDistance() + _rightEncoder.getDistance())/2));
		}
		tankDrive(0, 0);
		while(t.get() < 2.75) {
			elevator.setElevator(0.8);
		}
		elevator.setElevator(0.0);
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
	
	
	//URGENT REMINDER: RIGHT ENCODER RETURNS NEGATIVE VALUES ON COMP ROBOT - CHANGE BEFORE COMP
	public double getRightEncoder() {
		return _rightEncoder.getDistance();
	}
	//Resets encoder value
	public void reset() {
		_leftEncoder.reset();
		_rightEncoder.reset();
		_gyro.reset();
	}
	//Checks and returns angle 
	public double getAngle() {
		return _gyro.getAngle();
	}

	//	public double getUltraValue() {
	//		return _ultra.getVoltage();
	//	} 

	//	public double returnDistance() {
	//		_ultra.
	//	}

	//Stops the Robot
	public void stop() {
		tankDrive(0, 0);
	}


	//Turns to a specific angle(For Autonomous)
	public void turnToAngle(double angle, double speed) {
		boolean done = false;
		reset();

		// Default speed is at 0.7
		long maxTime = 1600;// 1.5 seconds
		double time = 0.0;
		double Kp = 0.0;
		double Ki = 0.0;
		double Kd = 0.0;
		
		if(angle > 0) {
			// PID constants
			Kp = 3.2; //3.2 //2.8
			Ki = 0.0;
			Kd = 0.8; //0.8
		}else {
			Kp = .69;//2.4;
			Ki = 0.0;//0.0;
			Kd = 0.7;//.0.7;
		}
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

			if ((Math.abs(errorSum) < 0.5)
					|| (System.currentTimeMillis() > time + maxTime)) {
				// System.out.println("Time" + System.currentTimeMillis() );
//				System.out.println("Angle " + this.getAngle());
				done = true;
				break;
			}
		}
		Timer.delay(1);
		
		System.out.println("Angle turnt to: " + this.getAngle());
		System.out.println("Left Encoder value: " + this.getLeftEncoder());
		System.out.println("Right Encoder value: " + this.getRightEncoder());
		System.out.println("Avg: " + (this.getLeftEncoder() - this.getRightEncoder())/2 );
		this.stop();
		done = false;
	}
	
//	public void turnToEncoder(double average, double speed) {
//		boolean done = false;
//		reset();
//
//		// Default speed is at 0.7
//		long maxTime = 1500;// 1.5 seconds
//		double time = 0.0;
//		
//		double Kp = 0.0;
//		double Ki = 0.0;
//		double Kd = 0.0;
//		
//		if(speed > 0) {
//			// PID constants
//			Kp = 1.1;
//			Ki = 0.0;
//			Kd = 0.0;
//		}else {
//			Kp = 6.0;
//			Ki = 0.0;
//			Kd = 0.0;
//		}
//		// PID variables
//		double moveSpeed = speed / 2;
//		double error = 0.0;
//		double prevError = 0.0;
//		double errorSum = 0.0;
//
//		average += (this.getLeftEncoder() - this.getRightEncoder())/2;
//
//		time = System.currentTimeMillis();
//
//		// PID loop
//		while (!done) {
//			prevError = error;
//			// System.out.println( " GYRO: " + this.getAngle() );
//			error = (average - ((this.getLeftEncoder() - this.getRightEncoder())/2) )/ 100.0;
//			errorSum += error;
//			errorSum = OI.normalize(errorSum, -200, 0, 200);
//
//			 System.out.println( "error: " + error + " errorSum: " + errorSum );
//
//			double p = error * Kp;
//			double i = errorSum * Ki;
//			double d = (error - prevError) * Kd;
//
//			this.turn(OI.normalize(-1 * (p + i + d), -moveSpeed, 0, moveSpeed));
//
//			if ((Math.abs(errorSum) < 2.0)
//					|| (System.currentTimeMillis() > time + maxTime)) {
//				// System.out.println("Time" + System.currentTimeMillis() );
////				System.out.println("Angle " + this.getAngle());
//				done = true;
//				break;
//			}
//		}
//		System.out.println("Angle turnt to: " + this.getAngle());
//		System.out.println("Left Encoder value: " + this.getLeftEncoder());
//		System.out.println("Right Encoder value: " + this.getRightEncoder());
//		System.out.println("Avg: " + (this.getLeftEncoder() - this.getRightEncoder())/2 );
//		this.stop();
//		done = false;
//	}
	
	//Turns the wheels(for teleop)
	public void turn(double speed) {
		_frontLeft.set(ControlMode.PercentOutput, OI.normalize(-speed,-1,0,1));
		_frontRight.set(ControlMode.PercentOutput, OI.normalize(-speed,-1,0,1));
		_rearLeft.set(ControlMode.PercentOutput, OI.normalize(-speed,-1,0,1));
		_rearRight.set(ControlMode.PercentOutput, OI.normalize(-speed,-1,0,1));
	}
}
