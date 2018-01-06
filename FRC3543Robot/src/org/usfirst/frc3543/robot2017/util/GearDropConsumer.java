package org.usfirst.frc3543.robot2017.util;

import ttfft.vision.GearDrop;

public interface GearDropConsumer {

	/**
	 * Set the gear drop.  Expect null if no gear drop is detected/
	 * @param gearDrop
	 */
	void setGearDrop(GearDrop gearDrop);
}
