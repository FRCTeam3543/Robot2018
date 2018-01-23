package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.Wiring;
import org.usfirst.frc.team3543.robot.commands.ClawOffCommand;
import org.usfirst.frc.team3543.robot.commands.ClawOpenCommand;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends BaseSubsystem {
	// configuration keys
	public static final String COMPRESSOR_PORT = "claw.compressor_port";
	public static final String SOLENOID_1_PORT = "claw.solenoid_1_port";
	public static final String SOLENOID_2_PORT = "claw.solenoid_2_port";
	
	private DoubleSolenoid doubleSolenoid;
	public Compressor airpusher;
				
	private boolean open = false;
	
	public Claw(RobotConfig config) {
		super(config);
		airpusher = new Compressor(Wiring.CLAW_COMPRESSOR_PORT);
		doubleSolenoid = new DoubleSolenoid(Wiring.CLAW_SOLENOID_1_PORT, Wiring.CLAW_SOLENOID_2_PORT);
		updateOperatorInterface();
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
	
	public void reset() {
		this.init();
	}

	public void init() {
		setClosedLoopControl(true);
		startCompressor();
		open();		
	}
	
	@Override
	public void updateOperatorInterface() {
		
	}
}
