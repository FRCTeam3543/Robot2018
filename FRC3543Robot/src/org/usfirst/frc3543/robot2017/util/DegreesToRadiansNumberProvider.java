package org.usfirst.frc3543.robot2017.util;

public class DegreesToRadiansNumberProvider implements NumberProvider {
	private NumberProvider degreesProvider;
	
	public DegreesToRadiansNumberProvider(NumberProvider degreesProvider) {
		this.degreesProvider = degreesProvider;
	}
	
	@Override
	public double getValue() {
		return Math.toRadians(degreesProvider.getValue());
	}
}
