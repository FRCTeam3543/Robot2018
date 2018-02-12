package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * PID Controller for rotary motion.
 * 
 * See https://wpilib.screenstepslive.com/s/3120/m/7912/l/79828-operating-the-robot-with-feedback-from-sensors-pid-control
 * @author mk
 *
 */
public class DriveLineRotationPID extends PIDSubsystem {
	DriveLine driveLine;
	
	public DriveLineRotationPID(DriveLine driveLine) {
		super("DriveLineRotationPID", Calibration.DRIVELINE_ROTATION_PID_P, Calibration.DRIVELINE_ROTATION_PID_I, Calibration.DRIVELINE_ROTATION_PID_D);
		this.driveLine = driveLine;
		this.getPIDController().setContinuous(true);
	}

	@Override
	protected double returnPIDInput() {
		return driveLine.getGyroAngleRadians();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO need to double-check the phase sense here, but this seems right, intuitively
		driveLine.getLeftMotor().pidWrite(-output);	
		driveLine.getRightMotor().pidWrite(output);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}
