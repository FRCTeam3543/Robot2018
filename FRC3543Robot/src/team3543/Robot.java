package team3543;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import team3543.robot.Bot;
import team3543.robot2018.Robot2018;

public class Robot extends TimedRobot {
	public static final Class<? extends Bot> ACTUAL_BOT_CLASS = Robot2018.class;
	private static final Logger LOGGER = Logger.getLogger("Robot");
	
	// We delegate so we can quickly plug in a different robot config, and have
	// many in the same project
	private Bot bot;

	public Robot() {
		super();
		try {
			bot = ACTUAL_BOT_CLASS.newInstance();
			// the exceptions won't happen ever
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		super.robotInit();
		LOGGER.info("Starting "+bot.getClass().getSimpleName());
		bot.robotInit();
	}
		
	/**
	 * This function is called when the disabled button is hit.
	 * You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit(){
		super.disabledInit();
		bot.disabledInit();
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
	
	Command getAutonomousCommand() {
		return bot.getAutonomousCommand();
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		bot.autonomousPeriodic();
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
		bot.teleopInit();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {		
		bot.teleopPeriodic();
		Scheduler.getInstance().run();
	}
		
	@Override
	public void startCompetition() {
		super.startCompetition();
		bot.startCompetition();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("deprecation")
	public void testPeriodic() {
		LiveWindow.run();
		bot.testPeriodic();
	}
}

