package org.usfirst.frc3543.robot2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command to "zig zag" the robot.  Rotate, drive forward, then rotate back. 
 * 
 * Rotates toward a line, drives forward and rotates back again.  This is mostly
 * for testing and calibration purposes, it is not used in competition.
 * 
 * @author MK
 */
public class ZigZagCommand extends CommandGroup {
	
	RotateByAngleCommand firstRotation;
	DriveForwardByDistanceCommand goForward;
	RotateByAngleCommand rotateBack;
	
	public ZigZagCommand(double angle, double distance) {		
		firstRotation = new RotateByAngleCommand(angle);
		rotateBack = new RotateByAngleCommand(-angle);
		goForward = new DriveForwardByDistanceCommand(distance);
		addSequential(firstRotation);
		addSequential(goForward);
		addSequential(rotateBack);
	}
	
	public void setAngle(double angle) {
		firstRotation.setRotationAngle(angle);
		rotateBack.setRotationAngle(-angle);
	}
	
	public void setDistance(double distance) {
		goForward.setTargetDistance(distance);
	}

}
