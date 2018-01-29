package org.usfirst.frc.team4550.robot;

public class Climber {
	CCTalon talon1;
	
	public Climber(){
		talon1 = new CCTalon(RobotMap.CLIMBER, false);
	}
	
	
	public void setCliber(double speed){
		talon1.set(speed);
	}



	
	
	
}
