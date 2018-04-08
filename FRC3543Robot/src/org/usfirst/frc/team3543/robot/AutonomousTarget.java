package org.usfirst.frc.team3543.robot;

import com.usfirst.frc.team3543.robot.pathrecording.RecordedPaths;

public class AutonomousTarget {
	public boolean middle = false;
	public boolean dropBlock = true;
	public String path = RecordedPaths.DRIVE_OVER_LINE;
	
	AutonomousTarget(String path, boolean middle, boolean dropBlock) {
		this.path = path;
		this.middle = middle;
		this.dropBlock = dropBlock;
	}
	
	public static AutonomousTarget create(String path, boolean middle, boolean dropBlock) {
		return new AutonomousTarget(path, middle, dropBlock);
	}
}
