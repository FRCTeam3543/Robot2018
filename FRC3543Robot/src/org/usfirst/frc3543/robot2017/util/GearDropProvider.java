package org.usfirst.frc3543.robot2017.util;

import ttfft.vision.GearDrop;

public interface GearDropProvider {

	public GearDrop getGearDrop();
	public void setGearDropConsumer(GearDropConsumer c);
}
