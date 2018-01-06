package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceGearCommand extends CommandGroup {

	public PlaceGearCommand(double gain) {
		addSequential(new MoveGearDumperCommand(RobotMap.DEFAULT_GEAR_DUMPER_UP));
		addSequential(new WaitCommand(6.0));
		addSequential(new DriveForwardByDistanceCommand(-RobotMap.DEFAULT_GEAR_DUMPER_BACKUP, gain));
		addSequential(new MoveGearDumperCommand(RobotMap.DEFAULT_GEAR_DUMPER_DOWN));		
	}
	
	public PlaceGearCommand() {
		this(RobotMap.DEFAULT_LINEAR_GAIN);
	}
}
