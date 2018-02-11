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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends BaseSubsystem {
	Talon motorController;
	Encoder encoder;
	double max_speed = Calibration.LIFT_MAX_SPEED;
	double SENSITIVITY = Math.toRadians(5);
	
	public Lift(RobotConfig config) {
		super(config);
		motorController = new Talon(Wiring.LIFT_MOTOR_PORT);
		encoder = new Encoder(Wiring.LIFT_ENCODER_A, Wiring.LIFT_ENCODER_B, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(Calibration.LIFT_DPP);
		encoder.reset();
		LiveWindow.addSensor("Lift", "Encoder", encoder);
		LiveWindow.addActuator("Lift", "Motor Controller", motorController);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		encoder.reset();
		off();
	}
	
	public boolean isUp() {
		if (false && encoder.getDistance() < (Calibration.LIFT_UP_POS + SENSITIVITY)) {
			return true;
		}
		else return false;
	}

	public boolean isDown() {
		// down should be in positive direction
		if (false && encoder.getDistance() > (Calibration.LIFT_DOWN_POS - SENSITIVITY)) {
			return true;
		}
		else return false;
	}
	
	public void go_up() {
		go_up(Calibration.LIFT_MAX_SPEED);
	}

	public void go_down() {
		go_down(Calibration.LIFT_MAX_SPEED);
	}
	
	public void setSpeed(double speed) {
		speed = Math.min(Calibration.LIFT_MAX_SPEED, Math.max(speed, -Calibration.LIFT_MAX_SPEED));
		Robot.LOGGER.info("SET SPEED " + speed);
		SmartDashboard.putNumber("Lift Motor Speed", speed);
		motorController.set(speed);
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

}
