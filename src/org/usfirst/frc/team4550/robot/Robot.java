package org.usfirst.frc.team4550.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
// Class

public class Robot extends IterativeRobot {

	boolean autoRun = false;
	public String switLoc;
	final String defaultAuto = "Default"; // should capitalize
	final String FORWARD = "Forward Cross";
	final String LEFT = "Left Position";
	final String RIGHT = "Right Postion";
	final String CENTER = "Center Postition";
	final String EXCHANGE_CENTER = "Exchange Center";
	final String EXCHANGE_LEFT = "Exchange Left";
	final String LEFT_SCALE_PRIO = "Left Scale - Priority";
	final String RIGHT_SCALE_PRIO = "Right Scale - Priority";
	final String LEFT_SCALE_ONLY = "Left Scale Only";
	final String RIGHT_SCALE_ONLY = "Right Scale Only";

	String autoSelected;
	private SendableChooser<String> _chooser;

	Chassis _chassis;
	OI _oi;
	Elevator _elevator;
	Intake _intake;
	Climber _climber;

	UsbCamera _camera1;
	UsbCamera _camera2;
	CameraServer server;

	double spdMult = 1.0;

	Timer time = new Timer();
	//Methods and Constructors start here kids
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	// Runs at Startup
	public void robotInit() {
		_chooser = new SendableChooser<String>();
		_chooser.addDefault("Default Auto", defaultAuto);
		_chooser.addObject("Center", CENTER);
		_chooser.addObject("Left", LEFT);
		_chooser.addObject("Right", RIGHT);
		_chooser.addObject("Forward", FORWARD);
		_chooser.addObject("Exchange Center", EXCHANGE_CENTER);
		_chooser.addObject("Exchange Left", EXCHANGE_LEFT);
		_chooser.addObject("Left - Scale prio", LEFT_SCALE_PRIO);
		_chooser.addObject("Right - Scale prio", RIGHT_SCALE_PRIO);
		_chooser.addObject("Left - Scale ONLY", LEFT_SCALE_ONLY);
		_chooser.addObject("Right - Scale ONLY", RIGHT_SCALE_ONLY);
		SmartDashboard.putData("Auto choices", _chooser);

		_chassis = Chassis.getInstance();
		_oi = new OI();
		_elevator = new Elevator();
		_intake = new Intake();
		_climber = new Climber();

		_chassis.reset();
		_climber.reset();
		server = CameraServer.getInstance();
		// CameraServer.getInstance().startAutomaticCapture();
		// CameraServer.getInstance().startAutomaticCapture(1);
		_camera1 = new UsbCamera("cam0", 0);
		_camera1.setBrightness(20);
		_camera1.setFPS(15);
		_camera2 = new UsbCamera("cam1", 1);
		_camera2.setBrightness(20);
		_camera2.setFPS(15);

		server.startAutomaticCapture(_camera1);
		server.startAutomaticCapture(_camera2);

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	// Initializes Autonomous

	public void autonomousInit() {
		switLoc = DriverStation.getInstance().getGameSpecificMessage();
		autoSelected = _chooser.getSelected();
		autoRun = true;
		_chassis.reset();
		_climber.reset();
		// System.out.println("Auto selected: " + autoSelected);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	// Runs Autonomous
	public void autonomousPeriodic() {
		double drvSpd = 0.4;
		double trnSpd = 0.8;
		double delay = 0;
		if (autoRun) {
			switch (autoSelected) {
			case LEFT:
				if (switLoc.charAt(0) == 'L') {
					System.out.println("Left Left");
					_chassis.driveDistNonTrapezoidal(300, drvSpd + .1);
					Timer.delay(.5);
					_chassis.driveDist(1100, drvSpd);
					Timer.delay(.2);
					_chassis.turnDist(175, trnSpd / 2);
					_chassis.driveDist(200, drvSpd, _elevator, 2.0);
					// _chassis.driveDist(50, drvSpd);

					_intake.setIntake(-.3, -.3);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					_chassis.driveDistNonTrapezoidal(100, -drvSpd);
					_chassis.turnDist(165, -trnSpd / 2);
					_elevator.moveTime(-0.8, 1.5);
				} else {
					// Left side - change for later
					System.out.println("Left Right");
					if (switLoc.charAt(1) == 'L') {
						_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
						Timer.delay(0.5);
						_chassis.driveDist(2150, drvSpd, _elevator, 4.500);
						Timer.delay(0.2);
						_chassis.turnDist(145, trnSpd / 2);
						_chassis.driveDistNonTrapezoidal(50, 0.2);

						_intake.setIntake(-.35, -.35);
						Timer.delay(1.5);
						_intake.setIntake(0, 0);
						_chassis.turnDist(190, trnSpd / 2);
						_elevator.moveTime(-0.8, 3.5);
					} else {
						_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
						Timer.delay(.5);
						_chassis.driveDist(1200, drvSpd);
					}
				}
				break;
			case RIGHT:
				if (switLoc.charAt(0) == 'L') {
					System.out.println("Right Left");
					if (switLoc.charAt(1) == 'R') {
						_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
						Timer.delay(0.5);
						_chassis.driveDist(2150, drvSpd, _elevator, 4.50);
						Timer.delay(0.2);
						_chassis.turnDist(165, -trnSpd / 2);
						_chassis.driveDistNonTrapezoidal(50, 0.2);

						_intake.setIntake(-.35, -.35);
						Timer.delay(1.5);
						_intake.setIntake(0, 0);
						_chassis.turnDist(190, -trnSpd / 2);
						_elevator.moveTime(-0.8, 3.5);
					} else {
						_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
						Timer.delay(.5);
						_chassis.driveDist(1200, drvSpd);
					}
				} else {
					_chassis.driveDistNonTrapezoidal(300, drvSpd + 0.1);
					Timer.delay(1.0);
					_chassis.driveDist(1100, drvSpd, _elevator, 2.00);
					Timer.delay(.2);
					_chassis.turnDist(165, -trnSpd / 2);
					_chassis.driveDist(100, drvSpd);
					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					_chassis.driveDistNonTrapezoidal(100, -drvSpd);
					_chassis.turnDist(165, trnSpd / 2);
					_elevator.moveTime(-0.8, 1.5);

				}
				break;
			case CENTER:
				if (switLoc.charAt(0) == 'L') {
					System.out.println("Center Left");
					_chassis.driveDist(400, drvSpd);
					_chassis.turnDist(165, -trnSpd / 2);

					_chassis.driveDistNonTrapezoidal(550, drvSpd);

					_chassis.turnDist(185, trnSpd / 2);
					_elevator.moveTime(0.8, .5);
					Timer.delay(0.2);
					_chassis.driveDist(400, drvSpd, _elevator, 1.5);

					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);

				} else {
					System.out.println("Center Right");
					_chassis.driveDist(400, drvSpd);

					_chassis.turnDist(200, trnSpd / 2);

					_chassis.driveDistNonTrapezoidal(375, drvSpd);

					_chassis.turnDist(165, -trnSpd / 2);
					Timer.delay(.2);
					_elevator.moveTime(0.8, .5);
					_chassis.driveDist(450, drvSpd, _elevator, 1.5);

					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);

				}
				break;
			case FORWARD:
				System.out.println("Forward Cross");
				_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
				Timer.delay(1.0);
				_chassis.driveDist(1200, drvSpd);
				break;
			case EXCHANGE_CENTER:
				System.out.println("Exchange Center");
				_chassis.driveDistNonTrapezoidal(100, drvSpd);
				_chassis.turnDist(165, -trnSpd / 2);
				_chassis.driveDist(170, drvSpd);
				_chassis.turnDist(165, -trnSpd / 2);
				_chassis.driveDist(100, drvSpd);
				_intake.setIntake(-.25, -.25);
				Timer.delay(1.0);
				_intake.setIntake(0, 0);
				_chassis.turnDist(50, -trnSpd / 2);
				_chassis.driveDistNonTrapezoidal(585, -drvSpd);

				break;
			case EXCHANGE_LEFT:
				_chassis.driveDistNonTrapezoidal(100, drvSpd);
				_chassis.turnDist(180, trnSpd / 2);
				_chassis.driveDist(220, drvSpd);
			
				_chassis.turnDist(180, trnSpd / 2);
				_chassis.driveDist(100, drvSpd);
				_intake.setIntake(-1, -1);
				Timer.delay(1.0);
				_intake.setIntake(0, 0);
				_chassis.turnDist(35, -trnSpd / 2);
				_chassis.driveDistNonTrapezoidal(800, -drvSpd);
				break;
			case LEFT_SCALE_PRIO:
				if (switLoc.charAt(1) == 'L') {
					_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
					Timer.delay(0.5);
					_chassis.driveDist(2150, drvSpd, _elevator, 4.50);
					Timer.delay(0.2);
					_chassis.turnDist(145, trnSpd / 2);
					_chassis.driveDistNonTrapezoidal(50, 0.2);

					_intake.setIntake(-.35, -.35);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					Timer.delay(.2);
					_chassis.turnDist(190, trnSpd / 2);
					// _chassis.driveDistNonTrapezoidal(100, -0.3);
					_elevator.moveTime(-0.8, 3.5);
				} else {
					// Left side - change for later
					if (switLoc.charAt(0) == 'L') {
						System.out.println("Left Left");
						_chassis.driveDistNonTrapezoidal(300, drvSpd + .1);
						Timer.delay(.5);
						_chassis.driveDist(1100, drvSpd);
						Timer.delay(.2);
						_chassis.turnDist(175, trnSpd / 2);
						_chassis.driveDist(200, drvSpd, _elevator, 2.0);
						// _chassis.driveDist(50, drvSpd);

						_intake.setIntake(-.3, -.3);
						Timer.delay(1.5);
						_intake.setIntake(0, 0);
						_chassis.driveDistNonTrapezoidal(100, -drvSpd);
						_chassis.turnDist(165, -trnSpd / 2);
						_elevator.moveTime(-0.8, 1.5);
					} else {
						_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
						Timer.delay(.5);
						_chassis.driveDist(1200, drvSpd);
					}
				}
				break;
			case RIGHT_SCALE_PRIO:
				if (switLoc.charAt(1) == 'R') {
					_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
					Timer.delay(1.0);
					_chassis.driveDist(2150, drvSpd, _elevator, 4.5);
					Timer.delay(0.2);
					_chassis.turnDist(145, -trnSpd / 2);
					_chassis.driveDistNonTrapezoidal(50, 0.2);

					_intake.setIntake(-.35, -.35);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					_chassis.turnDist(190, -trnSpd / 2);
					_elevator.moveTime(-.8, 3.5);

				} else if (switLoc.charAt(0) == 'R') {
					_chassis.driveDistNonTrapezoidal(300, drvSpd + 0.1);
					Timer.delay(1.0);
					_chassis.driveDist(1100, drvSpd, _elevator, 2.00);
					Timer.delay(.2);
					_chassis.turnDist(165, -trnSpd / 2);
					_chassis.driveDist(100, drvSpd);
					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					_chassis.driveDistNonTrapezoidal(100, -drvSpd);
					_chassis.turnDist(165, trnSpd / 2);
					_elevator.moveTime(-0.8, 1.5);
				} else {
					_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
					Timer.delay(.5);
					_chassis.driveDist(1200, drvSpd);
				}
				break;
			case LEFT_SCALE_ONLY:
				if (switLoc.charAt(1) == 'L') {
					_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
					Timer.delay(0.5);
					_chassis.driveDist(2150, drvSpd, _elevator, 4.50);
					Timer.delay(0.2);
					_chassis.turnDist(145, trnSpd / 2);
					_chassis.driveDistNonTrapezoidal(50, 0.2);

					_intake.setIntake(-.35, -.35);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					Timer.delay(.2);
					_chassis.turnDist(190, trnSpd / 2);
					_elevator.moveTime(-0.8, 3.5);
				} else {
					_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
					Timer.delay(.5);
					_chassis.driveDist(1200, drvSpd);
				}
				break;
			case RIGHT_SCALE_ONLY:
				if (switLoc.charAt(1) == 'R') {
					_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
					Timer.delay(1.0);
					_chassis.driveDist(2150, drvSpd, _elevator, 4.5);
					Timer.delay(0.2);
					_chassis.turnDist(145, -trnSpd / 2);
					_chassis.driveDistNonTrapezoidal(50, 0.2);

					_intake.setIntake(-.35, -.35);
					Timer.delay(1.5);
					_intake.setIntake(0, 0);
					_chassis.turnDist(190, -trnSpd / 2);
					_elevator.moveTime(-.8, 3.5);
				} else {
					_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
					Timer.delay(.5);
					_chassis.driveDist(1200, drvSpd);
				}
				break;
			case defaultAuto:
				break;
			}
		}
		autoRun = false;
	}

	public void teleopInit() {
		_chassis.reset();
		
		time.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override

	// Runs Teleop
	public void teleopPeriodic() {
		int modifier = 1;
		if (time.get() >= 30)
		{
			modifier = 0;
		}
		
			_chassis.tankDrive(OI.normalize(Math.pow(_oi.getRJoystickXAxis(), 3), -1.0, 0, 1.0) * 0.25 * modifier,
					OI.normalize(Math.pow(_oi.getLJoystickYAxis(), 3), -1.0, 0, 1.0) * 0.25 * modifier);
			if (!_elevator.getLimit()) {
				System.out.println("Pressed");
			}

			// if(_oi.getXButton()) {
			// spdMult = 0.6;
			// }else if(_oi.getTriangleButton()) {
			// spdMult = 1.0;
			// }
			// This Allows for the chassis driver to slow down if necessary

			// System.out.println("Angle: " + _chassis.getAngle());
			// System.out.println("Right Encoder: " + _chassis.getRightEncoder());

			if (_oi.getXButton()) {
				_intake.setIntake(0.4 * modifier, 0.65 * modifier);
			} else if (_oi.getTriangleButton()) {
				_intake.setIntake(-0.25 * modifier, -0.25 * modifier);
			} else {
				_intake.setIntake(0, 0);
			}

			if (_oi.getR2() > 0.05 && _elevator.getLimit()) {
				_elevator.setElevator(Math.pow(_oi.normalize(_oi.getR2(), -1, 0, 1), 3) * modifier);
			} else if (_oi.getL2() > 0.05) {
				_elevator.setElevator(Math.pow(_oi.normalize(-1.0 * _oi.getL2(), -1, 0, 1), 3) * modifier);
			} else {;;
				_elevator.setElevator(0);
			}

			if (_oi.getR1C2()) {
				_climber.setClimber(1.0 * modifier);
			} else if (_oi.getL1C2()) {
				_climber.setClimber(-1.0 * modifier);
			} else {
				_climber.setClimber(0.0 * modifier);
			}
		}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
