package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CircleCommandGroup extends CommandGroup {

	private Robot robot;
	protected NumberProvider distanceProvider;

	public CircleCommandGroup(Robot robot, NumberProvider distanceProvider, NumberProvider forwardGainProvider, NumberProvider rotationGainProvider) {
		requires(robot.getDriveLine());
		this.distanceProvider = distanceProvider;
		addSequential(new DriveForwardByDistanceCommand(robot, distanceProvider, forwardGainProvider));		
        addSequential(new RotateByAngleCommand(robot, NumberProvider.fixedValue(Math.toRadians(180)), rotationGainProvider));		
		addSequential(new DriveForwardByDistanceCommand(robot, distanceProvider, forwardGainProvider));		        
        addSequential(new RotateByAngleCommand(robot, NumberProvider.fixedValue(Math.toRadians(-180)), rotationGainProvider));
	}
	
	public CircleCommandGroup(Robot robot, NumberProvider distanceProvider, NumberProvider gainProvider) {
		this(robot, distanceProvider, gainProvider, gainProvider);
	}
	
	@Override
	protected void initialize() {
		robot.getDriveLine().resetAll();
	}
}