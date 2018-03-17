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
//Class

public class Robot extends IterativeRobot {



	boolean autoRun = false;
	public String switLoc;
	final String defaultAuto = "Default"; //should capitalize
	final String FORWARD = "Forward Cross";
	final String LEFT = "Left Position";
	final String RIGHT = "Right Postion";
	final String CENTER = "Center Postition";

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

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	//Runs at Startup	
	public void robotInit() {
		_chooser = new SendableChooser<String>();
		_chooser.addDefault("Default Auto", defaultAuto);
		_chooser.addObject("Center", CENTER);
		_chooser.addObject("Left", LEFT);
		_chooser.addObject("Right", RIGHT);
		_chooser.addObject("Forward",FORWARD);
		SmartDashboard.putData("Auto choices", _chooser);

		_chassis = Chassis.getInstance();
		_oi = new OI();
		_elevator = new Elevator();
		_intake = new Intake();
		_climber = new Climber();

		_chassis.reset();
		_climber.reset();
		server = CameraServer.getInstance();
//		CameraServer.getInstance().startAutomaticCapture();
//		CameraServer.getInstance().startAutomaticCapture(1);
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
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	//Initializes Autonomous

	public void autonomousInit() {
		switLoc = DriverStation.getInstance().getGameSpecificMessage();
		autoSelected = _chooser.getSelected();
		autoRun = true;
		_chassis.reset();
		_climber.reset();
		//		System.out.println("Auto selected: " + autoSelected);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	//Runs Autonomous
	public void autonomousPeriodic() {
		double drvSpd = 0.4;
		double trnSpd = 0.8;
		double delay = 0;
		if(autoRun){
			switch (autoSelected) {
			case LEFT:
				if(switLoc.charAt(0) == 'L'){
					System.out.println("Left Left");
					_chassis.driveDistNonTrapezoidal(100, drvSpd+.1);
					Timer.delay(.5);
					_chassis.driveDist(1300, drvSpd);
					Timer.delay(.2);
					_chassis.turnToAngle(85, trnSpd);
					_chassis.driveDist(200, drvSpd, _elevator, 2.0);
					//_chassis.driveDist(50, drvSpd);

					_intake.setIntake(-.3, -.3);
					Timer.delay(1.5);
					_intake.setIntake( 0, 0);	
				} else {
					//Left side - change for later
					System.out.println("Left Right");
					if(switLoc.charAt(1) == 'L') {
						_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
						Timer.delay(0.5);
						_chassis.driveDist(2300, drvSpd, _elevator, 5.0);
						Timer.delay(0.2);
						_chassis.turnToAngle(90, trnSpd-0.1);
						//						_chassis.driveDist(150, drvSpd, _elevator, 2.0);

						_intake.setIntake(-.3, -.3);
						Timer.delay(1.5);
						_intake.setIntake( 0, 0);	
					}else {
						_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
						Timer.delay(.5);
						_chassis.driveDist(1200, drvSpd);
					}
				}
				break;
			case RIGHT:
				if(switLoc.charAt(0) == 'L'){
					System.out.println("Right Left");
					if(switLoc.charAt(1) == 'R') {
						_chassis.driveDistNonTrapezoidal(400, drvSpd + 0.1);
						Timer.delay(0.5);
						_chassis.driveDist(2300, drvSpd, _elevator, 5.00);
						Timer.delay(0.3);
						_chassis.turnToAngle(-85.0, trnSpd-0.1);


						_intake.setIntake(-.3, -.3);
						Timer.delay(1.5);
						_intake.setIntake( 0, 0);
					}else {
						_chassis.driveDist(1400, drvSpd);
					}
				} else {
					_chassis.driveDistNonTrapezoidal(100, drvSpd+ 0.1);
					Timer.delay(1.0);
					_chassis.driveDist(1300, drvSpd, _elevator, 2.00);
					Timer.delay(.2);
					_chassis.turnToAngle(-90.0, trnSpd);
					_chassis.driveDist(200, drvSpd);
					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake( 0, 0);
				}
				break;
			case CENTER:
				if(switLoc.charAt(0) == 'L'){
					System.out.println("Center Left");
					_chassis.driveDist(500, drvSpd);
					Timer.delay(0.2);
					_chassis.turnToAngle(-90.0, trnSpd);
					_chassis.driveDist(575, drvSpd);
					Timer.delay(0.2);
					_chassis.turnToAngle(90, trnSpd);

					_chassis.driveDist(350, drvSpd, _elevator, 1.75);
					Timer.delay(0.25);
					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake( 0, 0);

				} else {
					System.out.println("Center Right");
					_chassis.driveDist(500, drvSpd);
					Timer.delay(.2);
					_chassis.turnToAngle(90, trnSpd);
					_chassis.driveDist(450, drvSpd);
					Timer.delay(.2);
					_chassis.turnToAngle(-90, trnSpd);
					_chassis.driveDist(350, drvSpd, _elevator, 1.75);
					Timer.delay(.2);
					_intake.setIntake(-.25, -.25);
					Timer.delay(1.5);
					_intake.setIntake( 0, 0);

				}
				break;
			case FORWARD:
				System.out.println("Forward Cross");
				_chassis.driveDistNonTrapezoidal(200, drvSpd + 0.1);
				Timer.delay(1.0);
				_chassis.driveDist(1200, drvSpd);
				break;
			case defaultAuto:
								_chassis.turnToAngle(-85, trnSpd - 0.1);
//								_chassis.turnToAngle(-90, trnSpd);
				//				Timer.delay(1.0);
				//				_chassis.turnToAngle(90.0, 0.35);
				//				_chassis.driveDist(500, .5);
				//				Timer.delay(.2);
				//				_chassis.turnToAngle(90, .8);
				//				_chassis.driveDist(500, .5);
				//				Timer.delay(.2);
				//				_chassis.turnToAngle(90, .8);
				//				_chassis.driveDist(500, .5);
				//				Timer.delay(.2);
				//				_chassis.turnToAngle(90, .8);
				//				_chassis.driveDist(500, .5);
				break;

			}
		}
		autoRun = false;
	}	

	/**
	 * This function is called periodically during operator control
	 */
	@Override

	//Runs Teleop
	public void teleopPeriodic(){
		_chassis.tankDrive(OI.normalize( Math.pow(_oi.getRJoystickXAxis() * spdMult, 3) , -1.0, 0, 1.0), OI.normalize( Math.pow(_oi.getLJoystickYAxis()*spdMult, 3) ,-1.0, 0, 1.0));

		if(!_elevator.getLimit()) {
			System.out.println("Pressed");
		}

		if(_oi.getXButton()) {
			spdMult = 0.6;
		}else if(_oi.getTriangleButton()) {
			spdMult = 1.0;
		}

		//	System.out.println("Angle: " + _chassis.getAngle());
		//	System.out.println("Right Encoder: " + _chassis.getRightEncoder());

		if(_oi.getXButtonC2()) {
			_intake.setIntake(0.4, 0.55);
		}else if(_oi.getCircleButtonC2()) {
			_intake.setIntake(-1.0, -1.0);
		}else if (_oi.getTriangleButtonC2()) {
			_intake.setIntake(-0.5, -0.5);
		}else if (_oi.getSquareButtonC2()){
			_intake.setIntake(-0.25 , -0.25);
		}else {
			_intake.setIntake(0 , 0);			
		}

		if(_oi.getR2C2()> 0.05 && _elevator.getLimit()) {
			_elevator.setElevator(	 Math.pow(_oi.normalize(_oi.getR2C2(), -1, 0, 1), 3)) ;
		}else if(_oi.getL2C2() > 0.05){
			_elevator.setElevator(  Math.pow(_oi.normalize(-1.0 *_oi.getL2C2(), -1, 0, 1), 3));
		}else{
			_elevator.setElevator( 0 );
		}

		if(_oi.getR1C2()) {
			_climber.setClimber(1.0);
		}else if(_oi.getL1C2()) {
			_climber.setClimber(-1.0);
		}else {
			_climber.setClimber(0.0);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

