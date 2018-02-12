package org.usfirst.frc.team3543.robot.util;

import org.usfirst.frc.team3543.robot.Robot;

/**
 * Interface that provides wiring and calibration info to Configurable parts
 * 
 * @author mk
 *
 */
public interface RobotConfig {

	public Config<Integer> getWiringConfig();
	public Config<Double> getCalibrationConfig();
//	public Config<Boolean> getFlagConfig();
	
	public default int getWiring(String key, int defaultValue) {
		if (!this.getWiringConfig().containsKey(key)) {
			Robot.LOGGER.info(String.format("Wiring %s not set, using default value %d", key, defaultValue));
		}		
		return this.getWiringConfig().lookup(key, defaultValue);
	}
	
	public default double getCalibration(String key, double defaultValue) {
		if (!this.getCalibrationConfig().containsKey(key)) {
			Robot.LOGGER.info(String.format("Calibration %s not set, using default value %f", key, defaultValue));
		}		
		return this.getCalibrationConfig().lookup(key, defaultValue);
	}

//	public default boolean getFlag(String key, boolean defaultValue) {
//		return this.getFlagConfig().lookup(key, defaultValue);
//	}	

	/**
	 * Flags return false by default, but  issues a warning as well
	 * 
	 * @param key
	 * @return
	 */
//	public default boolean getFlag(String key) {
//		if (!this.getFlagConfig().containsKey(key)) {
//			Robot.LOGGER.warning(String.format("Flag %s not set, assume false", key));
//		}
//		return this.getFlagConfig().lookup(key, false);
//	}
	
	/**
	 * Get a wiring value, and throw exception if none is provided
	 * 
	 * @param key
	 * @return
	 */
	public default int getWiring(String key) {
		if (!this.getWiringConfig().containsKey(key)) {
			throw new IllegalStateException(String.format("Required wiring config %s not set, robot won't work", key));
		}
		
		return this.getWiringConfig().get(key);
	}
	
	public default double getCalibration(String key) {
		if (!this.getCalibrationConfig().containsKey(key)) {
			throw new IllegalStateException(String.format("Required calibration config %s not set, robot won't work", key));
		}
		return this.getCalibrationConfig().get(key);
	}

}
