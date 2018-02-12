package org.usfirst.frc.team3543.robot;

public class AutonomousTarget {
	public boolean middle = false;
	public String path = RecordedPaths.DRIVE_OVER_LINE;
	
	AutonomousTarget(String path, boolean middle) {
		this.path = path;
		this.middle = middle;
	}
	
	public static AutonomousTarget create(String path, boolean middle) {
		return new AutonomousTarget(path, middle);
	}
}
