package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftDownCommand extends Command {
	Robot robot;
	public LiftDownCommand(Robot robot) {
		this.requires(robot.getLift());
		this.robot = robot;
	}
	public void execute() {
		robot.getLift().go_down();
	}
	
	@Override
	protected boolean isFinished() {
		return robot.getLift().isDown();
	}			
	@Override
	protected void end() {
		super.end();
		robot.getLift().stop();
	}			
}
