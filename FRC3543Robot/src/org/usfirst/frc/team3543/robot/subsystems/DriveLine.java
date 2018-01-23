// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team3543.robot.subsystems;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.Wiring;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main DriveLine.  Packages the 4 drive CANTalons, left/right quadrature encoders and gyro
 * 
 * Note that all the commands that use the drive line use high-level methods in this
 * class, they don't tale to the RobotDrive (etc.) directly.  Putting in high-level
 * methods is a good idea because it allows you to abstract the underlying actuators
 * from the commands.
 * 
 * @author MK
 *
 */
public class DriveLine extends BaseSubsystem {
	// Configuration keys
	public static final String SENSITIVITY = "driveline.sensitivity";	
	public static final String EXPIRATION = "driveline.expiration";	
	public static final String MAX_OUTPUT = "driveline.max_output";	

	public static final String LEFT_FRONT_MOTOR_PORT = "driveline.motor.left_front";
	public static final String LEFT_BACK_MOTOR_PORT = "driveline.motor.left_back";
	public static final String RIGHT_FRONT_MOTOR_PORT = "driveline.motor.right_front";
	public static final String RIGHT_BACK_MOTOR_PORT = "driveline.motor.right_back";
	public static final String GYRO_PORT = "driveline.gyro.port";
	public static final String GYRO_SENSITIVITY = "driveline.gyro.sensitivity";
	public static final String LEFT_ENCODER_DISTANCE_PER_PULSE = "driveline.left_encoder.distance_per_pulse";	
	public static final String RIGHT_ENCODER_DISTANCE_PER_PULSE = "driveline.right_encoder.distance_per_pulse";
	public static final String LEFT_ENCODER_PORT_1 = "driveline.left_encoder.port_1";	
	public static final String LEFT_ENCODER_PORT_2 = "driveline.left_encoder.port_2";
	public static final String RIGHT_ENCODER_PORT_1 = "driveline.right_encoder.port_1";	
	public static final String RIGHT_ENCODER_PORT_2 = "driveline.right_encoder.port_2";

	// Configuration defaults
	public static final double DEFAULT_ENCODER_DISTANCE_PER_PULSE = 0.0131;
	public static final double DEFAULT_GYRO_SENSITIVITY = 0.007;
	public static final double DEFAULT_DRIVE_SENSITIVITY = 0.5;
	public static final double DEFAULT_EXPIRATION = 0.1;
	public static final double DEFAULT_MAX_OUTPUT = 1.0;
    public static final double DEFAULT_TIMER_DELAY = 0.01;	// delay passed to Timer.delay() on driveline calls
	
	// Smart Dashboard stuff
	public static final String DASHBOARD_LEFT_ENCODER_DPP = "Driveline Left Quad DPP";
	public static final String DASHBOARD_RIGHT_ENCODER_DPP = "Driveline Right Quad DPP";
	public static final String DASHBOARD_LEFT_WHEEL_DISTANCE = "Driveline Left Wheel Distance";
	public static final String DASHBOARD_RIGHT_WHEEL_DISTANCE = "Driveline Right Wheel Distance";
	
	public static final String DASHBOARD_GYRO_ANGLE = "Driveline Gyro Angle";
	
	// Properties
	private double defaultLeftEncoderDPP;
	private double defaultRightEncoderDPP;
	
	private WPI_TalonSRX frontLeft;
	private WPI_TalonSRX backLeft;
	private WPI_TalonSRX frontRight;
	private WPI_TalonSRX backRight;
	private RobotDrive robotDrive;
	private AnalogGyro analogGyro;
	private Encoder quadratureEncoderLeft;
	private Encoder quadratureEncoderRight;

	private double gyroSensitivity = RobotMap.GYRO_SENSITIVITY;
	private double gyroGain = RobotMap.GYRO_FEEDBACK_GAIN;

	private double timerDelay = DEFAULT_TIMER_DELAY;

	public DriveLine(RobotConfig config) {
		super(config);

		// Initialize the robot
		int frontLeftMotor = Wiring.DRIVELINE_MOTOR_LEFT_FRONT;
		int backLeftMotor = Wiring.DRIVELINE_MOTOR_LEFT_BACK;
		int frontRightMotor = Wiring.DRIVELINE_MOTOR_RIGHT_FRONT;
		int backRightMotor = Wiring.DRIVELINE_MOTOR_RIGHT_BACK;
		int gyro = Wiring.DRIVELINE_GYRO_PORT;	
		int encoderLeft1 = Wiring.DRIVELINE_LEFT_ENCODER_PORT_1;
		int encoderLeft2 = Wiring.DRIVELINE_LEFT_ENCODER_PORT_2;
		int encoderRight1 = Wiring.DRIVELINE_RIGHT_ENCODER_PORT_1;
		int encoderRight2 = Wiring.DRIVELINE_RIGHT_ENCODER_PORT_2;

		frontLeft = new WPI_TalonSRX(frontLeftMotor);       
		backLeft = new WPI_TalonSRX(backLeftMotor);		
		backLeft.follow(frontLeft);
		
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontLeft.configOpenloopRamp(Calibration.DRIVELINE_OPEN_LOOP_RAMP, 0);
		backLeft.configOpenloopRamp(0, 0);
		
		frontRight = new WPI_TalonSRX(frontRightMotor);
		backRight = new WPI_TalonSRX(backRightMotor);
		backRight.follow(frontRight);		
		frontRight.setNeutralMode(NeutralMode.Brake);
		frontRight.configOpenloopRamp(Calibration.DRIVELINE_OPEN_LOOP_RAMP, 0);
		backRight.configOpenloopRamp(0, 0);
				
		robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

		String name = getName();
		LiveWindow.addActuator(name, "Front Left Motor", frontLeft);
		LiveWindow.addActuator(name, "Back Left Motor", backLeft);
		LiveWindow.addActuator(name, "Front Right Motor", frontRight);
		LiveWindow.addActuator(name, "Back Right Motor", backRight);
		
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(Calibration.DRIVELINE_EXPIRATION);
		robotDrive.setSensitivity(Calibration.DRIVELINE_SENSITIVITY);
		robotDrive.setMaxOutput(Calibration.DRIVELINE_MAX_OUTPUT);

		timerDelay = Calibration.DRIVELINE_TIMER_DELAY;

		analogGyro = new AnalogGyro(gyro);
		analogGyro.setSensitivity(Calibration.DRIVELINE_GYRO_SENSITIVTY);
		
		LiveWindow.addSensor(name, "Gyro", analogGyro);
		
		defaultLeftEncoderDPP = Calibration.DRIVELINE_LEFT_ENCODER_DPP;
		defaultRightEncoderDPP = Calibration.DRIVELINE_RIGHT_ENCODER_DPP;
		
		quadratureEncoderLeft = new Encoder(encoderLeft1, encoderLeft2, false, EncodingType.k2X);	    	
		quadratureEncoderLeft.setDistancePerPulse(defaultLeftEncoderDPP);
		quadratureEncoderLeft.setPIDSourceType(PIDSourceType.kRate);

		quadratureEncoderRight = new Encoder(encoderRight1, encoderRight2, false, EncodingType.k2X);
		quadratureEncoderRight.setDistancePerPulse(defaultRightEncoderDPP);
		quadratureEncoderRight.setPIDSourceType(PIDSourceType.kRate);

		// enable quad encoder feedback
		// FIXME - we need to confirm we've connect the AM-E4T's to the Talons and not the Rio via a am-2633
		// http://www.andymark.com/product-p/am-3
		// from the manual - The Talon directly supports Quadrature Encoders. 
		// If Quadrature is selected, the decoding is done in 4x mode. 
		// This means that each pulse will correspond to four counts.
//		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
//		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		LiveWindow.addSensor(name, "Left Quad Encoder", quadratureEncoderLeft);
		LiveWindow.addSensor(name, "Left Quad Encoder", quadratureEncoderRight);
	}

	public WPI_TalonSRX getLeftMotor() {
		return this.frontLeft;
	}
	
	public WPI_TalonSRX getRightMotor() {
		return this.frontRight;
	}
		
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		this.analogGyro.calibrate(); 	
	}

	public double getTimerDelay() {
		return this.timerDelay;
	}
	
	public void tankDrive(Joystick left, Joystick right) {
		robotDrive.tankDrive(left, right); // 
		updateOperatorInterface();
	}

	public void drive(double magnitude, double curve) {
		// Here we reverse magnitude, because the motors are inverted on our bot
		double clipped = Math.max(Calibration.MIN_SPEED, Math.min(Calibration.MAX_SPEED, magnitude));
		robotDrive.drive(-clipped, curve);
		updateOperatorInterface();
	}

	public void drive(double speed) {
		drive(speed, 0);
	}

	public void rotate(int dir, double speed) {
		drive(speed, dir < 0 ? -1 : 1);
	}

	public void rotateClockwise(double speed) {
		rotate(-1, speed);
	}

	public void rotateCounterClockwise(double speed) {
		rotate(1, speed);
	}

	public void driveStraight(double speed) {
		// see http://wpilib.screenstepslive.com/s/3120/m/7912/l/85772-gyros-to-control-robot-driving-direction
		double angle = getGyroAngle(); 
		double rot = -angle * gyroSensitivity * gyroGain;
		rot = Math.min(1, Math.max(-1, rot));// between -1 and 1
		drive(speed, rot);    	
	}

	public void turnInPlace(double speed) {
		tankDrive(speed, -speed);
	}

	public void tankDrive(double lspeed, double rspeed) {
		robotDrive.tankDrive(lspeed, rspeed);
	}

	public void tankDrive(double speed) {
		tankDrive(speed, speed);
	}

	public void arcadeDrive(Joystick stick) {
		robotDrive.arcadeDrive(stick);
		updateOperatorInterface();		
	}

	public void stop() {
		robotDrive.stopMotor();
		updateOperatorInterface();		
	}

	public double getGyroAngle() {
		return analogGyro.getAngle();
	}

	public double getGyroAngleRadians() {
		return Math.toRadians(getGyroAngle());
	}

	public void init() {
		robotDrive.setSafetyEnabled(false);
	}

	public void resetEncoders() {
//		double leftDpp = SmartDashboard.getNumber(DASHBOARD_LEFT_ENCODER_DPP, defaultLeftEncoderDPP);
//		double rightDpp = SmartDashboard.getNumber(DASHBOARD_RIGHT_ENCODER_DPP, defaultRightEncoderDPP);
		
		this.quadratureEncoderLeft.reset();		
//		this.quadratureEncoderLeft.setDistancePerPulse(leftDpp);
		this.quadratureEncoderRight.reset();
//		this.quadratureEncoderRight.setDistancePerPulse(rightDpp);
		updateOperatorInterface();
	}

	public void doTimerDelay() {
		Timer.delay(this.getTimerDelay());
	}
	
	public void resetGyro() {
		this.analogGyro.reset();
		this.updateOperatorInterface();
	}

	public double getLeftEncoderValue() {
		return this.quadratureEncoderLeft.getDistance();
	}

	public double getRightEncoderValue() {
		return this.quadratureEncoderRight.getDistance();
	}

	/**
	 * @deprecated
	 */
	public void updateDashboard() {
		this.updateOperatorInterface();
	}

	public void resetAll() {
		// followers should follow
		frontLeft.set(ControlMode.PercentOutput,0);
		frontRight.set(ControlMode.PercentOutput,0);
		resetEncoders();
		resetGyro();		
		updateOperatorInterface();		
	}
	
	@Override
	public void updateOperatorInterface() {		
		SmartDashboard.putNumber(DASHBOARD_LEFT_ENCODER_DPP,this.quadratureEncoderLeft.getDistancePerPulse());
		SmartDashboard.putNumber(DASHBOARD_RIGHT_ENCODER_DPP,this.quadratureEncoderRight.getDistancePerPulse());
		SmartDashboard.putNumber(DASHBOARD_LEFT_WHEEL_DISTANCE,this.getLeftEncoderValue());
		SmartDashboard.putNumber(DASHBOARD_RIGHT_WHEEL_DISTANCE,this.getRightEncoderValue());
		SmartDashboard.putNumber(DASHBOARD_GYRO_ANGLE, this.getGyroAngle());	
	}

	public double getGyroSensitivity() {
		return this.gyroSensitivity;
	}

	public double[] getPositionAndVelocity() {
		return new double[] {
				getLeftMotor().getSelectedSensorPosition(0),				
				getLeftMotor().getSelectedSensorVelocity(0),
				getRightMotor().getSelectedSensorPosition(0),
				getRightMotor().getSelectedSensorVelocity(0)
		};
		
	}

}


