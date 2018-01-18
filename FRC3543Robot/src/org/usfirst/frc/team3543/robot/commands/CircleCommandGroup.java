package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CircleCommandGroup extends CommandGroup {

	protected NumberProvider distanceProvider;

	public CircleCommandGroup(NumberProvider distanceProvider) {
		requires(Robot.driveLine);
		this.distanceProvider = distanceProvider;
		addSequential(new DriveForwardByDistanceCommand(distanceProvider));		
        addSequential(new RotateByAngleCommand(Math.toRadians(180)));		
		addSequential(new DriveForwardByDistanceCommand(distanceProvider));		        
        addSequential(new RotateByAngleCommand(Math.toRadians(-180)));
	}
	
	public CircleCommandGroup(double inches) {
		this(NumberProvider.fixedValue(inches));
	}

	@Override
	protected void initialize() {
		Robot.driveLine.resetAll();
		OI.dashboard.putDistanceRemaining(0);
	}
}