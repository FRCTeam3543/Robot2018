package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.OI;
import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.RobotMap;
import org.usfirst.frc3543.robot2017.util.NumberProvider;
import org.usfirst.frc3543.robot2017.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive forward by a given distance
 * 
 * @author MK
 */
public class DriveForwardByDistanceCommand extends Command {
	protected double targetDistance = 0;
	protected double startingEncoderValue = 0;
	protected double powerGain = 1;
	
	protected NumberProvider distanceProvider;
	protected NumberProvider gainProvider;
	
	public static final double START_TRAPEZOID_POINT = 0.2;
	public static final double END_TRAPEZOID_POINT = 0.8;
	public static final double MIN_MAGNITUDE = 0.12;
	
	public static final double SENSITIVITY = 0.75; //inches
	public DriveForwardByDistanceCommand(double distanceInInches, double powerGain) {
		this(NumberProvider.fixedValue(distanceInInches), NumberProvider.fixedValue(powerGain));
	}
	
	public DriveForwardByDistanceCommand(NumberProvider distanceProvider, NumberProvider gainProvider) {
		requires(Robot.driveLine);
		this.distanceProvider = distanceProvider;
		this.gainProvider = gainProvider;
	}
	
	public DriveForwardByDistanceCommand(NumberProvider distanceProvider) {
		this(distanceProvider,  new SmartDashboardNumberProvider(OI.DEFAULT_LINEAR_GAIN, RobotMap.DEFAULT_LINEAR_GAIN));
	}
	
	public DriveForwardByDistanceCommand(double distanceInInches) {
		this(NumberProvider.fixedValue(distanceInInches));
	}
	
	public void setTargetDistance(double distanceInInches) {
		this.targetDistance = distanceInInches;
	}
	
	public void setPowerGain(double powerGain) {
		this.powerGain = powerGain;
	}
	
	@Override 
	protected void initialize() {
		// read the starting encoder values
		Robot.driveLine.resetAll();	
		this.setTargetDistance(distanceProvider.getValue());
		this.setPowerGain(gainProvider.getValue());
	}
	
	@Override
	public void execute() {
		// trapezoid func for velocity for smooth drive.		
		Robot.driveLine.updateDashboard();
		double dist = Robot.driveLine.getLeftEncoderValue();
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
//		Robot.driveLine.drive(mag * this.powerGain);
		Robot.driveLine.driveStraight(mag * this.powerGain);		
	}

	@Override
	protected boolean isFinished() {
		double enc = Robot.driveLine.getLeftEncoderValue();
		OI.dashboard.putDistanceRemaining(this.targetDistance - enc);
		return Math.abs(this.targetDistance - enc)  < SENSITIVITY;
	}
	
	@Override
	protected void end() {
		super.end();
		Robot.driveLine.stop();
		Robot.driveLine.resetEncoders();
	}
}
