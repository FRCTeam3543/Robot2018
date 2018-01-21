package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.motion.MotionProfile;
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileCommand extends Command {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int BOTH = LEFT | RIGHT;
	
	Robot robot;
	NumberProvider distanceProvider;
	int which;
	DriveLine.MotionProfileRunner runner;
	private MotionProfile profile;
	boolean once = false;
	
	public MotionProfileCommand(Robot robot, MotionProfile profile, int which, NumberProvider distanceProvider) {
		super();
		
		this.robot = robot;
		this.distanceProvider = distanceProvider;
		this.which = which;
		this.profile = profile;
	}
	
	@Override	
	public void execute() {
		if (!once) {
			runner.start();
			once = true;
		}
	}
	
	@Override
	protected void initialize() {		
//		super.initialize();
//		robot.getDriveLine().
//		runner = robot.getDriveLine().initMotionProfile(this.profile);
	}
	
	@Override
	protected void interrupted() {
		super.interrupted();
		if (once) {
			runner.stop();
		}
	}

	@Override
	public synchronized void cancel() {
		super.cancel();
	}

	@Override
	protected boolean isFinished() {
		return runner.isActive();
	}
	
}
