package org.usfirst.frc.team3543.robot;

public class Calibration {
	
	// Driveline
	public static final double DRIVELINE_SENSITIVITY 			= 0.5;	// inches (target tolerance for PID control)
	public static final double DRIVELINE_EXPIRATION 			= 0.1;	// dunno
	public static final double DRIVELINE_MAX_OUTPUT 			= 1.0;	// maxmimum output - trim this to trim max speed
	public static final double DRIVELINE_ENCODER_DPP			= 0.0284 * 24/17;	// this is what we hand-calibrated
	public static final double DRIVELINE_LEFT_ENCODER_DPP		= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_RIGHT_ENCODER_DPP	= DRIVELINE_ENCODER_DPP;
	public static final double DRIVELINE_GYRO_SENSITIVTY		= 0.007;	 // volts/degree/second
	public static final double DRIVELINE_GYRO_GAIN			= 35;	// volts per degree per second based on full turn at 15 degrees off	
	public static final double DRIVELINE_TIMER_DELAY		= 0.01;  // for using the timer delay. Not sure if actually used.
	public static final double DRIVELINE_OPEN_LOOP_RAMP	= 0.6;	 // seconds.  This is the speed ramp rate. 
	// FIXME PID Controllers need to be tuned
	public static final double DRIVELINE_LINEAR_PID_P 	= -0.1;	 // hand tuned, proportional feedback gain
	public static final double DRIVELINE_LINEAR_PID_I 	= 0.000; // hand tuned, integral feedback gain, prob leave at 0
	public static final double DRIVELINE_LINEAR_PID_D 	= 0.00; 	 // hand tuned, deriviative feedback gain, prob leave at 0
	public static final double DRIVELINE_ROTATION_PID_P 	= 0.1;	// 
	public static final double DRIVELINE_ROTATION_PID_I 	= 0.0;
	public static final double DRIVELINE_ROTATION_PID_D 	= 0.01;
	
	public static final double RECORD_MODE_TRIM				= 0.45;
	public static final double ARCADE_ROTATION_TRIM		= 0.95; //scale the rotation axis in the arcade stick, makes it less twitchy
	public static final double MAX_SPEED	= 0.5;
	public static final double MIN_SPEED	= -0.5;
	
	public static final double DEFAULT_LINEAR_GAIN		= 0.25;
	public static final double DEFAULT_ROTATION_GAIN		= 0.27;

	public static final double INCHES_PER_ROTATION		= 18.8;

	public static final double LINEAR_SENSITIVITY			= 0.75; // inches
	public static final double ROTATION_SENSITIVITY		= Math.toRadians(0.5);	//degrees

	public static final double WRIST_MAX_SPEED			= 0.45; // 50%, for now
	public static final double WRIST_UP_POS				= 0; // degrees
	public static final double WRIST_DOWN_POS				= 1.17; //1.745; //Math.toRadians(90); // degrees
//	public static final double WRIST_DPP					= 0.0000681;	// wrist DPP
	public static final double WRIST_DPP					= 0.007;	// wrist DPP
	
	public static final double WRIST_OPEN_LOOP_RAMP		= 0.05;	// second
	
	public static final double WRIST_PID_P = -0.6;		// FIXME
	public static final double WRIST_PID_I = 0.00000001;		// FIXME
	public static final double WRIST_PID_D = 0;		// FIXME
	public static final double WRIST_TOLERANCE = 0.1;
	
	public static final double LIFT_MAX_SPEED			= 1; // 50%, for now
	public static final double LIFT_MAX_SPEED_UP		= LIFT_MAX_SPEED; // 50%, for now
	public static final double LIFT_MAX_SPEED_DOWN		= LIFT_MAX_SPEED * 0.8; // 50%, for now
	public static final double LIFT_CLIMB_SPEED			= LIFT_MAX_SPEED; // 50%, for now
	
	public static final double LIFT_UP_POS				= 135; // inches
	public static final double LIFT_DOWN_POS			= 0; // inches
	public static final double LIFT_DPP					= 0.00302;	// FIXME wrist DPP
	public static final double LIFT_OPEN_LOOP_RAMP		= 0.5;	// second
	
	public static final double LIFT_PID_P = 0.1;		// FIXME
	public static final double LIFT_PID_I = 0;		// FIXME
	public static final double LIFT_PID_D = 0;		// FIXME
	
	public static final double ULTRASONIC_CALIBRATION 	= 1.0;
	public static final double OBJECT_IN_RANGE 			= 0.1; 
}
