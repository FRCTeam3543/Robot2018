package org.usfirst.frc.team3543.robot.commands;

import com.sun.istack.internal.logging.Logger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command does nothing.
 * 
 * @author mk
 */
public class DuhCommand extends Command {

	
	@Override
	protected void initialize() {
		super.initialize();
		Logger.getLogger(DuhCommand.class).info("Init");
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
