package org.usfirst.frc.team3543.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.motion.MotionProfile;
import org.usfirst.frc.team3543.robot.motion.MotionProfileProvider;
import org.usfirst.frc.team3543.robot.motion.MotionProfileRunner;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileCommand extends Command {
	
	Robot robot;
	WPI_TalonSRX talon;
	NumberProvider distanceProvider;
	MotionProfileRunner runner;
	private MotionProfileProvider profileProvider;
	boolean once = false;
	
	public MotionProfileCommand(Robot robot, MotionProfileProvider profileProvider, WPI_TalonSRX talon) {
		super();
		requires(robot.getDriveLine());
		this.robot = robot;
		this.talon = talon;
		this.profileProvider = profileProvider;
	}
	
	@Override
	protected void initialize() {
		runner = new MotionProfileRunner(robot.getDriveLine().getLeftMotor(), profileProvider.getMotionProfile().getPeriod() / 2);
	}
	
	@Override	
	public void execute() {
		if (!once) {
			// put the motors in control mode			
			runner.getTalon().set(ControlMode.MotionProfile, 0);
			runner.startMotionProfile(profileProvider.getMotionProfile());			
			once = true;
		}
		runner.control();
	}
		
	@Override
	protected void interrupted() {
		runner.reset();
		super.interrupted();		
	}

	@Override
	public synchronized void cancel() {
		super.cancel();
	}

	@Override
	protected boolean isFinished() {
		return runner.getSetValue().equals(SetValueMotionProfile.Hold)
			   ||
			   runner.getSetValue().equals(SetValueMotionProfile.Invalid)					
		;		
	}
	
}
