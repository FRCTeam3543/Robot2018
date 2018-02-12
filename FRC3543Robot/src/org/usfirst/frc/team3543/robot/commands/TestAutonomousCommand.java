package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestAutonomousCommand extends Command {
	private Robot robot;
	
	public TestAutonomousCommand(Robot robot) {
		requires(robot.getDriveLine());
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		robot.getDriveLine().stop();
	}


	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
		robot.getDriveLine().driveStraight(0.5);
	}

	@Override
	public void cancel() {
		robot.getDriveLine().stop();		
		super.cancel();
	}
}
