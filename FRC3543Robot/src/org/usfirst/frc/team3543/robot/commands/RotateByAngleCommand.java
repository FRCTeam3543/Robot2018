package org.usfirst.frc.team3543.robot.commands;
//* Rotate the robot by a given angle

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.subsystems.drive.DriveLine;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;


public class RotateByAngleCommand extends Command {
	
	protected double targetAngle = 0;
	protected double rotateBy = 0;
	protected double startingAngle = 0;
	protected double sensitivity = 0.01;
	protected double targetRotation = 0;
	public static final double SENSITIVITY = 0.01;// Radians
	protected double gain = 1;
	
	private Robot robot;
	
	public static final double DEFAULT_SENSITIVITY_DEGREES = 1;
	
	NumberProvider angleInRadiansProvider;
	
	public RotateByAngleCommand(Robot robot, double angleInRadians) {
		this(robot, NumberProvider.fixedValue(angleInRadians));
	}
	
	public RotateByAngleCommand(Robot robot, NumberProvider angleInRadiansProvider) {
		requires(robot.getDriveLine());		
		this.robot = robot;
		this.angleInRadiansProvider = angleInRadiansProvider;
	}
		
	@Override 
	protected void initialize() {		
		DriveLine driveLine = robot.getDriveLine();
		driveLine.resetEncoders();
//		driveLine.controlHeading(this.angleInRadiansProvider.getValue());
	}
	
	@Override
	public void execute() {
		// do nothing, we are using PID
	}
	
	@Override
	protected boolean isFinished() {
		return true;
//		return robot.getDriveLine().isOnHeadingTarget();
	}
		
	@Override
	protected void end() {
		super.end();
		robot.getDriveLine().stopAll();
	}
}