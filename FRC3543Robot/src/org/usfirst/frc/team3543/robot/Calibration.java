package org.usfirst.frc.team3543.robot;

public class Calibration {
	
	// Driveline
	public static final double DRIVELINE_SENSITIVITY 		= 0.5;
	public static final double DRIVELINE_EXPIRATION 		= 0.1;
	public static final double DRIVELINE_MAX_OUTPUT 		= 1.0;	
	public static final double DRIVELINE_ENCODER_DPP		= 0.0284 * 24/17;
	public static final double DRIVELINE_LEFT_ENCODER_DPP		= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_RIGHT_ENCODER_DPP	= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_GYRO_SENSITIVTY	= 0.007;
	public static final double DRIVELINE_TIMER_DELAY		= 0.01;
	public static final double DRIVELINE_OPEN_LOOP_RAMP	= 1.0;
	// FIXME PID Controllers need to be tuned
	public static final double DRIVELINE_LINEAR_PID_P 	= -0.1;
	public static final double DRIVELINE_LINEAR_PID_I 	= 0.000; 
	public static final double DRIVELINE_LINEAR_PID_D 	= 0.00;
	public static final double DRIVELINE_ROTATION_PID_P 	= 0.1;
	public static final double DRIVELINE_ROTATION_PID_I 	= 0.0;
	public static final double DRIVELINE_ROTATION_PID_D 	= 0.01;
	
	public static final double MAX_SPEED	= 0.5;
	public static final double MIN_SPEED	= -0.5;
	
	public static final double DEFAULT_LINEAR_GAIN		= 0.25;
	public static final double DEFAULT_ROTATOIN_GAIN		= 0.27;

	public static final double INCHES_PER_ROTATION		= 18.8;

	public static final double LINEAR_SENSITIVITY			= 0.75; // inches
	public static final double ROTATION_SENSITIVITY		= Math.toRadians(0.5);	//degrees

	public static final double WRIST_MAX_SPEED			= 0.5; // 50%, for now
	public static final double WRIST_UP_POS				= 0; // degrees
	public static final double WRIST_DOWN_POS			= Math.toRadians(90); // degrees
	public static final double WRIST_DPP				= 0.1;	// FIXME wrist DPP
	public static final double WRIST_OPEN_LOOP_RAMP		= 1;	// second
}
