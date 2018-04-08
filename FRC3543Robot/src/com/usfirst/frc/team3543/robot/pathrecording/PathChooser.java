package com.usfirst.frc.team3543.robot.pathrecording;

import org.usfirst.frc.team3543.robot.AutonomousTarget;
import org.usfirst.frc.team3543.robot.GameData;

public class PathChooser {
	
	public static AutonomousTarget chooseAutonomousTarget(Integer startingPosition) {
		int robotLocation = startingPosition.intValue();
		// these are my paths
		String[] paths = RecordedPaths.AUTONOMOUS[robotLocation];
		String path;
		int nearSwitch = GameData.getInstance().getPosition(GameData.NEAR_SWITCH);
		int middleScale = GameData.getInstance().getPosition(GameData.MIDDLE_SCALE);
		
		boolean middle = robotLocation == GameData.ROBOT_MIDDLE; 
		boolean dropBlock = true;
		
		if (robotLocation == GameData.ROBOT_LEFT) {
			if (middleScale == GameData.PLACE_LEFT) {
				path = paths[2];
			}
			else if (nearSwitch == GameData.PLACE_LEFT) {
				path = paths[0];
			}
			else {
				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;				
				dropBlock = false;
				
			}
		}
		else if (robotLocation == GameData.ROBOT_MIDDLE) {
			// this is if we want to be aggressive from the middle
//			if (nearSwitch == GameData.PLACE_LEFT) {
//				path = paths[0];
//			}
//			else if (nearSwitch == GameData.PLACE_RIGHT) {
//				path = paths[1];
//			}			
//			else {
//				path = RecordedPaths.DRIVE_OVER_LINE_MIDDLE_LEFT;
//			}			
			// passive from middle
			if (nearSwitch == GameData.PLACE_LEFT) {
				path = RecordedPaths.DRIVE_OVER_LINE_MIDDLE_RIGHT;
				dropBlock = false;				
			}
			else if (nearSwitch == GameData.PLACE_RIGHT) {
				path = RecordedPaths.DRIVE_OVER_LINE_MIDDLE_LEFT;
				dropBlock = false;								
			}
			else {
				throw new RuntimeException("Weird, should not happen");
			}
		}
		else if (robotLocation == GameData.ROBOT_RIGHT) {
			if (middleScale == GameData.PLACE_RIGHT) {
				path = paths [3];
		
			}
			else if (nearSwitch == GameData.PLACE_RIGHT) {
				path = paths [1];
			}
			
			else {
				path = RecordedPaths.DRIVE_OVER_LINE_RIGHT;
				dropBlock = false;
			}
		}
		else {
			throw new RuntimeException("Weird robotLocation: "+robotLocation);
		}
		return AutonomousTarget.create(path, middle, dropBlock);
	}
}