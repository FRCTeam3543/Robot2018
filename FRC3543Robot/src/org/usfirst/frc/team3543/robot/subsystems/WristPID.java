package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Wiring;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class WristPID extends PIDSubsystem {
	Victor motorController;
	Encoder encoder;
	
	double max_speed = Calibration.WRIST_MAX_SPEED;
	double SENSITIVITY = Math.toRadians(5);
	
	public WristPID(RobotConfig config) {
		super(Calibration.WRIST_PID_P, Calibration.WRIST_PID_I, Calibration.WRIST_PID_D);
		motorController = new Victor(Wiring.WRIST_MOTOR_PORT);
		encoder = new Encoder(Wiring.WRIST_ENCODER_A, Wiring.WRIST_ENCODER_B, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(Calibration.WRIST_DPP);	// radians
		encoder.reset();
		LiveWindow.addSensor("Wrist", "Encoder", encoder);
		LiveWindow.addActuator("Wrist", "Motor Controller", motorController);
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
		motorController.set(output);
	}

}
