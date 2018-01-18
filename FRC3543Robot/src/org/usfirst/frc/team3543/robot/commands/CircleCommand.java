package org.usfirst.frc.team3543.robot.commands;
import org.usfirst.frc.team3543.robot.Robot;

// testing accuracy of autonomous driving, robot should end up where it started 

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CircleCommand extends CommandGroup {

	public CircleCommand(double inches, double gain) {
		requires(Robot.driveLine);
		addSequential(new DriveForwardByDistanceCommand(40, gain));                //40 inches = 1 meter
		addSequential(new RotateByAngleCommand(Math.toRadians(180), gain));         
		addSequential(new DriveForwardByDistanceCommand(40, gain));	
		addSequential(new RotateByAngleCommand(Math.toRadians(180), gain));
	}
	
	@Override
	protected void initialize() {
		Robot.driveLine.resetAll();
	}
}