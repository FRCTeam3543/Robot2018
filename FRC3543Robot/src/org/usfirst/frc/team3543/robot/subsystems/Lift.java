package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.Wiring;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends BaseSubsystem {
	Victor motorController;
	Victor otherMotorController;
	Encoder encoder;
	DigitalInput lowSwitch, highSwitch;
	
	double max_speed = Calibration.LIFT_MAX_SPEED;
	double SENSITIVITY = Math.toRadians(5);
	LiftPID liftPID;
	
	@SuppressWarnings("deprecation")
	public Lift() {
		super();
		motorController = new Victor(Wiring.LIFT_MOTOR_PORT);
		otherMotorController = new Victor(Wiring.LIFT_MOTOR_PORT_2);
		encoder = new Encoder(Wiring.LIFT_ENCODER_A, Wiring.LIFT_ENCODER_B, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(Calibration.LIFT_DPP);
		encoder.reset();
		
		lowSwitch = new DigitalInput(Wiring.LIFT_LOW_SWITCH);
		highSwitch = new DigitalInput(Wiring.LIFT_HIGH_SWITCH);
		
		LiveWindow.addSensor("Lift", "Encoder", encoder);
		LiveWindow.addActuator("Lift", "Motor Controller", motorController);
		LiveWindow.addActuator("Lift", "Other Motor Controller", otherMotorController);		
		LiveWindow.addSensor("Lift", "High Switch", highSwitch);
		LiveWindow.addSensor("Lift", "Low Switch", lowSwitch);
		
		liftPID = new LiftPID(this.motorController, this.encoder);
	}

	public LiftPID getPID() {
		return liftPID;
	}

	public void resetEncoder() {
		encoder.reset();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		encoder.reset();
		off();
	}
	
	public boolean isUp() {
//		if (false && encoder.getDistance() < (Calibration.LIFT_UP_POS + SENSITIVITY)) {
//			return true;
//		}
		// digital switch
		return !highSwitch.get();
//		if (highSwitch.get()) {
//			return true;
//		}
//		else return false;
	}

	public boolean isDown() {
		// down should be in positive direction
//		if (false && encoder.getDistance() > (Calibration.LIFT_DOWN_POS - SENSITIVITY)) {
//			return true;
//		}
		return !lowSwitch.get();
//		if (lowSwitch.get()) {
//			return true;
//		}		
//		else return false;
	}
	
	public void go_up() {
		go_up(Calibration.LIFT_MAX_SPEED_UP);
	}

	public void go_down() {
		go_down(Calibration.LIFT_MAX_SPEED_DOWN);
	}
	
	public void stop() {
		setSpeed(0);
	}
	
	public void setSpeed(double speed) {
		speed = Math.min(Calibration.LIFT_MAX_SPEED, Math.max(speed, -Calibration.LIFT_MAX_SPEED));
		Robot.LOGGER.info("SET SPEED " + speed);
		SmartDashboard.putNumber("Lift Motor Speed", speed);
		motorController.set(speed);
		otherMotorController.set(speed);
		SmartDashboard.putNumber("Lift position", encoder.get());
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
			Robot.LOGGER.info("Lift in up position");
		}		
	}
	
	public void go_down(double speed) {
		if (!isDown()) {
			setSpeed(speed);
		}
		else {
			Robot.LOGGER.info("Lift in down position");
		}
	}

	public void go_down_fast() {
		go_down(Calibration.LIFT_CLIMB_SPEED);
	}

}
