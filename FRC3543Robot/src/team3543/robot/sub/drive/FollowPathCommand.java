package team3543.robot.sub.drive;

import com.team254.frc2017.Kinematics;
import com.team254.lib.util.control.PathContainer;
import com.team254.lib.util.control.PathFollower;
import com.team254.lib.util.math.RigidTransform2d;
import com.team254.lib.util.math.Twist2d;

import edu.wpi.first.wpilibj.Timer;
import team3543.robot.sub.DriveLine;
import team3543.robot.sub.drive.DriveConstants;

public class FollowPathCommand extends DriveModeCommand {
	
	private PathFollower mPathFollower;
	
	public FollowPathCommand(DriveLine driveLine) {
		super(driveLine);		
//        mPathFollower = new PathFollower(pathContainer.buildPath(), pathContainer.isReversed(),
//                DriveConstants.kPathFollowerParameters);		
	}
		
	public void setPath(PathContainer pathContainer) {
		if (mPathFollower != null) {
			mPathFollower.forceFinish();
		}
		mPathFollower = new PathFollower(pathContainer.buildPath(), pathContainer.isReversed(),
              DriveConstants.kPathFollowerParameters);	
	}
	
	private DriveState getDriveState() {
		return getDriveLine().getDriveState();
	}
	
	@Override
	protected void initialize() {
		getDriveLine().stopAll();
		getDriveLine().getDriveState().resetDistanceDriven();
		getDriveLine().controlVelocity();
	}
		
	@Override 
	public void execute() {
		double timestamp = Timer.getFPGATimestamp();
		RigidTransform2d robot_pose = getDriveState().getLatestFieldToVehicle().getValue();
	    Twist2d command = mPathFollower.update(timestamp, robot_pose,
	            getDriveState().getDistanceDriven(), getDriveState().getPredictedVelocity().dx);
        Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);
        getDriveLine().controlVelocity(setpoint.left, setpoint.right);
    }
	
	@Override
	public synchronized void cancel() {
		super.cancel();
		finish();
	}
	
	void finish() {
		if (mPathFollower != null && mPathFollower.isFinished()) mPathFollower.forceFinish();
		getDriveLine().stopAll();
	}
	
	@Override
	public synchronized void end() {
		super.end();
		finish();
	}

	@Override 
	public boolean isFinished() {
		return mPathFollower.isFinished();
	}

}
