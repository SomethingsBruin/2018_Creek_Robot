package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.Compressor;


public class ShooterCompressor
{

	//The Compressor Instance
    private static ShooterCompressor _instance = null;
    
    //The FRC Compressor represented by this class
     private Compressor _compressor;
    
    /**
     * Constructs a new Compressor
     */
    private ShooterCompressor( )
    {
        _compressor = new Compressor( );
    }
    
    /**
     * Singleton of the Compressor
     * @return The Instance
     */
    public static ShooterCompressor getInstance( )
    {
        //Creates a new compressor if we already don't have one and returns the compressor if we do.
        if( _instance == null )
        {
            _instance = new ShooterCompressor( );
        }
        return _instance;
    }
    
    /**
     * Runs the Compressor
     */
    public void runCompressor( )
    {
    	_compressor.setClosedLoopControl( true );
    }
    
    /**
     * Stops the compressor
     */
    public void stop( )
    {
    	_compressor.stop();
    }
    
    /**
     * Starts the compressor
     */
    public void start( )
    {
    	_compressor.setClosedLoopControl( true );
    	_compressor.start( );
    }
	
}
