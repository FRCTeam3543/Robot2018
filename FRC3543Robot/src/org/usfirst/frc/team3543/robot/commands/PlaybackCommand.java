package org.usfirst.frc.team3543.robot.commands;

import java.util.List;

import org.usfirst.frc.team3543.robot.Path;
import org.usfirst.frc.team3543.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PlaybackCommand extends Command {

	Path path;
	Robot robot;
	
	public PlaybackCommand(Robot robot, Path path) {
		requires(robot.getDriveLine());
		this.robot = robot;
		this.path = Path.start().addAll(path);
	}
	
	@Override
	protected void initialize() {
		robot.getDriveLine().resetAll();
	}
	
	public void execute() {
		Path.Point point = path.shift();
		robot.getDriveLine().drive(point.magnitude, point.curve);
	}
	
	@Override
	protected boolean isFinished() {
		return path.isDone();
	}
	
	
}
