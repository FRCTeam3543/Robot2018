package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CircleCommandGroup extends CommandGroup {

	private Robot robot;
	protected NumberProvider distanceProvider;

	public CircleCommandGroup(Robot robot, NumberProvider distanceProvider) {
		requires(robot.getDriveLine());
		requires(robot.getDriveLineLinearPID());
		requires(robot.getDriveLineRotationPID());
		
		this.distanceProvider = distanceProvider;
		addSequential(new DriveForwardByDistanceUsingPIDCommand(robot, distanceProvider));		
        addSequential(new RotateByAngleUsingPIDCommand(robot, NumberProvider.fixedValue(Math.toRadians(180))));		
		addSequential(new DriveForwardByDistanceUsingPIDCommand(robot, distanceProvider));		        
        addSequential(new RotateByAngleUsingPIDCommand(robot, NumberProvider.fixedValue(Math.toRadians(-180))));
	}
		
	@Override
	protected void initialize() {
		robot.getDriveLine().resetAll();
	}
}