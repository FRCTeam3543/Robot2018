package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

public class ClawOffCommand extends ClawOpenCommand {

	public ClawOffCommand(Robot robot) {
		super(robot);
	}

	public void execute() {
		robot.getClaw().off();
	}
}
