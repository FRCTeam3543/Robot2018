package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;
import org.usfirst.frc.team3543.robot.util.NumberProvider;
import org.usfirst.frc.team3543.robot.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardByDistanceCommand extends Command {
	protected double targetDistance = 0;
	protected double distanceRemaining = 0;
	private boolean goingBackwards = false;
	
	protected double startingEncoderValue = 0;
	protected double powerGain = 1;
	
	protected NumberProvider distanceProvider;
	protected NumberProvider gainProvider;
	protected Robot robot;
	
	public static final double START_TRAPEZOID_POINT = 0.2;
	public static final double END_TRAPEZOID_POINT = 0.8;
	public static final double MIN_MAGNITUDE = 0.12;
	
	public static final double SENSITIVITY = 0.75; //inches

	public DriveForwardByDistanceCommand(Robot robot, double distanceInInches, double powerGain) {
		this(robot, NumberProvider.fixedValue(distanceInInches), NumberProvider.fixedValue(powerGain));
	}
	
	public DriveForwardByDistanceCommand(Robot robot, NumberProvider distanceProvider, NumberProvider gainProvider) {
		this.robot = robot;
		requires(robot.getDriveLine());
		this.distanceProvider = distanceProvider;
		this.gainProvider = gainProvider;
	}
	
//	public DriveForwardByDistanceCommand(Robot robot, NumberProvider distanceProvider) {
//		this(robot, distanceProvider,  new SmartDashboardNumberProvider(OI.DEFAULT_LINEAR_GAIN, RobotMap.DEFAULT_LINEAR_GAIN));
//	}
//	
//	public DriveForwardByDistanceCommand(Robot robot, double distanceInInches) {
//		this(robot, NumberProvider.fixedValue(distanceInInches));
//	}
	
	public void setTargetDistance(double distanceInInches) {
		this.targetDistance = distanceInInches;
		this.distanceRemaining = this.targetDistance;
		goingBackwards = false;
	}
	
	public void setPowerGain(double powerGain) {
		this.powerGain = powerGain;
	}
	
	@Override 
	protected void initialize() {
		// read the starting encoder values
<<<<<<< HEAD
		Robot.driveLine.resetAll();	
=======
		updateDistanceRemaining(0);
		robot.getDriveLine().resetAll();	
>>>>>>> branch 'master' of https://github.com/FRCTeam3543/Robot2018.git
		this.setTargetDistance(distanceProvider.getValue());
		this.setPowerGain(gainProvider.getValue());
	}
	
	protected void updateDistanceRemaining(double d) {
		// absolute value should be less
		this.goingBackwards = Math.abs(d) > Math.abs(this.distanceRemaining);
		this.distanceRemaining = d;
		robot.getOperatorInterface().putNumber("Distance Remaining", d);
	}
	
	@Override
	public void execute() {
		DriveLine driveLine = robot.getDriveLine();
		// trapezoid func for velocity for smooth drive.		
		driveLine.updateOperatorInterface();
		double dist = driveLine.getLeftEncoderValue();
		if (this.targetDistance > 0) {
			dist =  Math.max(this.targetDistance, dist);
		}
		else {
			dist = Math.min(this.targetDistance, dist);
		}
		
		double percentTraveled = (this.targetDistance - dist) / this.targetDistance;
		double mag = this.targetDistance >= 0 ? 1 : -1;
		if (percentTraveled < START_TRAPEZOID_POINT) {
			mag *= (START_TRAPEZOID_POINT - percentTraveled) / START_TRAPEZOID_POINT;
		}
		else if (percentTraveled > END_TRAPEZOID_POINT) {
			mag *= (1 - (percentTraveled - END_TRAPEZOID_POINT)) / START_TRAPEZOID_POINT;
		}
		if (this.targetDistance > 0) {
			mag = Math.max(MIN_MAGNITUDE, mag);	
		}
		else {
			mag = Math.min(-MIN_MAGNITUDE, mag);						
		}
		driveLine.driveStraight(mag * this.powerGain);		
	}

	@Override
	protected boolean isFinished() {
		robot.getDriveLine().updateOperatorInterface();
		double enc = robot.getDriveLine().getLeftEncoderValue();
		updateDistanceRemaining(this.targetDistance - enc);
		return this.goingBackwards || (Math.abs(this.targetDistance - enc)  < SENSITIVITY);
	}
	
	@Override
	protected void end() {
		super.end();
		robot.getDriveLine().stop();
		robot.getDriveLine().resetEncoders();
	}
}