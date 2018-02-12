package org.usfirst.frc.team3543.robot.motion;

import org.usfirst.frc.team3543.robot.Calibration;
import org.usfirst.frc.team3543.robot.util.NumberProvider;

public interface MotionProfileProvider {
	public MotionProfile getMotionProfile();
	
	public static MotionProfileProvider wrap(final MotionProfile profile) {
		return new MotionProfileProvider() {
			public MotionProfile getMotionProfile() {
				return profile;
			}
		};
	}

	public static MotionProfileProvider forDistance(final NumberProvider distanceProvider) {
		return new MotionProfileProvider() {
			MotionProfile profile = null;
			public MotionProfile getMotionProfile() {
				// in case this provider uses it more than once
				if (profile == null) {
					MotionProfileGenerator generator = new MotionProfileGenerator();		
					double distance = distanceProvider.getValue();
					profile = generator.generate(distance / Calibration.INCHES_PER_ROTATION);
				}				
				// convert to rotations				
				return profile;				
			}
		};
	}
}
