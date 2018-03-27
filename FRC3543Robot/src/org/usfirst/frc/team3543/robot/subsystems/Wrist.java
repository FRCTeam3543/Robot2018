package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.Wiring;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends BaseSubsystem {
	WPI_TalonSRX motorController;
	Encoder encoder;
	double max_speed = Calibration.WRIST_MAX_SPEED;
	double SENSITIVITY = Math.toRadians(5);
	WristPID wristPID;
	
	@SuppressWarnings("deprecation")
	public Wrist() {
		super();
		motorController = new WPI_TalonSRX(Wiring.WRIST_MOTOR_PORT);
		
		encoder = new Encoder(Wiring.WRIST_ENCODER_A, Wiring.WRIST_ENCODER_B, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(Calibration.WRIST_DPP);
		encoder.reset();
		LiveWindow.addSensor("Wrist", "Encoder", encoder);
		LiveWindow.addActuator("Wrist", "Motor Controller", motorController);
		this.wristPID = new WristPID(this.motorController, this.encoder);
	}
	
	public WristPID getPID() {
		return this.wristPID;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		encoder.reset();
		off();
	}
	
	public boolean isUp() {
		if (false && encoder.getDistance() < (Calibration.WRIST_UP_POS + SENSITIVITY)) {
			return true;
		}
		else return false;
	}

	public boolean isDown() {
		// down should be in positive direction
		if (false && encoder.getDistance() > (Calibration.WRIST_DOWN_POS - SENSITIVITY)) {
			return true;
		}
		else return false;
	}
	
	public void go_up() {
		go_up(Calibration.WRIST_MAX_SPEED);
	}

	public void go_down() {
		go_down(Calibration.WRIST_MAX_SPEED);
	}
	
	public void setSpeed(double speed) {
		speed = Math.min(Calibration.WRIST_MAX_SPEED, Math.max(speed, -Calibration.WRIST_MAX_SPEED));
		Robot.LOGGER.info("SET SPEED " + speed);
		SmartDashboard.putNumber("Wrist Motor Speed", speed);
		motorController.set(speed);
		SmartDashboard.putNumber("Wrist position", encoder.get());
	}
	
	public void off() {
		setSpeed(0);
	}
	
	public void go_up(double speed) {
		// go up, but only if not at the up limit
		if (!isUp()) {
			// todo this should ramp
			setSpeed(-speed);
		}
		else {
			Robot.LOGGER.info("Wrist in up position");
		}		
	}
	
	public void go_down(double speed) {
		if (!isDown()) {
			setSpeed(speed);
		}
		else {
			Robot.LOGGER.info("Wrist in down position");
		}
	}

	public void resetEncoder() {
		encoder.reset();
	}

}
