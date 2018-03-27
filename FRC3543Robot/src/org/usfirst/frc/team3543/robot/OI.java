package org.usfirst.frc.team3543.robot;

import org.usfirst.frc.team3543.robot.commands.ArcadeDriveWithJoystick;
import org.usfirst.frc.team3543.robot.commands.ClawCloseCommand;
import org.usfirst.frc.team3543.robot.commands.ClawOpenCommand;
import org.usfirst.frc.team3543.robot.commands.ControlWristCommand;
import org.usfirst.frc.team3543.robot.commands.DriveForwardByDistanceUsingPIDCommand;
import org.usfirst.frc.team3543.robot.commands.LiftDownCommand;
import org.usfirst.frc.team3543.robot.commands.LiftUpCommand;
import org.usfirst.frc.team3543.robot.commands.PlaceAutonomousCommand;
import org.usfirst.frc.team3543.robot.commands.PlaybackCommand;
import org.usfirst.frc.team3543.robot.oi.PathPlaybackSendableChooser;
import org.usfirst.frc.team3543.robot.oi.StartingPositionSendableChooser;
import org.usfirst.frc.team3543.robot.util.DegreesToRadiansNumberProvider;
import org.usfirst.frc.team3543.robot.util.NumberProvider;
import org.usfirst.frc.team3543.robot.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.Joystick;
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

	//////////////////////////////////////////
	// Joystick Button Assignments
	//////////////////////////////////////////
	// Generic
	public static final int LEFT_JOYSTICK 		= 0;
	public static final int RIGHT_JOYSTICK 		= 1;
	
	public static final int TRIGGER_BUTTON 				= 1;
	public static final int THUMB_LOWER_BUTTON 			= 3;
	public static final int THUMB_UPPER_BUTTON 			= 5;
	public static final int THUMB_LEFT_BUTTON 			= 4;
	public static final int THUMB_RIGHT_BUTTON 			= 2;
	public static final int SIDE_LEFT_UPPER_BUTTON 		= 6;
	public static final int SIDE_LEFT_LOWER_BUTTON 		= 7;
	public static final int SIDE_RIGHT_UPPER_BUTTON 	= 10;
	public static final int SIDE_RIGHT_LOWER_BUTTON 	= 8;
	public static final int BOTTOM_LEFT_BUTTON 			= 11;
	public static final int BOTTOM_RIGHT_BUTTON			= 9;
	
	// Assigned
	public static final int RECORD_BUTTON 			= SIDE_LEFT_UPPER_BUTTON; 
	public static final int PLAYBACK_BUTTON 		= SIDE_LEFT_LOWER_BUTTON; 	
	public static final int LIFT_UP_BUTTON 			= THUMB_UPPER_BUTTON; 
	public static final int LIFT_DOWN_BUTTON 		= THUMB_LOWER_BUTTON; 
	public static final int LIFT_CLIMB_BUTTON 		= BOTTOM_LEFT_BUTTON;	
	public static final int WRIST_CONTROL_BUTTON 	= THUMB_RIGHT_BUTTON;	
	public static final int CLAW_BUTTON 			= TRIGGER_BUTTON;
	
	public Joystick leftJoystick;
	public Joystick rightJoystick;	
	StringBuilder log = new StringBuilder();	

	public static final String DEFAULT_LINEAR_GAIN = "Default Linear Gain";
	public static final String DEFAULT_ROTATION_GAIN = "Default Rotation Gain";
	public static final String DRIVELINE_GYRO = "Gyro";
	public static final String LIFT_GAIN = "Lift Gain";
	public static final String DRIVELINE_ENCODER_LEFT = "Left Encoder";
	public static final String DRIVELINE_ENCODER_RIGHT = "Right Encoder";
	public static final String DISTANCE_REMAINING = "Distance Remaining";
	public static final String WHEEL_ENCODER_DISTANCE_PER_PULSE = "Wheel encoder distance per pulse";
	public static final String AUTONOMOUS_MODE = "Autonomous Mode";

	public String recordedPath = RecordedPaths.NONE; // The "None" path
	PathPlaybackSendableChooser pathPlaybackChooser = new PathPlaybackSendableChooser();
	StartingPositionSendableChooser startingPositionSendableChooser = new StartingPositionSendableChooser();
			
	public OI () {
		// Joysticks and buttons
		rightJoystick = new Joystick(RIGHT_JOYSTICK);        
		leftJoystick = new Joystick(LEFT_JOYSTICK);
	}

	public void connectToRobot(Robot robot) {	
		// these are separated into different methods so easier to read	
		initDrive(robot);
		initClaw(robot);
		initWrist(robot);
		initRecordAndPlayback(robot);
		initLift(robot);
	}

	protected void initDrive(Robot robot) {
		// arcade drive is initialized by the right trigger button
		ArcadeDriveWithJoystick arcadeDrive;
		arcadeDrive = new ArcadeDriveWithJoystick(robot);
		JoystickButton resumeArcadeDriveButton = new JoystickButton(rightJoystick, TRIGGER_BUTTON);
		resumeArcadeDriveButton.whenPressed(arcadeDrive);

		NumberProvider forwardDistanceProvider = this.provideNumber("Drive Forward Distance", 12);
//		NumberProvider rotationAngleProvider = new DegreesToRadiansNumberProvider(this.provideNumber("Rotate by Angle", 90));
		SmartDashboard.putData("Drive Forward", new DriveForwardByDistanceUsingPIDCommand(robot, forwardDistanceProvider));
	}

	protected void initClaw(Robot robot) {
		JoystickButton closeClawButton = new JoystickButton(leftJoystick, CLAW_BUTTON);
		closeClawButton.whenPressed(new ClawOpenCommand(robot));
		closeClawButton.whenReleased(new ClawCloseCommand(robot));
	}
	
	protected void initWrist(Robot robot) {
		// wrist hookup				
		JoystickButton wristButton = new JoystickButton(leftJoystick, WRIST_CONTROL_BUTTON);		
		ControlWristCommand controlWrist = new ControlWristCommand(robot, leftJoystick);
		wristButton.whileHeld(controlWrist);
		
		// currently unused - wrist PID control
//		JoystickButton wristUp = new JoystickButton(leftJoystick, 2);
//		JoystickButton wristDown = new JoystickButton(leftJoystick, 3);
//		
//		wristUp.whileHeld(new SetWristPositionCommand(robot, NumberProvider.fixedValue(Calibration.WRIST_UP_POS), NumberProvider.fixedValue(2000)));
////		wristDown.whileHeld(new SetWristPositionCommand(robot, NumberProvider.fixedValue(Calibration.WRIST_DOWN_POS)));
//
//		wristDown.whileHeld(new SetWristPositionCommand(robot, NumberProvider.fixedValue((Calibration.WRIST_DOWN_POS + Calibration.WRIST_UP_POS)/2), NumberProvider.fixedValue(2000)));
		
	}
			
	protected void initLift(Robot robot) {
		Command liftUpCommand = new LiftUpCommand(robot);
		Command liftDownCommand = new LiftDownCommand(robot);
		Command liftClimbCommand = new Command() {
			public void execute() {
				robot.getLift().go_down_fast();
			}
			
			@Override
			protected boolean isFinished() {
				return robot.getLift().isDown();
			}

			@Override
			protected void end() {
				super.end();
				robot.getLift().stop();
			}									
		};
		
		Command resetLiftEncoder = new Command() {

			@Override
			protected boolean isFinished() {
				robot.getLift().resetEncoder();
				return true;
			}
			
		};	
		
		Command stopLiftCommand = new Command() {
			
			void stopLift() {
				robot.getLift().off();
			}
			
			public void execute() {
				stopLift();
			}
			
			@Override
			protected boolean isFinished() {
				stopLift();
				return true;
			}
			
		};
		
		JoystickButton liftUp = new JoystickButton(leftJoystick, LIFT_UP_BUTTON);
		JoystickButton liftDown = new JoystickButton(leftJoystick, LIFT_DOWN_BUTTON);
		JoystickButton liftClimb = new JoystickButton(leftJoystick, LIFT_CLIMB_BUTTON);		

		liftUp.whileHeld(liftUpCommand);
		liftDown.whileHeld(liftDownCommand);
		liftClimb.whileHeld(liftClimbCommand);
		
	}
	
	protected void initRecordAndPlayback(Robot robot) {		
		// record
		JoystickButton logButton = new JoystickButton(leftJoystick, RECORD_BUTTON);
		pathPlaybackChooser = new PathPlaybackSendableChooser();
		
		SmartDashboard.putString("LOG", "Log off");
		SmartDashboard.putString("Recorded Path", RecordedPaths.NONE);
		SmartDashboard.setPersistent("Recorded Path");		
		SmartDashboard.putData("Playback", pathPlaybackChooser);
		SmartDashboard.putData("Starting Position", startingPositionSendableChooser);
		SmartDashboard.setPersistent("Motor trim");		
		
		// This is an inline interface implementation.  We should probably move it to 
		// be a standalone class.
		Command toggleLoggingCommand = new Command() {			
			public void start() {				
				super.start();				
				Robot.logging = true;
				if (!robot.getDriveLine().isRecording()) {
					Robot.log("---- LOG START ----");
					robot.getDriveLine().startRecording();		
					robot.getDriveLine().setTrim(SmartDashboard.getNumber("Motor trim",  Calibration.RECORD_MODE_TRIM));
					SmartDashboard.putString("Recorded Path", "");
				}
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
				if (p == null) {
					return;
				}
				p.setName("LAST RECORDED");
				if (p != null) {
					recordedPath = p.export();
					
					Robot.log(	"--- PATH START ---\n"
								+recordedPath
								+"\n---- PATH END ----");	
					getPathPlaybackChooser().addPath(p);
					SmartDashboard.putString("Recorded Path", recordedPath);
				}
				SmartDashboard.putString("RECORDING", "OFF");	
				robot.getDriveLine().setTrim(1.0);
			}
			@Override
			protected boolean isFinished() {
				return false;
			}			
		};
		logButton.whileHeld(toggleLoggingCommand);		

		// playback
		JoystickButton pathButton = new JoystickButton(leftJoystick, 4);
		pathButton.whenPressed(new PlaybackCommand(robot, new PathProvider() {

			@Override
			public Path getPath() {
				return getPathPlaybackChooser().getSelected();
			}
			
		}));

		JoystickButton lastPathButton = new JoystickButton(leftJoystick, PLAYBACK_BUTTON);
		lastPathButton.whenPressed(new PlaybackCommand(robot, new PathProvider() {
			@Override
			public Path getPath() {
				return Path.parse(recordedPath);
			}
			
		}));		

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
		int pos = startingPositionSendableChooser.getSelected().intValue();
		AutonomousTarget target = PathChooser.chooseAutonomousTarget(pos);
		Robot.log("Target is "+target.path+" and position is "+pos);
		return new PlaceAutonomousCommand(robot, PathProvider.forPath(target.path), target.middle, target.dropBlock);			
	}

	// static interface to play nice with the "official way"
	private static OI _instance = new OI();
	public static OI getInstance() {
		return _instance;
	}
}










