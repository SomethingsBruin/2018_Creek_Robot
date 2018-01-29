package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.DriverStation;
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
//Class

public class Robot extends IterativeRobot {
	boolean autoRun = false;
	public String switLocal;
	final String defaultAuto = "Default"; //should capatalize
	final String FORWARD = "Forward Cross";
	final String LEFT = "Left Position";
	final String RIGHT = "Right Postion";
	final String CENTER = "Center Postition";

	String autoSelected;
	private SendableChooser<String> _chooser;


	Chassis _chassis;
	OI _oi;

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
		switLocal = "R";//DriverStation.getInstance().getGameSpecificMessage();
		autoSelected = _chooser.getSelected();
		autoRun = true;
		_chassis.reset();
		//		System.out.println("Auto selected: " + autoSelected);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	//Runs Autonomous
	public void autonomousPeriodic() {
		double drvSpd = 0.3;
		double trnSpd = 0.8;
		double delay = 0;
		if(autoRun){
			switch (autoSelected) {
			case LEFT:
				if(switLocal.charAt(0) == 'L'){
					System.out.println("Left Left");
					_chassis.driveDist(1400, drvSpd);
					_chassis.turnToAngle(90.0, trnSpd);
					_chassis.driveDist(300,drvSpd);
					_chassis.turnToAngle(-90.0, trnSpd);
				} else {
					System.out.println("Left Right");
					_chassis.driveDist(1400, drvSpd);
					_chassis.turnToAngle(90.0, trnSpd);
					_chassis.driveDist(1300, drvSpd);
					_chassis.turnToAngle(-90.0, trnSpd);
				}
				break;
			case RIGHT:
				if(switLocal.charAt(0) == 'L'){
					System.out.println("Right Left");
					_chassis.driveDist(1300, drvSpd);
					_chassis.turnToAngle(-90.0, trnSpd);
					_chassis.driveDist(1300, drvSpd);
					_chassis.turnToAngle(90.0, trnSpd);
					_chassis.driveDist(200, drvSpd);
				} else {
					System.out.println("Right Right");
					_chassis.driveDist(1400, drvSpd);
					_chassis.turnToAngle(-90.0, trnSpd);
					_chassis.driveDist(500,drvSpd);
					_chassis.turnToAngle(90.0, trnSpd);
				}
				break;
			case CENTER:
				if(switLocal.charAt(0) == 'L'){
					System.out.println("Center Left");
					_chassis.driveDist(200, drvSpd);
					_chassis.turnToAngle(-45.0, trnSpd);
					_chassis.driveDist(1838.477, drvSpd);
					_chassis.turnToAngle(45.0, trnSpd);
				} else {
					System.out.println("Center Right");
					_chassis.driveDist(200, drvSpd);
					_chassis.turnToAngle(45.0, trnSpd);
					_chassis.driveDist(1550, drvSpd);
					_chassis.turnToAngle(-45.0, trnSpd);
				}
				break;
			case FORWARD:
				System.out.println("Forward Cross");
				_chassis.driveDist(1300, drvSpd);
				break;
			case defaultAuto:
				break;
/*			//Autonomous Cases 
			case FORWARD_CROSS:				
				System.out.println("Forward Cross");
				_chassis.driveDist(1300, drvSpd);
				break;
				//				Checked
			case LEFT_RIGHT:
				System.out.println("Left Right");
				_chassis.driveDist(1400, drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				_chassis.driveDist(1300, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				break;
				//				Checked
			case LEFT_LEFT:
				System.out.println("Left Left");
				_chassis.driveDist(1400, drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				_chassis.driveDist(300,drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				break;
				//				Checked
			case CENTER_RIGHT:
				System.out.println("Center Right");
				_chassis.driveDist(200, drvSpd);
				_chassis.turnToAngle(45.0, trnSpd);
				_chassis.driveDist(1550, drvSpd);
				_chassis.turnToAngle(-45.0, trnSpd);
				break;
			case CENTER_LEFT:
				System.out.println("Center Left");
				_chassis.driveDist(200, drvSpd);
				_chassis.turnToAngle(-45.0, trnSpd);
				_chassis.driveDist(1838.477, drvSpd);
				_chassis.turnToAngle(45.0, trnSpd);
				break;
			case RIGHT_LEFT:
				System.out.println("Right Left");
				_chassis.driveDist(1300, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(1300, drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				_chassis.driveDist(200, drvSpd);
				break;
			case RIGHT_RIGHT:
				System.out.println("Left Left");
				_chassis.driveDist(1400, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(500,drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				break;
			case RIGHT_EXCHANGE:
				System.out.println("Right Exchange");
				_chassis.driveDist(200, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(500, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(200, drvSpd);
				break;
			case LEFT_EXCHANGE:
				System.out.println("Left Exchange");
				_chassis.driveDist(200, drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				_chassis.driveDist(500, drvSpd);
				_chassis.turnToAngle(90.0, trnSpd);
				_chassis.driveDist(200, drvSpd);
				break;
			case CENTER_EXCHANGE:
				System.out.println("Center Exchange");
				_chassis.driveDist(200, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(100, drvSpd);
				_chassis.turnToAngle(-90.0, trnSpd);
				_chassis.driveDist(200, drvSpd);
				break;
			case defaultAuto:
				break;
			default:
				// Put default auto code here
				break;
				*/
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

