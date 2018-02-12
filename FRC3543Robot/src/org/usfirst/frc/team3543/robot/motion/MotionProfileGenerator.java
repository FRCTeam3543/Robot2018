package org.usfirst.frc.team3543.robot.motion;

import java.util.logging.Logger;

/**
 * See the motion profile generator spreadsheet
 * 
 * @author mk
 *
 */
public class MotionProfileGenerator {
	public static final Logger LOGGER = Logger.getLogger("MotionProfileGenerator");

	public static final int MAX_POINTS = MotionProfile.MAX_POINTS;		// CTRE Buffer Limit
	public static final double DEFAULT_T1 = 400;	// ms acceleration time constant at ramp-up
	public static final double DEFAULT_T2 = 200;	// ms acceleration time constant for ramp-peak
	public static final double DEFAULT_ITP = 10;	// ms duration per trajectory point
	public static final double DEFAULT_MAX_RPS = 2.0;	// ms duration per trajectory point

	private double maxRPS = 0;	
	private double itp = 0;
	
	public MotionProfileGenerator(double max_rps, double itp) {
		this.maxRPS = max_rps;
		this.itp = itp;
	}
	
	public MotionProfileGenerator() {
		this(DEFAULT_MAX_RPS, DEFAULT_ITP);
	}		
	
	public MotionProfile generate(double distanceInRotations, int points) {
		MotionProfile profile = new MotionProfile(0, this.itp);
		
		// Ramp time 
		double p = 0, v = 0, lastV = 0, t = 0, step = 0, Vmax = this.maxRPS, dt = (double)this.itp / 1000;
		
		// write the first point
		profile.add(p,v);
		
		double rampTime = 1;	// TODO - set me in constructor
		double freq = Math.PI / rampTime;
		// −(C19÷2)×SIN(C18×C23)÷C23+POWER(C19÷4,2)
		double rampDistance = -Vmax/2 * Math.sin(rampTime * freq) / freq + Math.pow(Vmax / 2, 2);
		
		double maxVdistance = Math.max(0, distanceInRotations - (2 * rampDistance));
		double totalTime = Math.max(0, 2 * rampTime + (maxVdistance / Vmax));
		double rampUpEnds = rampTime;
		double rampDownStarts = totalTime - rampTime;
		
		while (t < totalTime) {
			lastV = v;
			
			// IF(D26=1,−$C$19÷2×COS(C26×$C$23)+$C$19÷2,IF(E26=1,−$C$19÷2×COS((C26− $C$15)×$C$23)+$C$19÷2,$C$19))
			if (t < rampUpEnds) {
				v = -Vmax/2 * Math.cos(t * freq) + Vmax/2;
			}
			else if (t > rampDownStarts) {
				v = -Vmax/2 * Math.cos((t - totalTime) * freq) + Vmax/2;				
			}
			else {
				v = Vmax;
			}
			
			p = (lastV + v) / 2 * dt + p;
			
			profile.add(p, v); 
			step++;			
			if (step >= MAX_POINTS) {
			}
			t += dt;			
		}
		return profile;
	}
	
	public MotionProfile generate(double distanceInRotations) {
		return generate(distanceInRotations, MAX_POINTS);		
	}
	
}
