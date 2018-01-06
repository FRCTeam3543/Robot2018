package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Simple command to stop the drive line,
 * 
 * @author MK
 */
public class StopDrivelineCommand extends Command {

	public StopDrivelineCommand() {
		requires(Robot.driveLine);
	}
	
	@Override
	protected void initialize() {
		Robot.driveLine.stop();
	}

	@Override
	protected boolean isFinished() {		
		return false;
	}
	
}
