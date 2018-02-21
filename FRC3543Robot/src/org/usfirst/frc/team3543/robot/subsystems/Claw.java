package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Wiring;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Claw extends BaseSubsystem {
	// configuration keys
	public static final String COMPRESSOR_PORT = "claw.compressor_port";
	public static final String SOLENOID_1_PORT = "claw.solenoid_1_port";
	public static final String SOLENOID_2_PORT = "claw.solenoid_2_port";
	
	private DoubleSolenoid doubleSolenoid;
	public Compressor airpusher;
				
	private boolean open = false;
	
	public Claw() {
		super();
		airpusher = new Compressor(Wiring.CLAW_COMPRESSOR_PORT);
		doubleSolenoid = new DoubleSolenoid(Wiring.CLAW_SOLENOID_1_PORT, Wiring.CLAW_SOLENOID_2_PORT);
		updateOperatorInterface();
		LiveWindow.addActuator(getName(), "Compressor", airpusher);
		LiveWindow.addActuator(getName(), "Double Solenoid", doubleSolenoid);
		
	}

	@Override
	protected void initDefaultCommand() {
		// none
		reset();
	}

	public boolean isOpen() {
		return open;
	}
	
	public void open() {		
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);			
		open = true;
	}
	
	public void close() {
		this.doubleSolenoid.set(DoubleSolenoid.Value.kReverse);		
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
	
	public void reset() {
		this.init();
	}

	public void init() {
		setClosedLoopControl(true);	
		startCompressor();
		close();		
	}
	
	@Override
	public void updateOperatorInterface() {
		
	}
}
