package team3543.robot.sub.drive;

import com.team254.frc2017.Constants;
import com.team254.lib.util.control.Lookahead;
import com.team254.lib.util.control.PathFollower;

import team3543.robot.cal.PIDF;

/**
 * Constants.  Override in our own Calibration startup.
 * 
 * This class is to document which constants we are using.
 * 
 * @author mk
 */
public class DriveConstants {
	public static double kDriveHighGearMaxSetpoint = Constants.kDriveHighGearMaxSetpoint;
	public static double kDriveWheelDiameterInches = Constants.kDriveWheelDiameterInches;

	PIDF headingPID 			= new PIDF(0, 0, 0, 0, 0.01);
	PIDF drivePositionPIDF  	= new PIDF(0, 0, 0, 0, 0.01);
		
	public static PathFollower.Parameters kPathFollowerParameters = new PathFollower.Parameters(
            new Lookahead(Constants.kMinLookAhead, Constants.kMaxLookAhead,
                    Constants.kMinLookAheadSpeed, Constants.kMaxLookAheadSpeed),
            Constants.kInertiaSteeringGain, Constants.kPathFollowingProfileKp,
            Constants.kPathFollowingProfileKi, Constants.kPathFollowingProfileKv,
            Constants.kPathFollowingProfileKffv, Constants.kPathFollowingProfileKffa,
            Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel,
            Constants.kPathFollowingGoalPosTolerance, Constants.kPathFollowingGoalVelTolerance,
            Constants.kPathStopSteeringDistance);
}
