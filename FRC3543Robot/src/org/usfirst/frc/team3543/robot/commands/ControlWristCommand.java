package org.usfirst.frc.team3543.robot.commands;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ControlWristCommand extends Command {

	protected static int ctr = 0;
	Joystick joystick;
	Robot robot;
	int num = 0;
	
	public ControlWristCommand(Robot robot, Joystick joystick) {
		super();
		ctr++;
		num = ctr;
		this.robot = robot;
		this.joystick = joystick;
		requires(robot.wrist);
	}
	
	public void executeOld() {
		if (joystick.getRawButtonPressed(2)) {
			robot.wrist.go_up();
		}
		else if (joystick.getRawButton(3)) {
			robot.wrist.go_down();			
		}
	}
	
	public void execute() {
		// read joystick XY
		double pos = joystick.getY();
		// -1 to 1, back on the stick is 1 but back is up, os invert
		pos *= Calibration.WRIST_MAX_SPEED;
		
		Robot.LOGGER.info(num + " Joystick speed is "+pos);
		// pull back (negative pos) to go up
		// constraint to min/max

		robot.wrist.setSpeed(pos);
//		if (pos < 0) {
//			robot.wrist.go_up(pos);
//		}
//		else {
//			robot.wrist.go_down(pos);
//		}
		
	}
	
	@Override
	public void cancel() {
		super.cancel();
		robot.wrist.off();
	}
	
	@Override
	protected void interrupted() {
		super.interrupted();
//		robot.wrist.off();
	}

	@Override
	protected void end() {
		super.end();
		robot.wrist.off();
	}

	@Override
	protected boolean isFinished() {
//		return !this.isCanceled();
		return false;
	}

}
