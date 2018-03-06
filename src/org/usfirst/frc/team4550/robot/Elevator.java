
package org.usfirst.frc.team4550.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;

public class Elevator {
	TalonSRX talon1;
	Elevator _instance;	
	
	public Elevator(){
		talon1 = new TalonSRX(RobotMap.ELEVATOR);
		talon1.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setElevator(double speed){
		talon1.set(ControlMode.PercentOutput, speed);
	}
	
	public void moveElevator(double speed, double time) {
		talon1.set(ControlMode.PercentOutput, speed);
		Timer.delay(time);
		talon1.set(ControlMode.PercentOutput, 0.0 );
	}
	
	
	
	
}
