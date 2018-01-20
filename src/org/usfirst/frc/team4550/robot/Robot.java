package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	boolean autoRun = false;

	final String defaultAuto = "Default"; //should capatalize
	final String customAuto = "My Auto";
	final String LEFT_LEFT = "Left Left Case";
	final String LEFT_RIGHT = "Left Right Case";
	final String CENTER_RIGHT = "Center Right Switch";
	final String CENTER_LEFT = "Center Left Switch";
	final String RIGHT_LEFT = "Right Left Switch";
	final String RIGHT_RIGHT = "Right Right Switch";
	final String RIGHT_EXCHANGE = "Right go to Exchange";
	final String LEFT_EXCHANGE = "Left go to Exchange";
	final String CENTER_EXCHANGE = "Center go to Exchange";
	final String FORWARD_CROSS = "Cross Line";
			
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();


	Chassis _chassis;
	OI _oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		_chassis = Chassis.getInstance();
		_oi = new OI();
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
	public void autonomousInit() {
		autoSelected = FORWARD_CROSS;//chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		autoRun = true;
		_chassis.reset();
//		System.out.println("Auto selected: " + autoSelected);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		if(autoRun){
			switch (autoSelected) {
			case FORWARD_CROSS:				
//				_chassis.driveDist(1300, 0.3);
//				_chassis.driveDist(500, 0.25);
			case LEFT_LEFT:
				_chassis.driveDist(500, .101);
			case LEFT_RIGHT:
//				_chassis.driveDist(500, 0.01);
//				while(true){
//					double x = 0.01;
//					_chassis.driveDist(500, x);
//					x += 0.01;
//				}
				
//				_chassis.driveDist(1300, .3);
//				_chassis.turnToAngle(90.0, .6);
//				_chassis.driveDist(1300, .3);
//				_chassis.turnToAngle(-90.0,.6);
//				_chassis.driveDist(200,.3);
			case CENTER_RIGHT:
			case CENTER_LEFT:	
			case RIGHT_LEFT:
			case RIGHT_RIGHT:
			case RIGHT_EXCHANGE:
			case LEFT_EXCHANGE:
			case CENTER_EXCHANGE:	
			case defaultAuto:
			default:
				// Put default auto code here
				break;
			}
		}
		autoRun = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic(){
		_chassis.tankDrive(OI.normalize(_oi.getLJoystickXAxis(), -1.0, 0, 1.0), OI.normalize(_oi.getRJoystickYAxis(),-1.0, 0, 1.0));
//		System.out.println(_chassis.getAngle());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

