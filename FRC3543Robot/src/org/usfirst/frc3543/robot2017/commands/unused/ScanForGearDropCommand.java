package org.usfirst.frc3543.robot2017.commands.unused;

import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.commands.LocateGearDropUsingVisionCommand;
import org.usfirst.frc3543.robot2017.commands.RotateByAngleCommand;
import org.usfirst.frc3543.robot2017.util.GearDropConsumer;
import org.usfirst.frc3543.robot2017.util.GearDropProvider;

import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.CommandGroup;
import ttfft.vision.GearDrop;

public class ScanForGearDropCommand extends ChainedCommand implements GearDropConsumer, GearDropProvider {
	protected double incrementAngleInRadians;
	protected boolean finished = false;
	private GearDropConsumer gearDropConsumer;
	private double initialGyroAngle = 0;
	private double totalAngleChecked = 0;
	public static final double DEFAULT_INCREMENT_ANGLE = 0.174;  // 10 degrees
			
	protected LocateGearDropUsingVisionCommand locateGearDropCommand;
	protected RotateByAngleCommand rotateCommand;
	
	public ScanForGearDropCommand(double incrementAngleInRadians, GearDropConsumer gearDropConsumer) {
		super();
		this.setIncrementAngleInRadians(incrementAngleInRadians);
		this.setGearDropConsumer(gearDropConsumer);
        requires(Robot.driveLine);
        
        locateGearDropCommand = new LocateGearDropUsingVisionCommand(5, this);
        rotateCommand = new RotateByAngleCommand(incrementAngleInRadians);        
	}
	
	@Override 
	protected void initialize() {
		initialGyroAngle = Robot.driveLine.getGyroAngleRadians();
		super.initialize();
	}
	
	public void setIncrementAngleInRadians(double incrementAngleInRadians) {
		this.incrementAngleInRadians = incrementAngleInRadians;
	}
	public Command getFirstCommand() {
		return locatorCommand(); 
	}
	
	@Override
	public Command evaluateNextCommand(Command current) {
		if (finished) {
			return null;
		}
		else if (current == null) {
			return locatorCommand();
		}
		// were we looking for a gear?  Rotate
		else if (current == locateGearDropCommand) {
			if (totalAngleChecked >= Math.PI * 2) {
				// No gear drop found, but we are done
				setGearDrop(null);
				finished = true;			
				return null;
			}
			else {
				totalAngleChecked += incrementAngleInRadians; 			
				return rotateCommand;				
			}
		}
		// were we rotating?  Look for the gear drop again
		else if (current == rotateCommand) {
			return locateGearDropCommand;
		}
		// WTF? return null
		else {
			return null;
		}
	}
		
	protected Command locatorCommand() {
		return new LocateGearDropUsingVisionCommand(5, this);
	}
	
	@Override
	public void setGearDrop(GearDrop gearDrop) {
		if (gearDropConsumer != null) {
			gearDropConsumer.setGearDrop(gearDrop);
		}	
		if (gearDrop != null) {
			finished = true;
		}
	}

	@Override
	public GearDrop getGearDrop() {
		return locateGearDropCommand.getGearDrop();
	}

	@Override
	public void setGearDropConsumer(GearDropConsumer c) {
		gearDropConsumer = c;
		
	}
	
}
