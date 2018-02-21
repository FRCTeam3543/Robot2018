package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Wiring;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class UltrasonicRange extends BaseSubsystem {
	AnalogInput rangeSensor;
	
	public UltrasonicRange() {
		super();
		rangeSensor = new AnalogInput(Wiring.ULTRASOUND_PORT);	
		rangeSensor.setAccumulatorInitialValue(0);
		rangeSensor.setName("Ultrasound sensor");
		rangeSensor.setSubsystem(this.getName());
		LiveWindow.addSensor(this.getName(),rangeSensor.getName(), rangeSensor);
	}

	@Override
	protected void initDefaultCommand() {
		reset();
	}
	
	public void reset() {
		rangeSensor.resetAccumulator();
	}
	
	public double getAverageRange() {
		return rangeSensor.getAverageVoltage() * Calibration.ULTRASONIC_CALIBRATION;
	}
	
	public double getRange() {
		return rangeSensor.getVoltage() * Calibration.ULTRASONIC_CALIBRATION;
	}
	
}
