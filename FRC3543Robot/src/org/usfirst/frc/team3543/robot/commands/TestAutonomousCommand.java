package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestAutonomousCommand extends Command {

	public TestAutonomousCommand() {
		requires(Robot.driveLine);
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		Robot.driveLine.stop();
	}


	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
		Robot.driveLine.driveStraight(0.5);
	}

	@Override
	public void cancel() {
		Robot.driveLine.stop();		
		super.cancel();
	}
}
