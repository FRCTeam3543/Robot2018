package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to close the gear holder
 * 
 * This is just the opposite of the OpenGearHolderCommand
 * 
 * @author MK
 */
public class CloseGearHolderCommand extends OpenGearHolderCommand {
		
	public void execute() {
		Robot.gearHolder.close();			
	}
	
	public boolean isFinished() {
		return !Robot.gearHolder.isOpen();
	}
		
}
