package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClawOffCommand extends Command {

	public ClawOffCommand() {
		requires(Robot.claw);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	public void execute() {
		Robot.claw.off();
	}
}
