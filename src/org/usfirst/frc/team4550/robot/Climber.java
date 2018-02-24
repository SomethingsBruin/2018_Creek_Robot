package org.usfirst.frc.team4550.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;

public class Climber {
	TalonSRX talon1;
	Encoder _encoder;
	
	public Climber(){
		talon1 = new TalonSRX(RobotMap.CLIMBER);
		talon1.setInverted(true);
		talon1.setNeutralMode(NeutralMode.Brake);
		_encoder = new Encoder(RobotMap.ENCODER_A_CLIMBER, RobotMap.ENCODER_B_CLIMBER);
	}
	
	
	public void setClimber(double speed){
		talon1.set(ControlMode.PercentOutput, -speed);
	}
	
	public double getDistance() {
		return _encoder.getDistance();
	}
	
	public void reset() {
		_encoder.reset();
	}



	
	
	
}
