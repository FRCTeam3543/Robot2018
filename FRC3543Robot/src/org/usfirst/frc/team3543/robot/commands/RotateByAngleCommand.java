package org.usfirst.frc.team3543.robot.commands;
//* Rotate the robot by a given angle

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.RobotMap;
<<<<<<< HEAD
=======
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;
import org.usfirst.frc.team3543.robot.util.DegreesToRadiansNumberProvider;
>>>>>>> branch 'master' of https://github.com/FRCTeam3543/Robot2018.git
import org.usfirst.frc.team3543.robot.util.NumberProvider;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RotateByAngleCommand extends Command {
	
	protected double targetAngle = 0;
	protected double rotateBy = 0;
	protected double startingAngle = 0;
	protected double sensitivity = 0.01;
	protected double targetRotation = 0;
	public static final double SENSITIVITY = 0.01;// Radians
	protected double gain = RobotMap.DEFAULT_ROTATION_GAIN;
	
	private Robot robot;
	
	public static final double DEFAULT_SENSITIVITY_DEGREES = 1;
	
	NumberProvider angleInRadiansProvider;
	NumberProvider gainProvider;
	NumberProvider sensitivityProvider;
	
	public RotateByAngleCommand(Robot robot, double angleInRadians) {
		this(robot, angleInRadians, RobotMap.DEFAULT_ROTATION_GAIN);
	}
	
	public RotateByAngleCommand(Robot robot, NumberProvider angleInRadiansProvider, NumberProvider gainProvider, NumberProvider sensitivityProvider) {
		this.robot = robot;
		this.gainProvider = gainProvider;
		this.angleInRadiansProvider = angleInRadiansProvider;
		this.sensitivityProvider = sensitivityProvider;
		requires(robot.getDriveLine());		
	}
	
	public RotateByAngleCommand(Robot robot, NumberProvider angleInRadiansProvider, NumberProvider gainProvider) {
		this(robot, angleInRadiansProvider, gainProvider, NumberProvider.fixedValue(DEFAULT_SENSITIVITY_DEGREES));
	}
	
	public RotateByAngleCommand(Robot robot, double angleInRadians, double gain) {
		this(robot, NumberProvider.fixedValue(angleInRadians), NumberProvider.fixedValue(gain));
	}
	
	public RotateByAngleCommand(Robot robot, NumberProvider angleInRadiansProvider) {
		this(robot, angleInRadiansProvider, NumberProvider.fixedValue(RobotMap.DEFAULT_ROTATION_GAIN));
	}

	public void setRotationAngle(double angleInRadians) {
		this.rotateBy = angleInRadians;
	}
	
	public void setSensitivity(double radians) {
		this.sensitivity = radians;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
	}
	
	@Override 
	protected void initialize() {		
		DriveLine driveLine = robot.getDriveLine();
		Robot.LOGGER.info("ROTATE BY " +rotateBy);
		driveLine.resetGyro();
		this.setGain(gainProvider.getValue());
		this.setRotationAngle(angleInRadiansProvider.getValue());
		setSensitivity(sensitivityProvider.getValue());

		this.startingAngle = driveLine.getGyroAngleRadians();
		this.targetAngle = startingAngle + rotateBy;
		// FIXME - What is this?
		this.sensitivity = driveLine.getGyroSensitivity() * 2;		
	}
	
	@Override
	public void execute() {
		DriveLine driveLine = robot.getDriveLine();
		
		double diff = driveLine.getGyroAngleRadians() - targetAngle;		
		// if diff > 0, rotate CW.  Two magnitudes, depending on abs angle
		double mag = 1;
		double absdiff = Math.abs(diff);
		// ease in
//		if (absdiff < Math.toRadians(5)) {
//			mag = mag * absdiff / 5;
//		}		
		// set a minimum, so we get there
		mag = Math.max(mag, 0.12);
		
		if (diff > 0) { // go CW
			driveLine.rotateClockwise(mag * gain);
		}
		else { // go CCW
			driveLine.rotateCounterClockwise(mag * gain);			
		}		
	}
	
	@SuppressWarnings("static-access")
	@Override
	protected boolean isFinished() {
		double angle = robot.getDriveLine().getGyroAngleRadians();
		double diff = angle - targetAngle;
		robot.getOperatorInterface().putNumber("Angle remaining", Math.toDegrees(diff));
		
		boolean done = Math.abs(diff) < sensitivity;
		return done;
	}
		
	@Override
	protected void end() {
		super.end();
		robot.getDriveLine().stop();
	}
}