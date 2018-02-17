package org.usfirst.frc.team3543.robot;

import java.util.logging.Logger;

import org.usfirst.frc.team3543.robot.subsystems.Claw;
import org.usfirst.frc.team3543.robot.subsystems.DriveLine;
import org.usfirst.frc.team3543.robot.subsystems.DriveLineLinearPID;
import org.usfirst.frc.team3543.robot.subsystems.Lift;
import org.usfirst.frc.team3543.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

	// to be compatible with the old, dangerous idiom, a static
	private static Robot _instance = null;
	public static boolean logging = false;
	
	public static final Logger LOGGER = Logger.getLogger(Robot.class.getSimpleName());

	Command autonomousCommand;
	public DriveLine driveLine;
	public DriveLineLinearPID driveLineLinearPID;	
	public Claw claw;
	public Lift lift;
	public Wrist wrist;
	
	private boolean recording = false;
	
	public OI operatorInterface; // operator interface

	public Robot() {
		this(new OI());
	}
	
	public static Robot getInstance() {
		if (_instance == null) {
			_instance = new Robot();
		}
		return _instance;
	}
	
	public Robot(OI operatorInterface) {
		super();
		this.operatorInterface = operatorInterface;
		
		// properties should be initialized in the constructor, not later
		driveLine = new DriveLine();
		driveLineLinearPID = new DriveLineLinearPID(driveLine);
		claw = new Claw();
		wrist = new Wrist();
		lift = new Lift();
	}
	
	public DriveLine getDriveLine() {
		return this.driveLine;
	}
	
	public Wrist getWrist() {
		return this.wrist;
	}
		
	public Claw getClaw() {
		return this.claw;
	}
	
	public Lift getLift() {
		return this.lift;
	}
	
	public DriveLineLinearPID getDriveLineLinearPID() {
		return driveLineLinearPID;
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		LOGGER.info("INITIALIZE ROBOT");
		
		// MK - This is from the default project generator, we're going to ignore
		// and NOT create commands in the operator interface before the subsystems
		// are ready. This statement exists because of the use of static singleton
		// classes, which is an anti-pattern (bad practice) in Java.  If you
		// are properly using object-oriented design you should not have
		// "null pointers" in Java.
		
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		// oi = new OI();

		// this will actually do what the above does, explicitly
		// The OI will create commands and bind the UI to THIS robot
		getOperatorInterface().connectToRobot(this);
		driveLine.updateOperatorInterface();
		claw.updateOperatorInterface();		
	}

	public OI getOperatorInterface() {
		return this.operatorInterface;
	}
	
	/**
	 * This function is called when the disabled button is hit.
	 * You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit(){

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		GameData.getInstance().init();
		// schedule the autonomous command (example)
		Command autonomousCommand = this.getOperatorInterface().getAutonomousCommand(this);		
		if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		updateDashboard();    	
		Scheduler.getInstance().run();        
	}

	public void teleopInit() {
		LOGGER.info("TELEOP INIT");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) autonomousCommand.cancel();
		autonomousCommand = null;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		updateDashboard();    	
		Scheduler.getInstance().run();
		updateDashboard();
	}
	
	public void setRecording(boolean b) {
		this.recording = b;
	}

	public boolean isRecording() {
		return recording;
	}
	
	@Override
	public void startCompetition() {
		super.startCompetition();
		LOGGER.info("startCompetition");
	}

	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("deprecation")
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static void log(String message) {
		LOGGER.info(message);
	}

	protected void updateDashboard() {
		// put any extra dashboard update code here
		SmartDashboard.updateValues();
	}

}


