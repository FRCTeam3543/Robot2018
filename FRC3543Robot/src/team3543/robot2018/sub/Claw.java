package team3543.robot2018.sub;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {
	///////////////// Wiring //////////////
	public static int solenoid1Port 	= 1;
	public static int solenoid2Port 	= 2;
	public static int compressorPort 	= 3;
	public static int switchPort 		= 4;
	
	///////////////// Calibration ////////////////
	// None required
	
	private DoubleSolenoid doubleSolenoid;
	private Compressor airpusher;
	private DigitalInput holdingSwitch;
	
	private boolean open = false;
	
	public Claw() {
		super();
		airpusher = new Compressor(compressorPort);
		doubleSolenoid = new DoubleSolenoid(solenoid1Port, solenoid2Port);
		holdingSwitch = new DigitalInput(switchPort);
	}

	@Override
	protected void initDefaultCommand() {
		// none
		reset();
	}

	public boolean isHoldingSomething() {
		return holdingSwitch.get();
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
	
}
