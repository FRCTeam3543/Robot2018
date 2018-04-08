package team3543.robot2018.sub;

import org.usfirst.frc.team3543.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3543.robot.cal.PIDF;

public class Lift extends Subsystem {
	////////////////// Wiring ////////////////////////
	public static int motorPort 		= 1;
	public static int encoderPortA 		= 2;
	public static int encoderPortB 		= 3;
	public static int lowSwitchPort		= 4;
	public static int highSwitchPort	= 4;
	
	////////////////// Calibration ////////////////////
	public static double max_speed 				= 1;
	public static double encoderDPP 			= 0.1;
	public static double pidPercentTolerance 	= 1;	// percent
	public PIDF pidf = new PIDF(0,0,0,0,1);	
	
	////////////////// Components ///////////////////
	WPI_TalonSRX motorController;
	Encoder encoder;
	DigitalInput lowSwitch, highSwitch;
	PIDController liftPID;
	
	public Lift() {
		super();
		motorController = new WPI_TalonSRX(motorPort);
		encoder = new Encoder(encoderPortA, encoderPortB, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(encoderDPP);
		encoder.reset();
		
		lowSwitch = new DigitalInput(lowSwitchPort);
		highSwitch = new DigitalInput(highSwitchPort);
				
		liftPID = new PIDController(pidf.kP, pidf.kI, pidf.kD, pidf.kF, 
						this.getPIDSource(), this.getPIDOutput());
		liftPID.setPercentTolerance(pidPercentTolerance);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		encoder.reset();
		off();
	}
	
	public boolean isUp() {
		return highSwitch.get();
	}

	public boolean isDown() {
		return lowSwitch.get();
	}
	
	public void go_up() {
		go_up(max_speed);
	}

	public void go_down() {
		go_down(max_speed);
	}
	
	public void stop() {
		setSpeed(0);
	}
	
	void disablePID() {
		liftPID.disable();		
	}
	
	public void setSpeed(double speed) {
		disablePID();
		motorController.set(ControlMode.PercentOutput, speed);
	}
	
	public void off() {
		setSpeed(0);
	}
	
	public void go_up(double speed) {
		// go up, but only if not at the up limit
		if (!isUp()) {
			// todo this should ramp
			setSpeed(-speed);
		}
		else {
			Robot.LOGGER.info("Lift in up position");
		}		
	}
	
	public void go_down(double speed) {
		if (!isDown()) {
			setSpeed(speed);
		}
		else {
			Robot.LOGGER.info("Lift in down position");
		}
	}

	public void pidControl(double setpoint) {
		liftPID.setSetpoint(setpoint);
		if (!liftPID.isEnabled()) liftPID.enable(); 
	}
	
	private PIDSource getPIDSource() {
		return new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				pidSourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return pidSourceType;
			}

			@Override
			public double pidGet() {
				return encoder.getDistance();
			}
			
		};
	}
	
	private PIDOutput getPIDOutput() {
		return new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				motorController.set(ControlMode.PercentOutput, output);
			}			
		};
	}
	
	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
}
