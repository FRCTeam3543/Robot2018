package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class LiftPID extends PIDSubsystem {
	IMotorController motorController;
	Encoder encoder;
	
	double max_speed = Calibration.LIFT_MAX_SPEED;
	double SENSITIVITY = 0.25;	// inches
	
	public LiftPID(IMotorController motorController, Encoder encoder) {
		super(Calibration.LIFT_PID_P, Calibration.LIFT_PID_I, Calibration.LIFT_PID_D);
		this.motorController = motorController;
		this.encoder = encoder;		
		this.setAbsoluteTolerance(SENSITIVITY);
		encoder.setDistancePerPulse(Calibration.LIFT_DPP);
		encoder.reset();
		LiveWindow.addActuator("Lift","PID", this.getPIDController());

	}

	
	@Override
	protected void initDefaultCommand() {
		encoder.reset();		
	}

	public void reset() {
		this.setSetpoint(Calibration.LIFT_DOWN_POS);
	}
	
	public void hold() {
		this.setSetpoint(this.returnPIDInput());
	}
	
	@Override
	protected double returnPIDInput() {
		return encoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		motorController.set(ControlMode.PercentOutput, output);
	}

}
