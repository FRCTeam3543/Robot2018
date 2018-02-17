package org.usfirst.frc.team3543.robot.subsystems;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class BaseSubsystem extends Subsystem {
	
	public BaseSubsystem() {
		this.setName(getClass().getSimpleName());		
	}
	
	protected void updateOperatorInterface() {
		// Override me
	}
	
	/**
	 * Returns a logger
	 * @return
	 */
	protected Logger getLogger() {
		return Logger.getLogger(getName());
	}
}
