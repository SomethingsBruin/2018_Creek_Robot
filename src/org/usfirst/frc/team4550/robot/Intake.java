package org.usfirst.frc.team4550.robot;

public class Intake {

	CCTalon talon1;
	CCTalon talon2;

	public Intake(){
		talon1 = new CCTalon(RobotMap.INTAKE_A, false);
		talon2 = new CCTalon(RobotMap.INTAKE_B, false);
	}

	public void setIntake(double speed){
		talon1.set(speed);
		talon2.set(speed);
	}



}
