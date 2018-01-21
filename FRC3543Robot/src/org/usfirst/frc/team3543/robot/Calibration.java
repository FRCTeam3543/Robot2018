package org.usfirst.frc.team3543.robot;

public class Calibration {
	
	public static final double DRIVELINE_SENSITIVITY 		= 0.5;
	public static final double DRIVELINE_EXPIRATION 		= 0.1;
	public static final double DRIVELINE_MAX_OUTPUT 		= 1.0;	
	public static final double DRIVELINE_ENCODER_DPP		= 0.0131;
	public static final double DRIVELINE_LEFT_ENCODER_DPP	= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_RIGHT_ENCODER_DPP	= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_GYRO_SENSITIVTY	= 0.007;
	public static final double DRIVELINE_TIMER_DELAY		= 0.01;
	public static final double DRIVELINE_OPEN_LOOP_RAMP		= 1.0;
	
	public static final double DEFAULT_LINEAR_GAIN			= 0.25;
	public static final double DEFAULT_ROTATOIN_GAIN		= 0.27;

}
