package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftUpCommand extends Command {
	Robot robot;
	public LiftUpCommand(Robot robot) {
		this.requires(robot.getLift());
		this.robot = robot;
	}
	public void execute() {
		robot.getLift().go_up();
	}
	
	@Override
	protected boolean isFinished() {
		return robot.getLift().isUp();
	}			
	@Override
	protected void end() {
		super.end();
		robot.getLift().stop();
	}			
}
