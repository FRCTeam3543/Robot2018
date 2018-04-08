// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.subsystems.drive.DriveLine;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arcade drive command
 */
public class ArcadeDriveWithJoystick extends Command {
	private DriveLine driveLine;
	private Joystick joystick;
	double timerDelay;
	
	public ArcadeDriveWithJoystick(Robot robot) {
		super();
		this.driveLine = robot.getDriveLine();
		this.joystick = robot.getOperatorInterface().getRightJoystick();
		
		requires(this.driveLine);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		this.driveLine.resetEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {		
//		this.driveLine.arcadeDrive(this.joystick);
		this.driveLine.arcadeDrive(this.joystick.getY(), this.joystick.getX() * Calibration.ARCADE_ROTATION_TRIM);
		
//		this.driveLine.doTimerDelay();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveLine.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
