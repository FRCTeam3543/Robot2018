package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.Wiring;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftPID extends PIDSubsystem {
	Victor motorController;
	Encoder encoder;
	
	double max_speed = Calibration.LIFT_MAX_SPEED;
	double SENSITIVITY = 0.25;	// inches
	
	public LiftPID(RobotConfig config) {
		super(Calibration.LIFT_PID_P, Calibration.LIFT_PID_I, Calibration.LIFT_PID_D);
		motorController = new Victor(Wiring.LIFT_MOTOR_PORT);
		encoder = new Encoder(Wiring.LIFT_ENCODER_A, Wiring.LIFT_ENCODER_B, false, EncodingType.k2X);
		this.setAbsoluteTolerance(SENSITIVITY);
		encoder.setDistancePerPulse(Calibration.LIFT_DPP);
		encoder.reset();
		LiveWindow.addSensor("Lift", "Encoder", encoder);
		LiveWindow.addActuator("Lift", "Motor Controller", motorController);
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
		motorController.set(output);
	}

}
