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

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.RobotMap;
import org.usfirst.frc.team3543.robot.commands.ArcadeDriveWithJoystick;
import org.usfirst.frc.team3543.robot.util.RobotConfig;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
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
	public static final double DEFAULT_ENCODER_DISTANCE_PER_PULSE = 0.0284;
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
		int frontLeftMotor = config.getWiring(LEFT_FRONT_MOTOR_PORT, 1);
		int backLeftMotor = config.getWiring(LEFT_BACK_MOTOR_PORT, 2);
		int frontRightMotor = config.getWiring(RIGHT_FRONT_MOTOR_PORT, 3);
		int backRightMotor = config.getWiring(RIGHT_BACK_MOTOR_PORT, 4);
		int gyro = config.getWiring(GYRO_PORT, 1);		
		int encoderLeft1 = config.getWiring(LEFT_ENCODER_PORT_1);
		int encoderLeft2 = config.getWiring(LEFT_ENCODER_PORT_2);
		int encoderRight1 = config.getWiring(RIGHT_ENCODER_PORT_1);
		int encoderRight2 = config.getWiring(RIGHT_ENCODER_PORT_2);

		frontLeft = new WPI_TalonSRX(frontLeftMotor);       
		backLeft = new WPI_TalonSRX(backLeftMotor);
		frontRight = new WPI_TalonSRX(frontRightMotor);
		backRight = new WPI_TalonSRX(backRightMotor);
		robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

		String name = getName();
		LiveWindow.addActuator(name, "Front Left Motor", frontLeft);
		LiveWindow.addActuator(name, "Back Left Motor", backLeft);
		LiveWindow.addActuator(name, "Front Right Motor", frontRight);
		LiveWindow.addActuator(name, "Back Right Motor", backRight);
		
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(config.getCalibration(EXPIRATION, DEFAULT_EXPIRATION));
		robotDrive.setSensitivity(config.getCalibration(SENSITIVITY, DEFAULT_DRIVE_SENSITIVITY));
		robotDrive.setMaxOutput(config.getCalibration(MAX_OUTPUT, DEFAULT_MAX_OUTPUT));

		timerDelay = config.getCalibration("driveline.timer_delay", DriveLine.DEFAULT_TIMER_DELAY);

		analogGyro = new AnalogGyro(gyro);
		analogGyro.setSensitivity(DEFAULT_GYRO_SENSITIVITY);
		
		LiveWindow.addSensor(name, "Gyro", analogGyro);
		
		defaultLeftEncoderDPP = config.getCalibration(LEFT_ENCODER_DISTANCE_PER_PULSE, DEFAULT_ENCODER_DISTANCE_PER_PULSE);
		defaultRightEncoderDPP = config.getCalibration(RIGHT_ENCODER_DISTANCE_PER_PULSE, DEFAULT_ENCODER_DISTANCE_PER_PULSE);
		
		quadratureEncoderLeft = new Encoder(encoderLeft1, encoderLeft2, false, EncodingType.k2X);	    	
		quadratureEncoderLeft.setDistancePerPulse(defaultLeftEncoderDPP);
		quadratureEncoderLeft.setPIDSourceType(PIDSourceType.kRate);

		quadratureEncoderRight = new Encoder(encoderRight1, encoderRight2, false, EncodingType.k2X);
		quadratureEncoderRight.setDistancePerPulse(defaultRightEncoderDPP);
		quadratureEncoderRight.setPIDSourceType(PIDSourceType.kRate);

		LiveWindow.addSensor(name, "Left Quad Encoder", quadratureEncoderLeft);
		LiveWindow.addSensor(name, "Left Quad Encoder", quadratureEncoderRight);

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
		robotDrive.drive(-magnitude, curve);
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
		double leftDpp = SmartDashboard.getNumber(DASHBOARD_LEFT_ENCODER_DPP, defaultLeftEncoderDPP);
		double rightDpp = SmartDashboard.getNumber(DASHBOARD_RIGHT_ENCODER_DPP, defaultRightEncoderDPP);
		
		this.quadratureEncoderLeft.reset();
		this.quadratureEncoderLeft.setDistancePerPulse(leftDpp);
		this.quadratureEncoderRight.reset();
		this.quadratureEncoderRight.setDistancePerPulse(rightDpp);
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
}


