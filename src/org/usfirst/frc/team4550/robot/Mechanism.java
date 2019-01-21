package org.usfirst.frc.team4550.robot;

import org.first.frc.team4550.controls.CCTalon;

public class Mechanism
{
	
	private static Mechanism _instance;//This mechanism
		
	//The talon which represents the pivot of the mechanism arm.
    private CCTalon _pivot;
	
	private Mechanism( )
	{
		_pivot = new CCTalon( RobotMap.PIVOT_PORT, RobotMap.PIVOT_REVERSED );
	}
	/**
	 * Singleton for the Mechanism
	 * @return
	 */
	public static Mechanism getInstance()
	{
		//Creates a new mechanism if we already don't have one and returns the mechanism if we do
		if( _instance == null )
		{
			_instance = new Mechanism( );
		}
		return _instance;
	}
	
	/**
     * Lowers the arm on the mechanism at the given speed.
     * 
     * @param speed The speed at which to lower the arm.
     */
    public void moveArm( double speed )
    {
        //Raises the arm at the given speed.
        _pivot.set( speed );
    }
    
    /**
     * Stops the arm on the mechanism where it is.
     */
    public void stopArm()
    {
        _pivot.set( 0.0 );
    }

}
