package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DockGearCommand extends CommandGroup {

	public DockGearCommand(double inches, double gain) {
		requires(Robot.driveLine);
		// rotate a bit
//		addSequential(new RotateByAngleCommand(Math.toRadians(0.5), gain));
		addSequential(new DriveForwardByDistanceCommand(inches, gain));		
		// new for North Bay - dock the gear
		addSequential(new PlaceGearCommand());
	}
	
	@Override
	protected void initialize() {
		Robot.driveLine.resetAll();
	}
}
