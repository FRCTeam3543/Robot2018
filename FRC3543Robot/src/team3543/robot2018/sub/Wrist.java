package team3543.robot2018.sub;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;
import org.usfirst.frc.team3543.robot.Wiring;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import team3543.robot.cal.PIDF;

/** 
 * TODO Need switches for top and bottom
 * @author mk
 */
public class Wrist extends Subsystem {
	/////////////////// Wiring ///////////
	public static int motorPort			= 1;
	public static int encoderPortA		= 2;
	public static int encoderPortB		= 3;
	
	////////////////// Calibration ////////////
	public static double encoderDPP 	= 0.004; // FIXME
	public static double percentTolerance = 1;	// percent
	public static double upPosition 	= 1;
	public static double downPosition 	= -1; // FIXME
	public static PIDF pidGains	= new PIDF(0,0,0,0, 1);

	///////////////// Components ///////////////
	WPI_TalonSRX motorController;
	Encoder encoder;
	double max_speed = Calibration.WRIST_MAX_SPEED;
	PIDController wristPID;
	boolean closedLoop = false;
	
	public Wrist() {
		super();
		motorController = new WPI_TalonSRX(motorPort);		
		motorController.set(ControlMode.PercentOutput, 0);
		encoder = new Encoder(encoderPortA, encoderPortA, false, EncodingType.k2X);
		
		encoder.setDistancePerPulse(encoderDPP);
		encoder.reset();
		
		wristPID = new PIDController(pidGains.kP, pidGains.kI, pidGains.kD, pidGains.kF,
				this.initPIDSource(), this.initPIDOutput()				
		);
		wristPID.setAbsoluteTolerance(pidGains.tolerance);
	}
		
	public void disableSetpoint() {
		this.wristPID.disable();
		this.closedLoop = false;		
	}
	
	public void setSetpoint(double value) {
		this.wristPID.setSetpoint(value);
		this.wristPID.enable();		
		this.closedLoop = true;
	}
	
	public boolean isAtSetpoint() {
		return this.wristPID.onTarget();
	}
	
	public boolean isSetpointEnabled() {
		return this.closedLoop;
	}
	
	public void go_up() {
		go_up(Calibration.WRIST_MAX_SPEED);
	}

	public void go_down() {
		go_down(Calibration.WRIST_MAX_SPEED);
	}
	
	public void setSpeed(double speed) {
		speed = Math.min(Calibration.WRIST_MAX_SPEED, Math.max(speed, -Calibration.WRIST_MAX_SPEED));
		Robot.LOGGER.info("SET SPEED " + speed);
		motorController.set(speed);
	}
	
	public void off() {
		setSpeed(0);
	}
	
	@Override
	public void periodic() {
		super.periodic();
	}

	public boolean isUp() {
		return false; // FIXME
	}
	
	public boolean isDown() {
		return false; // FIXME
	}
	
	public void go_up(double speed) {
		// go up, but only if not at the up limit
		if (!isUp()) {
			// todo this should ramp
			setSpeed(-speed);
		}
		else {
			Robot.LOGGER.info("Wrist in up position");
		}		
	}
	
	public void go_down(double speed) {
		if (!isDown()) {
			setSpeed(speed);
		}
		else {
			Robot.LOGGER.info("Wrist in down position");
		}
	}

	public void resetEncoder() {
		encoder.reset();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}

	private PIDSource initPIDSource() {
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
	
	private PIDOutput initPIDOutput() {
		return new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				motorController.set(ControlMode.PercentOutput, output);
			}					
		};
	}
	
	PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

}
