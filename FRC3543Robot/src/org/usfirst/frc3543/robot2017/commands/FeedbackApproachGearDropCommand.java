package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.OI;
import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.RobotMap;
import org.usfirst.frc3543.robot2017.World;
import org.usfirst.frc3543.robot2017.util.NumberProvider;
import org.usfirst.frc3543.robot2017.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import ttfft.vision.GearDrop;

/**
 * Command to approach the gear drop using feedback control based on vision
 * 
 * The robot will attempt to detect the gear drop and use the drive line
 * to centre on the gear drop and drive towards it.  When it is inside
 * 33in and the gear drop tape is likely to be going off the bottom of the 
 * image, compute the remaining distance and use the drive forward command
 * 
 * @see GearDropAutonomousCommandGroup
 * @author MK
 *
 */
public class FeedbackApproachGearDropCommand extends Command {
	GearDrop gearDrop = null;
	public static final double MIN_DETECT_DISTANCE = 30; // inches
	public static final int DRIVE_BLIND_MAX = 6;
	NumberProvider rotationGainProvider;
	NumberProvider linearGainProvider;
	
	public FeedbackApproachGearDropCommand(NumberProvider linearGainProvider, NumberProvider rotationGainProvider) {	
		requires(Robot.driveLine);
		requires(Robot.visionSubsystem);
		this.linearGainProvider = linearGainProvider;
		this.rotationGainProvider = rotationGainProvider;
	}
	
	public FeedbackApproachGearDropCommand() {
		this(
				new SmartDashboardNumberProvider(OI.DEFAULT_LINEAR_GAIN, RobotMap.DEFAULT_LINEAR_GAIN),
				new SmartDashboardNumberProvider(OI.DEFAULT_ROTATION_GAIN, RobotMap.DEFAULT_ROTATION_GAIN)
				);
	}
	
	@Override
	protected void initialize() {
		gearDrop = null;
	}
	
	boolean neverGearDrop = true;
	int driveBlindCount = 0;
	@Override
	public void execute() {
		GearDrop lastGearDrop = gearDrop;
		if (gearDrop == null || gearDrop.distanceFromTarget >= MIN_DETECT_DISTANCE) {
			gearDrop = detectGearDrop();
			if (gearDrop != null) neverGearDrop = false;
		}		
		// if we don't have a new gear drop use the last one
		if (gearDrop == null && lastGearDrop != null) gearDrop = lastGearDrop;
		// if we never had one, invent
		if (gearDrop == null && neverGearDrop) {
			// can we invent a drive forward distance?			
		}
		
		double gain = linearGainProvider.getValue();		
		if (gearDrop != null) {

			double distance = gearDrop.distanceFromTarget; // just on
			OI.dashboard.putGearfinderLocation(String.format("Gear drop at %.1f in", distance));

			
			boolean closeIn = gearDrop.distanceFromTarget <= MIN_DETECT_DISTANCE;
			if (closeIn) {
				// do nothing
				OI.dashboard.putGearfinderLocation(String.format("Closing %.1f in", distance));
				Robot.driveLine.stop();
//				Scheduler.getInstance().add(new DriveForwardByDistanceCommand(distance, gain));
				Robot.driveLine.resetAll();
				Scheduler.getInstance().add(new DockGearCommand(distance, gain));
				
			}
			else {
//				double rotationGain = rotationGainProvider.getValue();				
//				double offset = gearDrop.offsetFromCenter;
				double angle = computeAngleToGearDropPerpendicular(gearDrop);
				// use 10 degrees = -1 angle
				double limit = Math.toRadians(15);
				double curveGain = Math.max(-1, Math.min(1, angle/limit));
				Robot.driveLine.drive(gain, curveGain);
				//Robot.driveLine.drive(gain, (angle < 0 ? -1 : 1) * rotationGain);
			}
		}
		else if (driveBlindCount++ < DRIVE_BLIND_MAX) {
			// drive forward
			Robot.driveLine.drive(gain, 0);
		}
		else {
			Robot.LOGGER.info("Oops, lots visibility");
			Robot.driveLine.stop();
			OI.dashboard.putGearfinderLocation("No gear drop");
		}		
	}
	
	protected GearDrop detectGearDrop() {
		GearDrop gd = Robot.visionSubsystem.detectGearDrop();
		if (gd == null) {
			Robot.driveLine.stop();
			for (int i=0; i<4;i++) {
				gd = Robot.visionSubsystem.detectGearDrop();
				if (gd != null) break;
			}			
		}
		return gd;
	}
	
	@Override
	protected void end() {
		Robot.driveLine.stop();
	}
	
	/**
	 * Compute the approximate angle to the line perpendicular to the gear drop
	 * 
	 * @param gearDrop2
	 * @return
	 */
	protected double computeAngleToGearDropPerpendicular(GearDrop gearDrop2) {
		double d = gearDrop.distanceFromTarget;
		double x = gearDrop.offsetFromCenter;
		return  Math.asin(x/d);
	}


	/**
	 * This command is done when we're inside the minimum distance to
	 * use vision to detect the gear.  Notice in the execute we
	 * kick off a DriveForwardByDisanceCommand once we are inside it.
	 * 
	 * We're also done if we no longer know where the gear drop is.
	 */
	@Override
	protected boolean isFinished() {
		return gearDrop == null || gearDrop.distanceFromTarget < MIN_DETECT_DISTANCE;		
	}
}
