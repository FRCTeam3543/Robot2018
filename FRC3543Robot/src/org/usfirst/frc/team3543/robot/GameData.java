package org.usfirst.frc.team3543.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class GameData {

	// These are for array indices
	public static final int ROBOT_LEFT = 0;
	public static final int ROBOT_MIDDLE = 1;
	public static final int ROBOT_RIGHT = 2;

	public static final int NEAR_SWITCH = 0;
	public static final int MIDDLE_SCALE = 1;
	public static final int FAR_SWITCH = 2;
	
	public static final int PLACE_LEFT = 0;
	public static final int PLACE_RIGHT = 1;
			
	private int[] gameData = { PLACE_LEFT, PLACE_LEFT, PLACE_LEFT };
	
	private static GameData _instance = null;		// singleton	
	public static GameData getInstance() {
		if (_instance == null) {
			_instance = new GameData();
			_instance.init();
		}
		return _instance;
	}
	
	public int[] getGameData() {
		return gameData;
	}
	
	public int getPosition(int num) {
		if (num < 0 || num >= gameData.length) {
			throw new IllegalArgumentException("Invalid index");
		}
		return gameData[num];
	}
	
	public void init() {
		// see https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
		String message = DriverStation.getInstance().getGameSpecificMessage();
		int i = 0;
		for (char c : message.toCharArray()) {
			switch (c) {
				case 'L':
					gameData[i] = PLACE_LEFT;
					break;
				case 'R':
					gameData[i] = PLACE_RIGHT;
					break;
				default:
					// leave at Unknown
					break;
			}
			i++;
			if (i > 2) break;
		}
		Robot.log("GameData is "+message);
	}
	
	public Alliance getAlliance() {
		return DriverStation.getInstance().getAlliance();
	}
	
	// Zero-index the location (0=1, 1=2, 2=2)
	public int getLocation() {
		return DriverStation.getInstance().getLocation() - 1;
	}
}
