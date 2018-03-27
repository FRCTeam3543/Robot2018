package org.usfirst.frc.team3543.robot;

class PathChooser {
	
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
			if (nearSwitch == GameData.PLACE_LEFT) {
//				path = paths[0];
				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;

			}
			else if (middleScale == GameData.PLACE_LEFT) {
				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;
//				middle = true;
			}			
			else {
				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;				
				dropBlock = false;
				
			}
		}
		else if (robotLocation == GameData.ROBOT_MIDDLE) {
			// this is if we want to be aggressive from the middle
//			if (nearSwitch == GameData.PLACE_LEFT) {
//				path = RecordedPaths.PLACE_MIDDLE_LEFT;
//			}
//			else if (nearSwitch == GameData.PLACE_RIGHT) {
//				path = RecordedPaths.PLACE_MIDDLE_RIGHT;
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
			if (nearSwitch == GameData.PLACE_RIGHT) {
//				path = paths [1];
				path = RecordedPaths.DRIVE_OVER_LINE_RIGHT;				
			}			
			else if (middleScale == GameData.PLACE_RIGHT) {
				//path = paths [3];
				path = RecordedPaths.DRIVE_OVER_LINE_RIGHT;
//				middle = true;
			}			
			else {
				path = RecordedPaths.DRIVE_OVER_LINE_RIGHT;
				dropBlock = false;
			}
		}
		else {
//			throw new RuntimeException("Weird robotLocation: "+robotLocation);
			path = RecordedPaths.DRIVE_OVER_LINE;
		}
		// Barrie - never drpo
		dropBlock = false;
		middle = false;
		// Barrie finals, just go fwd
		path = RecordedPaths.DRIVE_OVER_LINE;
		return AutonomousTarget.create(path, middle, dropBlock);
	}
}