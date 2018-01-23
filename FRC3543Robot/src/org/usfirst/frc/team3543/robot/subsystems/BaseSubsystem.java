package org.usfirst.frc.team3543.robot.subsystems;

import java.util.logging.Logger;

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class BaseSubsystem extends Subsystem {
	private OI operatorInterface;
	
	public BaseSubsystem(RobotConfig config) {
		this.operatorInterface = operatorInterface;
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
