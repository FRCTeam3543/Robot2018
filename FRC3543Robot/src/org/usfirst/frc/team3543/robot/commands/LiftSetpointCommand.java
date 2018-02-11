package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author C4 Robotics
 * @deprecated for SetLiftPositionCommand
 */
public class LiftSetpointCommand extends Command {
	NumberProvider setpointProvider;
	Robot robot;
	
	public LiftSetpointCommand(Robot robot, NumberProvider setpointProvider) {
		requires(robot.getLift());
		this.setpointProvider = setpointProvider;
	}
	
	public void execute() {
		double setpoint = this.setpointProvider.getValue();
		this.robot.getLift().getPID().setSetpoint(setpoint);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	public synchronized void cancel() {
		super.cancel();
		// hold wherever we are currently set
		this.robot.getLift().getPID().hold();
	}
	
	
}
