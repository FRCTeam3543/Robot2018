package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.motion.MotionProfile;
import org.usfirst.frc.team3543.robot.motion.MotionProfileProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BothWheelsMotionProfileCommand extends CommandGroup {

	public BothWheelsMotionProfileCommand(Robot robot, MotionProfileProvider leftWheel, MotionProfileProvider rightWheel) {
		requires(robot.getDriveLine());		
		addParallel(new MotionProfileCommand(robot, leftWheel, robot.getDriveLine().getLeftMotor()));
		addParallel(new MotionProfileCommand(robot, rightWheel, robot.getDriveLine().getRightMotor()));		
	}
}
