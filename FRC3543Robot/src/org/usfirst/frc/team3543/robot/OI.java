/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3543.robot;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team3543.robot.commands.ArcadeDriveWithJoystick;
import org.usfirst.frc.team3543.robot.commands.CircleCommandGroup;
import org.usfirst.frc.team3543.robot.commands.ClawCloseCommand;
import org.usfirst.frc.team3543.robot.commands.ClawOpenCommand;
import org.usfirst.frc.team3543.robot.commands.ControlWristCommand;
import org.usfirst.frc.team3543.robot.commands.DriveForwardByDistanceCommand;
import org.usfirst.frc.team3543.robot.commands.DriveForwardByDistanceUsingPIDCommand;
import org.usfirst.frc.team3543.robot.commands.DuhCommand;
import org.usfirst.frc.team3543.robot.commands.ExecOnceCommand;
import org.usfirst.frc.team3543.robot.commands.PlaybackCommand;
import org.usfirst.frc.team3543.robot.commands.RotateByAngleCommand;
import org.usfirst.frc.team3543.robot.commands.RotateByAngleUsingPIDCommand;
import org.usfirst.frc.team3543.robot.commands.TankDriveWithJoystick;
import org.usfirst.frc.team3543.robot.oi.PathPlaybackSendableChooser;
import org.usfirst.frc.team3543.robot.util.DegreesToRadiansNumberProvider;
import org.usfirst.frc.team3543.robot.util.NumberProvider;
import org.usfirst.frc.team3543.robot.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	
	StringBuilder log = new StringBuilder();
	
	public static final int LEFT_JOYSTICK = 0;
	public static final int RIGHT_JOYSTICK = 1;
	public static final int TRIGGER_BUTTON = 1;
	public static final int THUMB_BUTTON = 2;
	public static final int LOG_BUTTON = 6; // thumb on left joystick logs
	
	public static final int WRIST_CONTROL_BUTTON = 2;
	
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

	public String recordedPath = Path.NONE;
	
	ControlWristCommand controlWrist;
	ArcadeDriveWithJoystick arcadeDrive;
	
	public OI () {
		// Joysticks and buttons
		rightJoystick = new Joystick(RIGHT_JOYSTICK);        
		leftJoystick = new Joystick(LEFT_JOYSTICK);
	}

	public void connectToRobot(Robot robot) {
		
		arcadeDrive = new ArcadeDriveWithJoystick(robot);
//		TankDriveWithJoystick tank = new TankDriveWithJoystick(robot);
		JoystickButton resumeArcadeDriveButton = new JoystickButton(rightJoystick, TRIGGER_BUTTON);
//		JoystickButton resumeTankDriveButton = new JoystickButton(leftJoystick, THUMB_BUTTON);

		resumeArcadeDriveButton.whenPressed(arcadeDrive);
//		resumeTankDriveButton.whenPressed(tank);

		JoystickButton closeClawButton = new JoystickButton(leftJoystick, TRIGGER_BUTTON);
		closeClawButton.whenPressed(new ClawCloseCommand(robot));
		closeClawButton.whenReleased(new ClawOpenCommand(robot));

		NumberProvider forwardDistanceProvider = this.provideNumber("Drive Forward Distance", 12);
		NumberProvider rotationAngleProvider = new DegreesToRadiansNumberProvider(this.provideNumber("Rotate by Angle", 90));
		SmartDashboard.putData("Rotate robot degrees", new RotateByAngleUsingPIDCommand(robot, rotationAngleProvider));			
		SmartDashboard.putData("Drive Forward", new DriveForwardByDistanceUsingPIDCommand(robot, forwardDistanceProvider));
		SmartDashboard.putData("Circle Test", new CircleCommandGroup(robot, forwardDistanceProvider));

		
		// wrist hookup
		JoystickButton wristButton = new JoystickButton(leftJoystick, 2);		
		controlWrist = new ControlWristCommand(robot, leftJoystick);
		wristButton.whileHeld(controlWrist);
		
		initRecordAndPlayback(robot);
	}

	PathPlaybackSendableChooser pathPlaybackChooser = new PathPlaybackSendableChooser();
	String[] emptyPathList = {};
	Map<String, String> pathMap = new HashMap<String, String>();
	
	protected void updatePlaybackChooser() {
		pathPlaybackChooser = new PathPlaybackSendableChooser();
		pathMap.clear();
		String [] paths = SmartDashboard.getStringArray("Paths", emptyPathList);
		Path p;
		for (String path : paths) {
			p = Path.parse(path);
			pathMap.put(p.getName(), path);			
		}
	}
	
	public void savePathMap() {
		List<String> savedPaths = new ArrayList<>();		
		String path;
		for (String s : pathMap.keySet()) {
			path = pathMap.get(s);
			pathPlaybackChooser.addObject(s, Path.parse(path));
			savedPaths.add(path);
		}
		SmartDashboard.putData("Path Playback", pathPlaybackChooser);
		SmartDashboard.putStringArray("Paths", (String[])savedPaths.toArray());				
	}
	
	protected void initRecordAndPlayback(Robot robot) {
		SmartDashboard.putString("LOG", "Log off");
		SmartDashboard.putString("Recorded Path", "");
		SmartDashboard.setPersistent("Paths");
		SmartDashboard.putStringArray("Paths", emptyPathList);
		// a string field where we can put the name to save the last recorded path under
		SmartDashboard.putString("Save Path As", "");
		
		// Our button to save the path.  To overwrite the path enter the name here
		SmartDashboard.putData("Save path", new ExecOnceCommand() {
			
			@Override
			protected void executeOnce() {
				// get the list of paths
				String pathName = SmartDashboard.getString("Save Path As", String.format("New path %s", pathMap.size()+1));
				String path = SmartDashboard.getString("Recorded Path", "");
				if (!pathName.equals("") && !path.equals("") && !pathName.contains("::")) {
					// apply the name
					Path p = Path.parse(path);
					p.setName(pathName);									
					pathMap.put(p.getName(), p.export());
					savePathMap();
					updatePlaybackChooser();
				}				
			}			
		});

		// remove a selected path.  It is the one selected in the chooser
		SmartDashboard.putData("Delete path", new ExecOnceCommand() {

			@Override
			protected void executeOnce() {
				// get the list of paths
				Path selected = pathPlaybackChooser.getSelected();
				if (selected != null) {					
					// do we have this in the hash?
					if (pathMap.containsKey(selected.getName())) {
						pathMap.remove(selected.getName());
					}
					savePathMap();
					updatePlaybackChooser();					
				}
			}			
		});		
		// record
		JoystickButton logButton = new JoystickButton(leftJoystick, LOG_BUTTON);
		Command toggleLoggingCommand = new Command() {
			
			public void start() {				
				super.start();				
				Robot.logging = true;
				if (!robot.getDriveLine().isRecording()) {
					Robot.log("---- LOG START ----");
					robot.getDriveLine().startRecording();		
					SmartDashboard.putString("Recorded Path", "");					
				}
			}
						
			public void execute() {
//				record(robot);
			}

			public void cancel() {
				super.cancel();
				done();
			}
			
			public void end() {
				super.end();
				done();
			}
		
			void done() {
				Robot.logging = false;
				Path p = robot.getDriveLine().stopRecording();
				if (p != null) {
					recordedPath = p.export();
					
					Robot.log(	"--- PATH START ---\n"
								+recordedPath
								+"\n---- PATH END ----");	
					SmartDashboard.putString("Recorded Path", recordedPath);										
				}
				SmartDashboard.putString("RECORDING", "OFF");	
			}
			@Override
			protected boolean isFinished() {
				return false;
			}			
		};
		logButton.whileHeld(toggleLoggingCommand);		
		// playback
		JoystickButton pathButton = new JoystickButton(leftJoystick, 7);
		pathButton.whenPressed(new PlaybackCommand(robot, new PathProvider() {
			@Override
			public Path getPath() {
				return getPathPlaybackChooser().getPath();
			}
			
		}));
		
		updatePlaybackChooser();
	}
	
	protected PathPlaybackSendableChooser getPathPlaybackChooser() {
		return pathPlaybackChooser;
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










