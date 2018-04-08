package team3543.robot.oi;

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

import com.usfirst.frc.team3543.robot.pathrecording.RecordedPath;
import com.usfirst.frc.team3543.robot.pathrecording.PathChooser;
import com.usfirst.frc.team3543.robot.pathrecording.PathProvider;
import com.usfirst.frc.team3543.robot.pathrecording.RecordedPaths;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a base operator interface for the Robot.
 */
public class BaseOI {

	//////////////////////////////////////////
	// Joystick Button Assignments
	//////////////////////////////////////////
	
	// Generic
	public static final int LEFT_JOYSTICK 		= 0;
	public static final int RIGHT_JOYSTICK 		= 1;
	
	public static final int TRIGGER_BUTTON 				= 1;
	public static final int THUMB_LOWER_BUTTON 			= 2;
	public static final int THUMB_UPPER_BUTTON 			= 3;
	public static final int THUMB_LEFT_BUTTON 			= 4;
	public static final int THUMB_RIGHT_BUTTON 			= 5;
	public static final int SIDE_LEFT_UPPER_BUTTON 		= 6;
	public static final int SIDE_LEFT_LOWER_BUTTON 		= 7;
	public static final int SIDE_RIGHT_UPPER_BUTTON 	= 10;
	public static final int SIDE_RIGHT_LOWER_BUTTON 	= 11;
	public static final int BOTTOM_LEFT_BUTTON 			= 8;
	public static final int BOTTOM_RIGHT_BUTTON			= 9;
	
	// Controls
	private Joystick leftJoystick;
	private Joystick rightJoystick;	

	public String recordedPath = RecordedPaths.NONE; // The "None" path
	PathPlaybackSendableChooser pathPlaybackChooser = new PathPlaybackSendableChooser();
	StartingPositionSendableChooser startingPositionSendableChooser = new StartingPositionSendableChooser();
			
	public BaseOI () {
		// Joysticks and buttons
		rightJoystick = new Joystick(RIGHT_JOYSTICK);        
		leftJoystick = new Joystick(LEFT_JOYSTICK);
	}

	public Joystick getLeftJoystick() {
		return leftJoystick;
	}
	
	public Joystick getRightJoystick() {
		return rightJoystick;
	}
	
}










