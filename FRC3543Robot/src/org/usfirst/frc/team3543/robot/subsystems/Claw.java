package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.commands.ClawOffCommand;
import org.usfirst.frc.team3543.robot.commands.ClawOpenCommand;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {

	private DoubleSolenoid doubleSolenoid = RobotMap.clawDoubleSolenoid;
	public Compressor airpusher = new Compressor(RobotMap.COMPRESSOR_PORT);
				
	private boolean open = false;
	
	
	@Override
	protected void initDefaultCommand() {		
		// leave for now
		setDefaultCommand(new ClawOpenCommand());
	}

	public boolean isOpen() {
		return open;
	}
	
	public void open() {		
		open = true;
		this.doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void close() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);	
		open = false;
	}
	
	public boolean getPressureSwitchValve() {
		return airpusher.getPressureSwitchValue();
	}
	
	public boolean isCompressorEnabled() {
		return airpusher.enabled();
	}
	
	public void off() {
		doubleSolenoid.set(DoubleSolenoid.Value.kOff);	
		open = false;		
	}
	
	public void setClosedLoopControl(boolean b) {
		airpusher.setClosedLoopControl(b);
	}

	public void startCompressor() {
		airpusher.setClosedLoopControl(true);
		airpusher.start();
	}
	
	public void stopCompressor() {
		airpusher.stop();
	}
	
	public void init() {
		setClosedLoopControl(true);
		startCompressor();
		open();		
	}
	
}
