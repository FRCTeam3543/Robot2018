package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClawOpenCommand extends Command {

	public ClawOpenCommand() {
		requires(Robot.claw);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	public void execute() {
		Robot.claw.open();
	}
}
