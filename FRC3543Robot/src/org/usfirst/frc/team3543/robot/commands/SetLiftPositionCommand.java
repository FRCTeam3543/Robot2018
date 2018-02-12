package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;

public class SetLiftPositionCommand extends Command {
	Robot robot;
	NumberProvider setpointProvider;
	
	public SetLiftPositionCommand(Robot robot, NumberProvider setPointProvider) {
		requires(robot.getLift());
		this.robot = robot;
		this.setpointProvider = setPointProvider;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		updateSetpoint();
	}
	
	public void updateSetpoint() {
		robot.getLift().getPID().setSetpoint(setpointProvider.getValue());
	}
	
	public void execute() {
		updateSetpoint();
	}

	@Override
	protected boolean isFinished() {
		return robot.getLift().getPID().onTarget();
	}

//	public void end() {
//		super.end();
//		robot.getLift().hold();
//	}
}
