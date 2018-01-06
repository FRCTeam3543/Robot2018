package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoopGearDropCommand extends CommandGroup {

	public BoopGearDropCommand() {
		addSequential(new MoveGearDumperCommand(RobotMap.DEFAULT_GEAR_DUMPER_BOOP));
		addSequential(new WaitCommand(RobotMap.DEFAULT_GEAR_DUMPER_BOOP_WAIT));
		addSequential(new MoveGearDumperCommand(RobotMap.DEFAULT_GEAR_DUMPER_DOWN));		
	}
}
