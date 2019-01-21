package org.usfirst.frc.team4550.robot;


public class RobotMap
{
	
	public static final int L_JOYSTICK_HORIZONTAL = 0;
	public static final int L_JOYSTICK_VERTICAL = 1;
	//PS3 only public static final int L2_R2 = 2;
	public static final int L2 = 2;
	public static final int R2 = 3;
	public static final int R_JOYSTICK_HORIZONTAL = 4;
	public static final int R_JOYSTICK_VERTICAL = 5;

	public static final int X_BUTTON = 1;
	public static final int O_BUTTON = 2;
	public static final int SQUARE_BUTTON = 3;
	public static final int TRIANGLE_BUTTON = 4;
	public static final int L1_BUTTON = 5;
	public static final int R1_BUTTON = 6;
//	public static final int DOWN_BUTTON = 7;
//	public static final int RIGHT_BUTTON = 8;
//	public static final int LEFT_BUTTON = 9;
//	public static final int UP_BUTTON = 10;
//	public static final int L3_BUTTON = 11;
//	public static final int R3_BUTTON = 12;
	
	public static final int SELECT_BUTTON = 7;
	public static final int START_BUTTON = 8;
	//These buttons are when you push down the left and right circle pad
	public static final int L_JOYSTICK_BUTTON = 9;
	public static final int R_JOYSTICK_BUTTON = 10;

	public static final double LEFT_Y_ZERO = -0.0078125;
	public static final double RIGHT_Y_ZERO = -0.0078125;
	
	
	
	public static final int LEFT_FRONT_TALON_PORT = 0;
	public static final boolean LEFT_FRONT_TALON_REVERSE = false;
	public static final int RIGHT_FRONT_TALON_PORT = 1;
	public static final boolean RIGHT_FRONT_TALON_REVERSE = true;
	public static final int LEFT_REAR_TALON_PORT = 2;
	public static final boolean LEFT_REAR_TALON_REVERSE = false;
	public static final int RIGHT_REAR_TALON_PORT = 3;
	public static final boolean RIGHT_REAR_TALON_REVERSE = true;
	
	public static final int PIVOT_PORT = 4;
	public static final boolean PIVOT_REVERSED = false;
	
	public static final int SHOOTER_PORT = 5;
	
	public static final double SHOOT_LOW_FOOT = 0.025;
	public static final double SHOOT_MED_FOOT = 0.035;
	public static final double SHOOT_HI_FOOT = 0.15;
	
	public static final double SHOOT_LOW_GYM = 0.022;
	public static final double SHOOT_MED_GYM = 0.030;
	public static final double SHOOT_HI_GYM = 0.060;
	
}
