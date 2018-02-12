package org.usfirst.frc.team3543.robot.oi;


import org.usfirst.frc.team3543.robot.GameData;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Starting position is from your ROBOT's perspective
 * 
 * @see https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
 * 
 * @author mk
 */
public class StartingPositionSendableChooser extends SendableChooser<Integer> {

	public StartingPositionSendableChooser() {
		super();
		addDefault("MIDDLE", GameData.ROBOT_MIDDLE);
		addObject("LEFT", GameData.ROBOT_LEFT);		
		addObject("MIDDLE", GameData.ROBOT_MIDDLE);
		addObject("RIGHT", GameData.ROBOT_RIGHT);
	}
		
}
