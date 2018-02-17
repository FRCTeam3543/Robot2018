package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForwardByDistanceUsingPIDCommand extends Command {
	protected double targetDistance = 0;
	protected double distanceRemaining = 0;
	
	protected double startingEncoderValue = 0;
	protected double powerGain = 1;
	
	protected NumberProvider distanceProvider;
	protected NumberProvider gainProvider;
	protected Robot robot;
	
	public static final double START_TRAPEZOID_POINT = 0.2;
	public static final double END_TRAPEZOID_POINT = 0.8;
	public static final double MIN_MAGNITUDE = 0.12;
	
	public static final double SENSITIVITY = Calibration.LINEAR_SENSITIVITY; //inches
	
	public DriveForwardByDistanceUsingPIDCommand(Robot robot, double distanceInInches) {
		this(robot, NumberProvider.fixedValue(distanceInInches));
	}
	
	public DriveForwardByDistanceUsingPIDCommand(Robot robot, NumberProvider distanceProvider) {
		this.robot = robot;
		requires(robot.getDriveLine());
		requires(robot.getDriveLineLinearPID());
		this.distanceProvider = distanceProvider;
	}
		
	public void setTargetDistance(double distanceInInches) {
		this.targetDistance = distanceInInches;
		this.distanceRemaining = this.targetDistance;
		// Set the target setpoint
		this.robot.getDriveLineLinearPID().setSetpoint(targetDistance);
	}
		
	@Override 
	protected void initialize() {
		// read the starting encoder values
		updateDistanceRemaining();
		robot.getDriveLine().resetAll();	
		this.setTargetDistance(distanceProvider.getValue());	
		this.updateDistanceRemaining();
		robot.getDriveLineLinearPID().enable();
		robot.getDriveLineLinearPID().getPIDController().setAbsoluteTolerance(SENSITIVITY);
		
	}
	
	protected void updateDistanceRemaining() {
		double d = robot.getDriveLineLinearPID().getPosition();		
		SmartDashboard.putNumber("Target Disance", this.targetDistance);
		SmartDashboard.putNumber("Linear PID Setpoint", robot.getDriveLineLinearPID().getSetpoint());
		SmartDashboard.putNumber("Linear PID Position", robot.getDriveLineLinearPID().getPosition());		
		robot.getOperatorInterface().putNumber("Distance Remaining", d - this.targetDistance);
	}
	
	@Override
	public void execute() {
		// do nothing
		updateDistanceRemaining();
	}

	@Override
	protected boolean isFinished() {
		return robot.getDriveLineLinearPID().onTarget();
//		double err = robot.getDriveLineLinearPID().getPosition() - this.targetDistance;		
//		return (Math.abs(err)  < SENSITIVITY);
	}
	
	
	@Override
	public synchronized void cancel() {
		super.cancel();
		reset();
	}

	protected void interrupted() {
		reset();
	}
	
	@Override
	protected void end() {
		super.end();
		reset();
	}
	
	private void reset() {
		robot.getDriveLineLinearPID().disable();
		robot.getDriveLine().stop();
		robot.getDriveLine().resetEncoders();		
	}
}