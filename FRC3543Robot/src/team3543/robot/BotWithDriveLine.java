package team3543.robot;

import com.team254.lib.util.control.PathContainer;

import team3543.robot.sub.DriveLine;
import team3543.robot.sub.drive.AcquireHeadingCommand;
import team3543.robot.sub.drive.ArcadeDriveCommand;
import team3543.robot.sub.drive.FollowPathCommand;

public abstract class BotWithDriveLine extends Bot {

	//// Standard DriveLine
	private DriveLine driveLine;
	
	//// Commands
	private ArcadeDriveCommand arcadeDrive;
	private FollowPathCommand pathFollowerCmd;
	private AcquireHeadingCommand acquireHeadingCmd;

	public BotWithDriveLine() {
		super();
		this.driveLine = new DriveLine();		
		this.arcadeDrive = new ArcadeDriveCommand(this.driveLine, this.getOperatorInterface().getRightJoystick());
		this.driveLine.setDefaultCommand(this.arcadeDrive);		
		this.pathFollowerCmd = new FollowPathCommand(this.driveLine);
		this.acquireHeadingCmd = new AcquireHeadingCommand(driveLine);
	}
	
	public DriveLine getDriveLine() {
		return this.driveLine;
	}
	
	////// High level command launchers
	public void followPath(PathContainer pathContainer) {
		this.pathFollowerCmd.setPath(pathContainer);
		this.pathFollowerCmd.start();
	}
	
	public void turnToHeading(double headingInRadians) {
		this.acquireHeadingCmd.setTarget(headingInRadians);
		this.acquireHeadingCmd.start();
	}
}
