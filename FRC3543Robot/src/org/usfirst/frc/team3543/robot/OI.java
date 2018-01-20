/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3543.robot;


import org.usfirst.frc.team3543.robot.commands.ArcadeDriveWithJoystick;
import org.usfirst.frc.team3543.robot.commands.CircleCommandGroup;
import org.usfirst.frc.team3543.robot.commands.ClawCloseCommand;
import org.usfirst.frc.team3543.robot.commands.ClawOpenCommand;
import org.usfirst.frc.team3543.robot.commands.DriveForwardByDistanceCommand;
import org.usfirst.frc.team3543.robot.commands.DuhCommand;
import org.usfirst.frc.team3543.robot.commands.RotateByAngleCommand;
import org.usfirst.frc.team3543.robot.commands.TankDriveWithJoystick;
import org.usfirst.frc.team3543.robot.util.DegreesToRadiansNumberProvider;
import org.usfirst.frc.team3543.robot.util.NumberProvider;
import org.usfirst.frc.team3543.robot.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * Going to extend SmartDashboard so we can use an instance instead of a static singleton
 */
public class OI {

	// static interface to play nice with the "official way"
	private static OI _instance = new OI();
	public static OI getInstance() {
		return _instance;
	}
	
	public Joystick leftJoystick;
	public Joystick rightJoystick;
	
	public static final int LEFT_JOYSTICK = 0;
	public static final int RIGHT_JOYSTICK = 1;
	public static final int TRIGGER_BUTTON = 1;
	public static final int THUMB_BUTTON = 2;
	public static final int CLOSE_CLAW_BUTTON = THUMB_BUTTON;

	public static final String DEFAULT_LINEAR_GAIN = "Default Linear Gain";
	public static final String DEFAULT_ROTATION_GAIN = "Default Rotation Gain";
	public static final String DRIVELINE_GYRO = "Gyro";
	public static final String LIFT_GAIN = "Lift Gain";
	public static final String DRIVELINE_ENCODER_LEFT = "Left Encoder";
	public static final String DRIVELINE_ENCODER_RIGHT = "Right Encoder";
	public static final String DISTANCE_REMAINING = "Distance Remaining";
	public static final String WHEEL_ENCODER_DISTANCE_PER_PULSE = "Wheel encoder distance per pulse";

	public static final String AUTONOMOUS_MODE = "Autonomous Mode";

	public static final double WHEEL_DISTANCE_PER_PULSE = (6 * Math.PI) / (360 * 0.4615);

	public OI () {
		// Joysticks and buttons
		rightJoystick = new Joystick(RIGHT_JOYSTICK);        
		leftJoystick = new Joystick(LEFT_JOYSTICK);
	}

	public void connectToRobot(Robot robot) {
		
		ArcadeDriveWithJoystick arcade = new ArcadeDriveWithJoystick(robot);
		TankDriveWithJoystick tank = new TankDriveWithJoystick(robot);
		JoystickButton resumeArcadeDriveButton = new JoystickButton(rightJoystick, TRIGGER_BUTTON);
		JoystickButton resumeTankDriveButton = new JoystickButton(leftJoystick, TRIGGER_BUTTON);

		resumeArcadeDriveButton.whenPressed(arcade);
		resumeTankDriveButton.whenPressed(tank);

		JoystickButton closeClawButton = new JoystickButton(rightJoystick, CLOSE_CLAW_BUTTON);
		closeClawButton.whenPressed(new ClawCloseCommand(robot));
		closeClawButton.whenReleased(new ClawOpenCommand(robot));

		NumberProvider linearGainProvider = this.provideNumber("Drive Forward Gain", 
				robot.getConfig().getCalibration("default.linear_gain", RobotMap.DEFAULT_LINEAR_GAIN)
		);
		NumberProvider rotationGainProvider = this.provideNumber("Rotation gain", 
				robot.getConfig().getCalibration("default.rotation_gain", RobotMap.DEFAULT_ROTATION_GAIN)
		);
		NumberProvider forwardDistanceProvider = this.provideNumber("Drive Forward Distance", 12);
		NumberProvider rotationAngleProvider = new DegreesToRadiansNumberProvider(this.provideNumber("Rotate by Angle", 90));
		SmartDashboard.putData("Rotate robot degrees", new RotateByAngleCommand(robot, rotationAngleProvider, rotationGainProvider));			
		SmartDashboard.putData("Drive Forward", new DriveForwardByDistanceCommand(robot, forwardDistanceProvider, linearGainProvider));
		SmartDashboard.putData("Circle Test", new CircleCommandGroup(robot, forwardDistanceProvider, linearGainProvider, rotationGainProvider));
	}

	protected SmartDashboardNumberProvider provideNumber(String forKey, double defaultValue) {
		putNumber(forKey, defaultValue);
		return new SmartDashboardNumberProvider(forKey, defaultValue);
	}
	
	public Joystick getLeftJoystick() {
		return leftJoystick;
	}

	public Joystick getRightJoystick() {
		return rightJoystick;
	}

	public void putDrivelineEncoders(double left, double right) {
		putNumber(OI.DRIVELINE_ENCODER_LEFT, round(left,1));
		putNumber(OI.DRIVELINE_ENCODER_RIGHT, round(right,1));
	}

	public void putDrivelineGyro(double angleInDegrees) {
		putNumber(OI.DRIVELINE_GYRO, round(angleInDegrees,1));
	}

	public double getWheelEncoderDistancePerPulse() {
		return SmartDashboard.getNumber(OI.WHEEL_ENCODER_DISTANCE_PER_PULSE, WHEEL_DISTANCE_PER_PULSE);
	}

	private double round(double num, int decimals) {
		double p = 10;
		if (decimals == 0) p = 1;
		else if (decimals == 1) p = 10;
		else p = Math.pow(10, decimals);
		return Math.round(num * p) / p;
	}

	public void putNumber(String key, double value) {
		SmartDashboard.putNumber(key,value);
	}

	public void putBoolean(String key, boolean value) {
		SmartDashboard.putBoolean(key,value);
	}

	public Command getAutonomousCommand(Robot robot) {
		// FIXME - need to set up the autonomous command here
		return new DuhCommand();
	}

}










