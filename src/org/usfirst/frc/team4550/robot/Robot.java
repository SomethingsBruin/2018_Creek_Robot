

package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	private static OI _oi;// The driver interface 
	private static Chassis _chassis; // The robot chassis
	private static Mechanism _mechanism; //The robot mechanism
	private static Shooter _shooter;//The shooter
	private static ShooterCompressor _compressor;//The compressor
	
	private SendableChooser _chooser;
	private SendableChooser _chooser2;
	
	
	/**
	 * This function is run when the robot is first started up
	 *  and should be used for any initialization. 
	 */
    public void robotInit()
    {
    	// Creates the OI, the Chassis, the Mechanism, the Shooter, and the Compressor of the robot
    	_oi = new OI();
    	_chassis = Chassis.getInstance();
    	_mechanism = Mechanism.getInstance();
    	_shooter = Shooter.getInstance();
    	_compressor = ShooterCompressor.getInstance();
    }
    
    /**
	 * This function is called when the disabled button is hit.
	 * You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit()
	{
		_chassis.reset();//Stops the chassis
		_mechanism.stopArm();//Stops the mechanism
		_compressor.stop();//Stops the compressor
	}

    /**
     * This function is called periodically when the robot is disabled
     */
    public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {

    }
    
    /**
     * 
     * hey
     * 
     */
    
    /**
     * Starts the teleop
     */
    public void teleopInit()
	{
    	//_compressor.start( );
	}

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
    	Scheduler.getInstance().run();
    	//Drives the robot
    	_chassis.holoDrive( OI.normalize( .25, -.25, RobotMap.RIGHT_Y_ZERO, _oi.getRJoystickYAxis( ) ) , OI.normalize( .25, -.25, 0, _oi.getRJoystickXAxis( ) ), OI.normalize( .25, -.25, 0, _oi.getLJoystickXAxis( ) ) );
    	
    	//Moves the arm
    	_mechanism.moveArm(  OI.normalize( .7, -.7, 0, _oi.getL2() - _oi.getR2() ) );
    	//System.out.println( OI.normalize( .7, -.7, 0, _oi.getL2() - _oi.getR2() ) );
   		
    	//Stops the arm if necessary
    	if( Math.abs( _oi.getL2() - _oi.getR2() ) <= 0.05 )
   		{
   			_mechanism.stopArm();
   		}
    	//Runs the compressor
   		_compressor.runCompressor();
   		
   		
   		//Shoots if needed
   		if( _oi.getXButton() )
   		{
   			_shooter.shoot( RobotMap.SHOOT_LOW_FOOT );
   		}
   		else if( _oi.getOButton( ) )
   		{
   			_shooter.shoot( RobotMap.SHOOT_MED_FOOT );
   		}
   		else if( _oi.getTriangleButton( ) )
   		{
   			_shooter.shoot(  RobotMap.SHOOT_HI_FOOT );
   		}

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	
    }
    
}
