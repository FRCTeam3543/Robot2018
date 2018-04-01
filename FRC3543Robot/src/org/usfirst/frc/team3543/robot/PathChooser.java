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
				path = RecordedPaths.PLACE_LEFT_SWITCH_FROM_LEFT;			
				dropBlock = true;
//				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;
			}
			else if (middleScale == GameData.PLACE_LEFT) {
				path = RecordedPaths.PLACE_LEFT_SWITCH_FROM_LEFT;
				dropBlock = false;				
//				middle = true;
			}			
			else {
				path = RecordedPaths.PLACE_LEFT_SWITCH_FROM_LEFT;
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
				path = RecordedPaths.PLACE_RIGHT_SWITCH_FROM_RIGHT;
				dropBlock = true;
//				path = RecordedPaths.DRIVE_OVER_LINE_RIGHT;				
			}			
			else if (middleScale == GameData.PLACE_RIGHT) {
				//path = paths [3];
				path = RecordedPaths.PLACE_RIGHT_SWITCH_FROM_RIGHT;
				dropBlock = false;				
//				middle = true;
			}			
			else {
				path = RecordedPaths.PLACE_RIGHT_SWITCH_FROM_RIGHT;
				dropBlock = false;
			}
		}
		else {
//			throw new RuntimeException("Weird robotLocation: "+robotLocation);
			path = RecordedPaths.DRIVE_OVER_LINE;
		}
		
		return AutonomousTarget.create(path, middle, dropBlock);
	}
}