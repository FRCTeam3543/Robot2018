package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class WristPID extends PIDSubsystem {
	IMotorController motorController;
	Encoder encoder;
	
	double max_speed = Calibration.WRIST_MAX_SPEED;
	double SENSITIVITY = Math.toRadians(5);
	
	@SuppressWarnings("deprecation")
	public WristPID(IMotorController motorController, Encoder encoder) {
		super(Calibration.WRIST_PID_P, Calibration.WRIST_PID_I, Calibration.WRIST_PID_D);
		this.motorController = motorController;
		this.encoder = encoder;
		encoder.setDistancePerPulse(Calibration.WRIST_DPP);	// radians
		this.setAbsoluteTolerance(Calibration.WRIST_TOLERANCE);
		encoder.reset();

		LiveWindow.addActuator("Wrist","PID", this.getPIDController());
	}

	@Override
	protected void initDefaultCommand() {
		encoder.reset();		
	}

	public void reset() {
		this.setSetpoint(Calibration.WRIST_UP_POS);
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
