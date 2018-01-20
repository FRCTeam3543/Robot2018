package org.usfirst.frc.team3543.robot.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.usfirst.frc.team3543.robot.Robot;

public class ConfigLoader {
	public static Properties loadFromStream(InputStream is) throws IOException {
		Properties props = new Properties();
		props.load(is);		
		return props;		
	}
	
	public static Properties loadFromFileOnClasspath(String propsFile) {
		try {
			return loadFromStream(Robot.class.getResourceAsStream(propsFile));
		} catch (IOException e) {
			Robot.LOGGER.warning(String.format("Could not find %s on classpath", propsFile));
			throw new IllegalArgumentException(e);
		}
	}
}
