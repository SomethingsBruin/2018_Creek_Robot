package org.usfirst.frc.team4550.robot;

public class Elevator {
	CCTalon talon1;
	
	public Elevator(){
		talon1 = new CCTalon(RobotMap.ELEVATOR, false);
	}
	
	
	public void setElevator(double speed){
		talon1.set(speed);
	}
}
