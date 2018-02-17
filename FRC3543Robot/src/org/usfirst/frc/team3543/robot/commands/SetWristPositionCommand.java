package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;

public class SetWristPositionCommand extends Command {
	Robot robot;
	NumberProvider setpointProvider;
	NumberProvider maxTimeProvider;
	long startTime;
	public SetWristPositionCommand(Robot robot, NumberProvider setPointProvider, NumberProvider maxTimeProvider) {
		requires(robot.getWrist());
		this.robot = robot;
		this.setpointProvider = setPointProvider;
		this.maxTimeProvider = maxTimeProvider;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		startTime = System.currentTimeMillis();
		robot.getWrist().getPID().enable();
		updateSetpoint();
	}
	
	public void updateSetpoint() {
		robot.getWrist().getPID().setSetpoint(setpointProvider.getValue());
	}
	
	public void execute() {
		updateSetpoint();
	}

	@Override
	protected boolean isFinished() {
		boolean onTarget = robot.getWrist().getPID().onTarget();
		boolean done = onTarget || 
				((System.currentTimeMillis() - startTime) > maxTimeProvider.getValue());
		return done;
	}

	public void end() {
		super.end();
		robot.getWrist().getPID().disable();
	}
}
