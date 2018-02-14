package org.usfirst.frc.team3543.robot.commands;

import java.util.List;

import org.usfirst.frc.team3543.robot.Path;
import org.usfirst.frc.team3543.robot.PathProvider;
import org.usfirst.frc.team3543.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PlaybackCommand extends Command {

	Path path;
	PathProvider pathProvider;
	Robot robot;
	
	public PlaybackCommand(Robot robot, PathProvider pathProvider) {
		super();
		requires(robot.getDriveLine());
		this.robot = robot;
		this.pathProvider = pathProvider;
	}
	
	@Override
	protected void initialize() {
		Robot.log("PATH IS: "+pathProvider.getPath().export());
		this.path = Path.start().addAll(pathProvider.getPath());		
		Robot.log("INITIALIZE PLAYBACK "+path.export());
		Robot.log("Path has "+path.getPoints().size() + " points");
		robot.getDriveLine().resetAll();
	}

	public void execute() {
		if (!path.isDone()) {		
			Path.Point point = path.shift();
			robot.getDriveLine().setLeftRightMotorOutputs(point.left, point.right);			
		}
		else {
			Robot.log("Path isDone");
		}
	}
	
	@Override
	protected boolean isFinished() {
		if (path.isDone()) {
			Robot.log("PATH IS DONE "+path.getPoints().size());
		}
		return path.isDone();
	}
	
	
}
