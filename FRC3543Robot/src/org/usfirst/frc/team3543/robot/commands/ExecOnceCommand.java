package org.usfirst.frc.team3543.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class ExecOnceCommand extends Command {
	boolean once = false;
	
	@Override
	public void execute() {
		if (!once) {
			once = true;
			executeOnce();
		}
	}
	
	protected abstract void executeOnce();
	
	@Override
	protected boolean isFinished() {
		return once;
	}

}
