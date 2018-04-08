package team3543.robot;

import java.util.logging.Logger;

import org.usfirst.frc.team3543.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class Bot {
	public static final Logger LOGGER = Logger.getLogger(Bot.class.getSimpleName());

	OI operatorInterface;
		
	public Bot() {
		// odd that the operator interface adds the OI
		operatorInterface = new OI();
	}
			
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		LOGGER.info("INITIALIZE ROBOT");		
		initCommands();
	}

	protected void initCommands() {
		
	}
	
	/// Commands 
	
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
		// schedule the autonomous command (example)
		Command autonomousCommand = getAutonomousCommand();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	public abstract Command getAutonomousCommand();
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();        
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		Command autonomousCommand = getAutonomousCommand();
		if (autonomousCommand != null && autonomousCommand.isRunning()) {
			autonomousCommand.cancel();
		}
	}

	public void startCompetition() {
		// override me
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {		
		Scheduler.getInstance().run();
		updateDashboard();
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
