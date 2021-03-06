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

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Arcade drive command
 */
public class TankDriveWithJoystick extends Command {
	DriveLine driveLine;
	Joystick leftStick;
	Joystick rightStick;
	
	public TankDriveWithJoystick(Robot robot) {
		driveLine = robot.getDriveLine();
		leftStick = robot.getOperatorInterface().getLeftJoystick();
		rightStick = robot.getOperatorInterface().getRightJoystick();
		requires(driveLine);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.LOGGER.info("TANK DRIVE INIT");
		driveLine.resetEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		driveLine.tankDrive(leftStick, rightStick);
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
