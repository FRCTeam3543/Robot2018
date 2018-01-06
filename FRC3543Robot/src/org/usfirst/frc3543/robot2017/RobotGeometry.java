package org.usfirst.frc3543.robot2017;

/**
 * Stores settings related to the robot's physical size.
 * 
 * Because FIRST started with our neighbours to the south, everything is in inches.
 * 
 * @author MK
 */
public class RobotGeometry {
	
	// center-to-center wheel base, in inches
	public double wheelBase;	// FIXME - need the accurate value
	public double overallWidth;
	public double overallLength;
	public double overallHeight;	
	public double cameraY; 	// camera height from ground
	public double cameraX; 	// camera distance from robot centerline
	public double cameraZ; 	// camera distance from robot centerline (back from face)
	public double frontAxleZ;	// distance from the nose of the robot to the front Axle
	
	public double encoderCyclesPerRev;
	public double encoderPulsesPerRev;
	public double encoderDistancePerPulse;
	public double gearReduction = 0.4615;	// 12/26 from sprocket to wheel gear
	
	public RobotGeometry() {
		// TODO - set all these robot geometry values
		wheelBase = 24;
		overallWidth = 36;
		overallLength = overallWidth;
		overallHeight = 24;
		cameraY = 16;
		cameraX = 0;
		frontAxleZ = 3;	// from the bumper
		cameraZ = 18 + frontAxleZ;		
		
		// AndyMark E4T Quadrature Encoder
		encoderCyclesPerRev = 360;
		encoderPulsesPerRev = 1440;
		gearReduction = 0.4615;
		
		double wheelDiameter = 6; // inches
		double wheelCircumference = Math.PI * wheelDiameter;
		encoderDistancePerPulse = wheelCircumference / (encoderCyclesPerRev * gearReduction);
				
	}
	
	
}
