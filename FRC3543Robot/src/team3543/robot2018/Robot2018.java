package team3543.robot2018;

import org.usfirst.frc.team3543.robot.OI;
import org.usfirst.frc.team3543.robot.subsystems.Lift;

import com.team254.frc2017.RobotState;

import edu.wpi.first.wpilibj.command.Command;
import team3543.robot.BotWithDriveLine;
import team3543.robot.cal.PIDF;
import team3543.robot.oi.OIDashboard;
import team3543.robot.sub.DriveLine;
import team3543.robot2018.sub.Claw;
import team3543.robot2018.sub.Wrist;

public class Robot2018 extends BotWithDriveLine {
	///////////////// Actuators /////////////
	private DriveLine driveLine;
	private Claw claw;
	private Wrist wrist;
	private Lift lift;
	
	//////////////// State //////////////////
	RobotState robotState = new RobotState();
	
	////////////// Wiring /////////////////
	
	///////////////// Operator Interface /////
	OI oi = new OI();
	
	///////// PID Controller Tunings ///////
	PIDF wristPIDF 			= new PIDF(0, 0, 0, 0, 0.01);
	// More tunings in DriveConstants (for the driveLine)
	
	///////// Other Calibration ////////////
	double driveEncoderDPP 		= 0;
	double driveOpenLoopRamp 	= 0.5;
	double driveGyroSensitivity  = 0.007;
	
	static {
		Wrist.percentTolerance 	= 1;
		Wrist.encoderDPP 		= 0.02;
		
		
	}
	
	/////////////////// Wiring /////////////////////
	int leftMasterMotorPort 	= 1;
	int leftSlaveMotorPort 	= 2;
	int rightMasterMotorPort = 3;
	int rightSlaveMotorPort 	= 4;

	int leftMasterEncoder 	= 1;
	int leftSlaveEncoder 	= 2;
	int rightMasterEncoder 	= 3;
	int rightSlaveEncoder 	= 4;
	
	int gyroPort				= 9;
	
	int wristMotorPort		= 5;
	int wristEncoderPortA	= 6;	
	int wristEncoderPortB	= 7;	
	
	int clawSolenoid1Port	= 6;
	int clawSolenoid2Port	= 7;
	int clawCompressorPort	= 8;	
	int clawObjectSensorPort = 9;	
	
	int liftMotor1Port		= 8;
	int liftMotor2Port		= 8;	
	int liftEncoderPort		= 10;	
	int liftTopSwitchPort	= 11;
	int liftBottomSwitchPort	= 12;
	
	///////// Constructor //////
	public Robot2018() {
		super();
		claw = new Claw();		
		wrist = new Wrist();
		lift = new Lift();
	}
	
	public DriveLine getDriveLine() {
		return this.driveLine;
	}
		
	///////// Assembly /////////
	@Override
	public void robotInit() {
		super.robotInit();
	}
			
	//////////////// State ///////////////
	public RobotState getState() {
		return robotState;
	}
	
	public void updateDashboard() {
		OIDashboard.update();
	}

	@Override
	public void autonomousPeriodic() {
		super.autonomousPeriodic();
		updateDashboard();
	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		super.teleopPeriodic();
		updateDashboard();		
	}

	@Override
	public Command getAutonomousCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	Game.AutonomousTarget autonomousTarget = null;
		
//	////////// Simple Tasks ///////////
//	public void arcadeDrive() {
//		arcadeDrive.start();
//	}
//	
//	public void followPath(Path path) {
//		if (!path.isFinished()) driveLine.follow(path);
//		else done();
//	}
//	
//	public void pickUpBlock() {
//		// first check if the claw is open.  If not open it
//		if (!claw.isOpen()) claw.open();
//		// the claw is open, now make sure the wrist is down
//		else if (!wrist.isDown()) wrist.go_down();
//		// the claw is open and the wrist is down, now grab the block
//		else if (!claw.isOpen()) claw.close();
//		// the wrist is down and we have the block, so lift it up
//		else if (!wrist.isUp()) wrist.go_up();
//		// ok, we should be done
//		else done();
//	}
//	
//	public void dropBlock() {
//		if (!wrist.isDown()) wrist.go_down();
//		else if (!claw.isOpen()) claw.open();
//		else done();
//	}
//	
//	public void choosePath() {
//		
//	}

	
//	public void autonomousMode() {
//		if (!hasChosenAutonomousTarget()) chooseAutonomousTarget();
//		else if (isHoldingBlock()) {
//			if (!arrivedAtAutonomousTarget()) driveToAutonomousTarget();
//			else if (autonomousTargetIsScale() && !liftRaised()) raiseLift();
//			else if (isHoldingBlock() && shouldDropBlock()) dropBlock();			
//		}
//		else {
//			if (arrivedAtAutonomousTarget()) backUpABit();
//			else if (!arrivedAtAutonomousTarget()) lowerLift();
//			else done();
//		}
//	}
//	
//	public void teleopMode() {
//		// these all run in parallel
//		if (OI.leftJoystick.trigger.isPressed()) claw.close();
//		if (OI.leftJoystick.button5.isPressed()) wrist.go_up();
//		else if (OI.leftJoystick.button6.isPressed()) wrist.go_down();
//		if (OI.leftJoystick.button7.isPressed()) lift.go_up();
//		else if (OI.leftJoystick.button8.isPressed()) lift.go_down();		
//		driveLine.arcadeDrive(OI.rightjoystick);
//	}
		
	/////////////////// Sensing ///////////////////
//	@Dashboard("Holding Block")
//	public boolean isCarryingBlock() {
//		return wrist.isUp() && claw.isHoldingSomething();
//	}
//	
//	public boolean isHoldingBLock() {
//		return claw.isHoldingSomething();
//
//	}
//	
//	public boolean hasChosenAutonomousTarget() {
//		return autonomousTarget != null;
//	}
//	 
	
//	// Thinking
//	public void chooseAutonomousTarget() {
//		if (IStartedOn(LEFT) && Game.nearSwitchIsOn(LEFT)) {
//			_shouldDropBlock = true;
//			autonomousTarget = Game.AutonomousTarget.SWITCH_LEFT;
//		}
//		else if (IStartedOn(RIGHT) && Game.nearSwitchIsOn(RIGHT)) {
//			_shouldDropBlock = true;
//			autonomousTarget = Game.AutonomousTarget.SWITCH_LEFT;			
//		}
//		///
//	}
	
}
