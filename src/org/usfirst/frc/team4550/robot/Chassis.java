package org.usfirst.frc.team4550.robot;

import org.first.frc.team4550.controls.CCTalon;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//import com.kauailabs.navx.frc.AHRS;

public class Chassis
{
	
	private static Chassis _instance;//The current chassis
	
	private CCTalon _leftFront;// The left front talon on the robot
	private CCTalon _rightFront;// The right front talon on the robot
	private CCTalon _leftRear;//The left rear talon on the robot
	private CCTalon _rightRear;//The right rear talon on the robot
//	private AHRS _gyro; // this is a gyroscope
	private Servo _servo;

	private double rotateToAngleRate;
	
	private Chassis( )
	{
		//Initializes the talons
		_leftFront = new CCTalon( RobotMap.LEFT_FRONT_TALON_PORT, RobotMap.LEFT_FRONT_TALON_REVERSE );
		_rightFront = new CCTalon( RobotMap.RIGHT_FRONT_TALON_PORT, RobotMap.RIGHT_FRONT_TALON_REVERSE );
		_leftRear = new CCTalon( RobotMap.LEFT_REAR_TALON_PORT, RobotMap.LEFT_REAR_TALON_REVERSE );
		_rightRear = new CCTalon( RobotMap.RIGHT_REAR_TALON_PORT, RobotMap.RIGHT_REAR_TALON_REVERSE );
		try{
//			_gyro = new AHRS(SPI.Port.kMXP);
		}catch( RuntimeException ex ){
			 DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
		_servo = new Servo(6);
	}	
	
	/**
	 * Singleton of the Chassis
	 * @return the Chassis in use
	 */
	public static Chassis getInstance()
	{
		//Creates a new chassis if we already don't have one and returns the chassis if we do
		if( _instance == null )
		{
			_instance = new Chassis();
		}
		return _instance;
	}
	/**
	 * Drives the T-Shirt shooter
	 * @param fwd
	 * @param sld
	 * @param rot
	 */
	public void holoDrive( double fwd, double sld, double rot )
    {
        //Calculates the vector for each motor given the forward, slide, and rotation values.
        double lf = fwd + sld + rot;
        double rf = fwd - sld - rot;
        double lr = fwd - sld + rot;
        double rr = fwd + sld - rot;
        
        //Makes the max value equal to the left motor.
        double maxVal = Math.abs( lf );
        
        //If any motor value is greater than the max value, make the max value equal to that motor value.
        if ( Math.abs( rf ) > maxVal )
        {
            maxVal = Math.abs( rf );
        }
        
        if ( Math.abs( lr ) > maxVal )
        {
            maxVal = Math.abs( lr );
        }
        
        if ( Math.abs( rr ) > maxVal )
        {
            maxVal = Math.abs( rr );
        }
        
        //If the max value was greater than one or less than negative one...
        if( maxVal > 1.0 ) 
        {
            //Limit that motor value and scale the other motor values appropriately.
            lf /= maxVal;
            rf /= maxVal;
            lr /= maxVal;
            rr /= maxVal;
        }
         
        //Set each motor to its corresponding value.
        _leftFront.set( lf );
        _rightFront.set( rf );
        _leftRear.set( lr );
        _rightRear.set( rr );
    }
	
	/**
	 * Stops the robot
	 */
	public void stop()
	{
		// Stops the robot
		_leftFront.set( 0 );
		_rightFront.set( 0 );
		_leftRear.set( 0 );
		_rightRear.set( 0 );
	}
	
	/**
	 * Resets the Chassis
	 */
	public void reset()
	{
		stop();
	}
	
	public void normalDrive( double y )
	{
		_leftFront.set( y );
		_rightFront.set( y );
		_leftRear.set( y );
		_rightRear.set( y );
	}
	
	public void turnToAngle(double degree, double speed )
	{
//		
//		double Kp = 0.0;
//		double Ki = 0.0;
//		double Kd = 0.0;
//		double Kf = 0.0;
//		
////		//PIDController controller = new PIDController(Kp, Ki, Kd, Kf, _gyro, this);
////		
////		controller.setInputRange(-180.0f,  180.0f);
////	    controller.setOutputRange(-1.0, 1.0);
////	    controller.setAbsoluteTolerance(2.0f);
////	    controller.setContinuous(true);
//		
//	    LiveWindow.addActuator("DriveSystem", "RotateController", controller);
//	    
//	    _gyro.reset();
//	    
//	    controller.setSetpoint(degree);
//	    double currentRotationRate;
//	    controller.enable();
//	    currentRotationRate = rotateToAngleRate;
	}


	public void setServo(double pos){
		_servo.set(pos);
	}
	
}
