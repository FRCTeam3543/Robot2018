package team3543.robot.cmd;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a do-nothing command
 * 
 * @author mk
 */
public class IdleCommand extends Command {
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
