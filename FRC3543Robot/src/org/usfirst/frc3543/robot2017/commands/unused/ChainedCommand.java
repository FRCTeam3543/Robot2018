package org.usfirst.frc3543.robot2017.commands.unused;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import java.util.List;
import java.util.logging.Logger;

import org.usfirst.frc3543.robot2017.Robot;

import java.util.ArrayList;

abstract public class ChainedCommand extends Command {
	public static final Logger LOGGER = Logger.getLogger(ChainedCommand.class.getSimpleName());
	private Command currentCommand = null;
	private boolean exec_once = false;
	
	public abstract Command evaluateNextCommand(Command currentCommand);
	public abstract Command getFirstCommand();
	
	protected Command getCurrentCommand() {
		return currentCommand;
	}
	
	protected void setCurrentCommand(Command c) {
		currentCommand = c;
		if (currentCommand != null) {
			Scheduler.getInstance().add(currentCommand);
			LOGGER.info("setCurrentCommand: Current command scheduled: " + c.getClass().getSimpleName());			
		}
		else {
			LOGGER.info("setCurrentCommand: Current command is NULL");
		}
		exec_once = false;
	}
	
	
	
	@Override
	protected void initialize() {
		setCurrentCommand(getFirstCommand());		
	}
	
	@Override
	protected void execute() {
		// I think we do nothing here.  Everything is in isFinished
//		if (currentCommand != null && !exec_once) {
//			currentCommand.start();		
//		}
		exec_once = true;
	}

	@Override
	protected boolean isFinished() {
		Command currentCommand = getCurrentCommand();
		if (currentCommand == null) {
			Robot.LOGGER.info("isFinished: No current command, finished");
			return true;
		}
		else {
			LOGGER.info("isFinished: Current command is "+currentCommand.getClass().getSimpleName());
			// if canceled, we're canceled
			if (currentCommand.isCanceled()) {
				LOGGER.info("isFinished: Current command canceled");			
				return true;
			}		
			// if the command is running, keep it running
			else if (currentCommand.isRunning()) {
				Robot.LOGGER.info("isFinished: Current command isRunning");				
				return false;
			}
			// if not running, see if there is a new command
			else if (!currentCommand.isRunning() && exec_once) {
				LOGGER.info("Executed once and not running, get next command");
				Command c = evaluateNextCommand(currentCommand);
				LOGGER.info("Current command is NOT running, next is " + (c == null ? "NULL" : c.getClass().getSimpleName()));						
				
				// if no next command, we're done
				setCurrentCommand(c);
				if (c == null) {
					return true;
				}
				else {
					return false;
				}
			}
			else { // WTF return true
				LOGGER.info("WTF, guess we're done");						

				return true;
			}
		}

	}

	@Override
	protected void interrupted() {
		if (currentCommand != null) {
			currentCommand.cancel();
		}
		super.interrupted();		
	}
	
	@Override
	public synchronized void cancel() {
		if (currentCommand != null) {
			currentCommand.cancel();
		}		
		super.cancel();
	}
		
	
	
	
	
	
}
