package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.Robot;

/**
 * Opposite of the LiftRobotCommand, running the motor backwards in order to lower it
 * 
 * @author MK
 * @see LiftRobotCommand
 */
public class LowerRobotCommand extends LiftRobotCommand {

	@Override
	public void execute() {
    	Robot.liftSubsystem.lower(getGain());
    }
}
