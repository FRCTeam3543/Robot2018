package team3543.robot.sub.drive;


import team3543.robot.sub.DriveLine;
import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDriveCommand extends DriveModeCommand {
	private Joystick joystick;
	
	public ArcadeDriveCommand(DriveLine driveLine, Joystick joystick) {
		super(driveLine);
		this.joystick = joystick;
	}

	@Override
	protected void initialize() {
		getDriveLine().stopAll();
	}
	
	@Override
	public void execute() {
		this.getDriveLine().arcadeDrive(this.joystick);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
