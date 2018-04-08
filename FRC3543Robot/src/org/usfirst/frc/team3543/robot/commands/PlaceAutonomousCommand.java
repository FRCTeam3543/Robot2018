package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import com.usfirst.frc.team3543.robot.pathrecording.PathProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceAutonomousCommand extends CommandGroup {
	Robot robot;
	
	public PlaceAutonomousCommand(Robot robot, PathProvider pathProvider, boolean middle, boolean dropBlock) {
		super();
		requires(robot.getDriveLine());
		requires(robot.getClaw());
		requires(robot.getLift());
		requires(robot.getWrist());
		this.robot = robot;
		addSequential(new WaitCommand(NumberProvider.fixedValue(1000)));
		addSequential(new PlaybackCommand(robot, pathProvider));
		// if we're middle we have to raise the lift first
		if (middle) {
			addSequential(new LiftUpCommand(robot));
		}
		if (dropBlock) {
			addSequential(new SetWristPositionCommand(robot, NumberProvider.fixedValue(Calibration.WRIST_DOWN_POS), NumberProvider.fixedValue(2000)));
			addSequential(new ClawOpenCommand(robot));
		}
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.robot.getWrist().resetEncoder();
	}
	
}