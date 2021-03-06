package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

public class ClawCloseCommand extends ClawOpenCommand {

	public ClawCloseCommand(Robot robot) {
		super(robot);
	}
	
	public void execute() {
		robot.getClaw().close();
	}
}
