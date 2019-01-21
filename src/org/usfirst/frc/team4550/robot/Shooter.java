package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Shooter
{

	//The talon that controls the shooting
    private Talon _shooter;
    
    //The shooter on the robot
    private static Shooter _instance;
    
    //Whether or not we are firing
    private boolean firing = false;
    
    //The Timer that controls when to fire
    private Timer _timer;
    
    /**
     * Private constructor for singleton that creates a new talon for the shooter
     * @param talonLocation The port of the talon 
     */
    private Shooter( )
    {
        //Creates a new talon that will control the shooter at the specified port
        _shooter = new Talon( RobotMap.SHOOTER_PORT );
    }
    
    /**
     * Singleton of the Shooter
     * @return Returns the instance of the Shooter that is in use
     */
    public static Shooter getInstance( )
    {
    	//Creates a new shooter if we already don't have one and returns the shooter if we do
        if( _instance == null )
        {
            _instance = new Shooter( );
        }
        return _instance;
    }
    /**
     * Shoots the tShirt
     * @param delay The time that the valve is open for the shot to occur
     */
    public void shoot( double delay )
    {
        //Checks to see if we are already firing, if so, don't fire
        if( !firing && ( _timer == null || _timer.get( ) > 1.0 ) )
        {
          //Creates a timer to make sure we aren't firing to quickly
          _timer = new Timer( );
          _timer.reset( );
          _timer.start( );
          
          //Starts the firing process and fires using the delay provided
          firing = true;
          _shooter.set( 1.0 );
          Timer.delay( delay );
          _shooter.set( 0.0 );
          firing = false;
          System.out.println("fired");
        }
    }
	
}
