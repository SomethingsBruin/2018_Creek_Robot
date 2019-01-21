package org.usfirst.frc.team4550.robot;

import static org.usfirst.frc.team4550.robot.RobotMap.*;
import edu.wpi.first.wpilibj.Joystick;


public class OI
{
	// The Joystick that the driver is using
	private Joystick _joystick;
	
	public OI()
	{
		//Sets _joystick to to the correct input
		int input = 0 ; // input 0
		_joystick = new Joystick ( input );
		
	}
	
	public void printAxis()
	{
		//Prints all of the axises on the controller
		for( int i = 0; i < _joystick.getAxisCount(); i++ )
		{
			System.out.printf( "%10s", i + " " + _joystick.getRawAxis( i ) + "\t" );
	
		}
		System.out.println();
		
	}
	
	public void printButtons()
	{
		// Prints all of the buttons on the controller
		for( int i = 1; i <= _joystick.getButtonCount(); i++ )
		{
			System.out.printf( "%10s", i + "   " + _joystick.getRawButton( i ) + "\t" );
		}
		System.out.println();
	}

	public double getAxis( int axis )
	{
		// Returns the value of the specified axis.
		return _joystick.getRawAxis( axis );
	}

	public double getLJoystickXAxis()
	{
		// Returns the left _joystick's horizontal value.
		return _joystick.getRawAxis( L_JOYSTICK_HORIZONTAL );
	}

	public double getLJoystickYAxis()
	{
		// Returns the left _joystick's vertical value, which is inverted.
		return _joystick.getRawAxis( L_JOYSTICK_VERTICAL ) * -1;
	}

	public double getRJoystickXAxis()
	{
		// Returns the right _joystick's horizontal value.
		return _joystick.getRawAxis( R_JOYSTICK_HORIZONTAL );
	}

	public double getRJoystickYAxis()
	{
		// Returns the right _joystick's vertical value, which is inverted.
		return _joystick.getRawAxis( R_JOYSTICK_VERTICAL ) * -1;
	}

	/*public double getL2R2()
	{
		// Returns the left / right bumpers.
		return _joystick.getRawAxis( L2_R2 );
	}*/
	
	public double getL2( )
	{
		return _joystick.getRawAxis(  L2 );
	}
	
	public double getR2( )
	{
		return _joystick.getRawAxis( R2 );
	}
	
	public boolean getXButton( )
	{
		//Returns whether or not the x button is being pressed
		return _joystick.getRawButton( RobotMap.X_BUTTON );
	}
	
	public boolean getOButton( )
	{
		return _joystick.getRawButton( RobotMap.O_BUTTON );
	}
	
	public boolean getTriangleButton( )
	{
		return _joystick.getRawButton(  RobotMap.TRIANGLE_BUTTON );
	}
	
	public boolean getL1Button(){
		return _joystick.getRawButton( RobotMap.L1_BUTTON );
	}
	
	/**
	 * Normalizes the a given value
	 * @param max
	 * @param min
	 * @param value
	 * @return
	 */
	public static double normalize( double max, double min, double zero, double value )
	{
		//If the value exceeds the maximum, set it back to the max
		if( value > max )
		{
			return max;
		}
		//If the value is lower than the minimum, set it back to the min
		else if( value < min )
		{
			return min;
		}
		//If the controller is at neutral position, set the speed to zero
		else if (value == zero){
			return 0;
		}
		//Otherwise the value is fine
		return value*value*value;
	}
	
}
