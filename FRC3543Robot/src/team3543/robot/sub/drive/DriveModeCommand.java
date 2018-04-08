package team3543.robot.sub.drive;

import edu.wpi.first.wpilibj.command.Command;
import team3543.robot.sub.DriveLine;

public abstract class DriveModeCommand extends Command {
	private DriveLine driveLine;
	
	DriveModeCommand(DriveLine driveLine) {
		super();		
		this.driveLine = driveLine;
		requires(driveLine);
	}
	
	protected DriveLine getDriveLine() {
		return driveLine;
	}
}
