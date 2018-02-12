package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClawOpenCommand extends Command {
	Robot robot;
	
	public ClawOpenCommand(Robot robot) {
		this.robot = robot;
		requires(robot.getClaw());
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	public void execute() {
		robot.getClaw().open();
	}
}
