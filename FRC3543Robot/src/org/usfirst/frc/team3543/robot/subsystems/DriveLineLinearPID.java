package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * PID Controller for linear motion.
 * 
 * See https://wpilib.screenstepslive.com/s/3120/m/7912/l/79828-operating-the-robot-with-feedback-from-sensors-pid-control
 * @author mk
 *
 */
public class DriveLineLinearPID extends PIDSubsystem {
	DriveLine driveLine;
	
	public DriveLineLinearPID(DriveLine driveLine) {
		super("DriveLineLinearPID", Calibration.DRIVELINE_LINEAR_PID_P, Calibration.DRIVELINE_LINEAR_PID_I, Calibration.DRIVELINE_LINEAR_PID_D);
		this.driveLine = driveLine;
	}

	@Override
	protected double returnPIDInput() {
		return driveLine.getLeftEncoderValue();
	}

	@Override
	protected void usePIDOutput(double output) {
		driveLine.getLeftMotor().pidWrite(output);	
		driveLine.getRightMotor().pidWrite(output);		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
