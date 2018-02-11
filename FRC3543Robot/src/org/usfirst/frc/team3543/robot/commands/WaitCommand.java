package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command {
	long startTime;
	private NumberProvider timeoutProvider;
	
	public WaitCommand(NumberProvider timeoutInMilliSeconds) {
		super();
		this.timeoutProvider = timeoutInMilliSeconds;
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		startTime = System.currentTimeMillis();
	}


	@Override
	protected boolean isFinished() {
		return (System.currentTimeMillis() - startTime) >= timeoutProvider.getValue();
	}

	
}
