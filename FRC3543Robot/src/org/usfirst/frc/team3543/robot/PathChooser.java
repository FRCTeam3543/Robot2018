package org.usfirst.frc.team3543.robot;

class PathChooser {
	
	public static AutonomousTarget chooseAutonomousTarget(Integer startingPosition) {
		int robotLocation = startingPosition.intValue();
		// these are my paths
		String[] paths = RecordedPaths.AUTONOMOUS[robotLocation];
		String path;
		int nearSwitch = GameData.getInstance().getPosition(GameData.NEAR_SWITCH);
		int middleScale = GameData.getInstance().getPosition(GameData.MIDDLE_SCALE);
		int farSwitch = GameData.getInstance().getPosition(GameData.FAR_SWITCH);		
		boolean middle = robotLocation == GameData.ROBOT_MIDDLE; 
		
		if (robotLocation == GameData.ROBOT_LEFT) {
			if (middleScale == GameData.PLACE_LEFT) {
				path = paths[2];
			}
			else if (nearSwitch == GameData.PLACE_LEFT) {
				path = paths[0];
			}
			else {
				path = RecordedPaths.DRIVE_OVER_LINE_LEFT;
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
			}
			else if (nearSwitch == GameData.PLACE_RIGHT) {
				path = RecordedPaths.DRIVE_OVER_LINE_MIDDLE_LEFT;
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
			}
		}
		else {
			throw new RuntimeException("Weird robotLocation: "+robotLocation);
		}
		return AutonomousTarget.create(path, middle);
	}
}