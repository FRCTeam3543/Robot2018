package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.OI;
import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.util.GearDropConsumer;
import org.usfirst.frc3543.robot2017.util.GearDropProvider;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import ttfft.vision.GearDrop;

/**
 * Command to locate the gear drop using the vision subsystem
 * 
 * This is mostly for the SmartDashboard button, to allow testing of the
 * vision subsystem.
 * 
 * @author MK
 */
public class LocateGearDropUsingVisionCommand extends Command implements GearDropProvider {

	private GearDrop gearDrop = null;
		
	private int maxRechecks;
	private boolean finished = false;
	private GearDropConsumer consumer = null;
	private int recheckCounter = 0;
	
	public LocateGearDropUsingVisionCommand(int maxRechecks, GearDropConsumer consumer) {
		super();
		this.setMaxRechecks(maxRechecks);
		this.setGearDropConsumer(consumer);
	}
	
	public LocateGearDropUsingVisionCommand() {
		this(5);
	}
	
	public LocateGearDropUsingVisionCommand(int maxRechecks) {
		this(maxRechecks, null);
	}

	public void setGearDropConsumer(GearDropConsumer c) {
		this.consumer = c;
	}
	
	public void setMaxRechecks(int m) {
		this.maxRechecks = m;
	}
	public void reset() {	
		gearDrop = null;
		finished = false;
		recheckCounter = 0;
	}
	
	@Override
	protected void initialize() {
		super.initialize();	
		reset();
	}

	@Override
	protected void execute() {
		// see if we can see a gear drop. Should be on first try, but we'll look a couple
		// of times just in case
		if (recheckCounter++ < maxRechecks) {			
			gearDrop = Robot.visionSubsystem.detectGearDrop();
			if (gearDrop != null) {
				OI.dashboard.putGearfinderLocation(String.format("FOUND IT %.1f in", gearDrop.distanceFromTarget));
				finished = true;
			}	
			else {
				OI.dashboard.putGearfinderLocation("Finding Nothing ...");
			}
			if (consumer != null) {
				consumer.setGearDrop(gearDrop);
			}
//			Timer.delay(0.05);
		}
		else {
			if (consumer != null) {
				consumer.setGearDrop(null);				
			}
			finished = true;
		}
		
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}
	
	@Override
	protected void end() {
		super.end();
		finished = true;
	}


	@Override
	public GearDrop getGearDrop() {
		return gearDrop;
	}

}
