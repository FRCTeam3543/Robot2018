package org.usfirst.frc.team3543.robot.commands;
//* Rotate the robot by a given angle

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;
import org.usfirst.frc.team3543.robot.util.DegreesToRadiansNumberProvider;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RotateByAngleUsingPIDCommand extends Command {
	
	protected double targetAngle = 0;
	protected double rotateBy = 0;
	protected double startingAngle = 0;
	protected double sensitivity = 0.01;
	protected double targetRotation = 0;
	public static final double SENSITIVITY = Calibration.ROTATION_SENSITIVITY;// Radians
	
	private Robot robot;
		
	NumberProvider angleInRadiansProvider;
	NumberProvider gainProvider;
	NumberProvider sensitivityProvider;
	
	public RotateByAngleUsingPIDCommand(Robot robot, double angleInRadians) {
		this(robot, NumberProvider.fixedValue(angleInRadians));
	}
	
	public RotateByAngleUsingPIDCommand(Robot robot, NumberProvider angleInRadiansProvider) {
		this.robot = robot;
		this.angleInRadiansProvider = angleInRadiansProvider;
		requires(robot.getDriveLine());		
		requires(robot.getDriveLineRotationPID());
	}
	
	public void setRotationAngle(double angleInRadians) {
		this.rotateBy = angleInRadians;
	}
		
	@Override 
	protected void initialize() {		
		DriveLine driveLine = robot.getDriveLine();
		Robot.LOGGER.info("ROTATE BY " +rotateBy);
		driveLine.resetGyro();
		this.setRotationAngle(angleInRadiansProvider.getValue());
		this.targetAngle = driveLine.getGyroAngleRadians() + rotateBy;
		this.sensitivity = SENSITIVITY;		
		this.robot.getDriveLineRotationPID().setSetpoint(this.targetAngle);
		this.robot.getDriveLineRotationPID().enable();
	}
	
	@Override
	public void execute() {
		robot.getOperatorInterface().putNumber("Angle remaining", Math.toDegrees(getError()));
	}
	
	double getError() {
		return this.targetAngle - this.robot.getDriveLineRotationPID().getPosition();
	}
	
//	@SuppressWarnings("static-access")
	@Override
	protected boolean isFinished() {
		boolean done = Math.abs(getError()) < SENSITIVITY;
		return done;
	}

	
	@Override
	protected void interrupted() {
		super.interrupted();
		reset();
	}

	@Override
	public synchronized void cancel() {
		super.cancel();
		reset();
	}

	@Override
	protected void end() {
		super.end();
		reset();
	}
	
	void reset() {
		robot.getDriveLineRotationPID().disable();
		robot.getDriveLine().stop();	
	}
}