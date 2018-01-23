package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.motion.MotionProfileGenerator;
import org.usfirst.frc.team3543.robot.motion.MotionProfileProvider;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SmoothDriveForwardByDistanceCommand extends CommandGroup {

	public SmoothDriveForwardByDistanceCommand(Robot robot, NumberProvider distanceProvider) {
		requires(robot.getDriveLine());
		MotionProfileProvider provider = MotionProfileProvider.forDistance(distanceProvider);
		addSequential(new BothWheelsMotionProfileCommand(robot, provider, provider));
	}
}
