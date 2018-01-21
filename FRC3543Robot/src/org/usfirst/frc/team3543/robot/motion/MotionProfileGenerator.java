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
	private double t1 = 0;
	private double t2 = 0;
	private double itp = 0;
	
	public MotionProfileGenerator(double max_rps, double t1, double t2, double itp) {
		this.maxRPS = max_rps;
		this.t1 = t1;
		this.t2 = t2;
		this.itp = itp;
	}
	
	public MotionProfileGenerator() {
		this(DEFAULT_MAX_RPS, DEFAULT_T1, DEFAULT_T2, DEFAULT_ITP);
	}
		
	public MotionProfile oldGenerate(double distanceInRotations, int points) {
		MotionProfile profile = new MotionProfile(0);
		double t4 = distanceInRotations / this.maxRPS * 1000;
		double fl1 = Math.round(this.t1 / this.itp);
		double fl2 = Math.round(this.t2 / this.itp);
		double n = t4 / this.itp;
		// this matches the spreadsheet
		int step = 0;
		double f1_sum = 0, f2_sum = 0, time = 0, dt = (double) this.itp / 1000;
		boolean input = false;
		double p = 0, v = 0, a = 0, lastV = 0;	
		// write the first point
		profile.add(p,v);
		step++;		
		while (step < (n+1)) {
			// IF((B27<($C$11+2)),1,0)
			input = (step + 1) < (n+2) ? true : false;
			// SUM(
			//	OFFSET(
			//		E27,((−1 x MIN($C$10,B27))+1),0,MIN($C$10,B27),1
			//	)
			// )			
			// sum of previous 20 rows, or N if not available - so this is a moving average
			// MAX(0,MIN(1,(E26+IF((D27=1),(1÷$C$9),(−1÷$C$9)))))
			f1_sum = Math.max(0.0, Math.min(1.0, fl1 + (input ? (1.0 / fl1) : (-1.0 / fl1) )));
			f2_sum = (f2_sum / fl2) + f1_sum;

			lastV = v;
			// ((E27+F27)÷(1+$C$10))×$C$3
			v = (f1_sum + f2_sum) / (1 + fl2) * this.maxRPS;
			//((((I27+I26)÷2)×$C$7)÷1000)+J26
			p = ((lastV + v) / 2) * this.itp / 1000 + p;
			a = (v - lastV) / this.itp / 1000;
			profile.add(p, v); 
			step++;
			if (step >= MAX_POINTS) {
				LOGGER.warning("Maxmimum points reached");
				break;
			}
		}
		return profile;
	}
	
	public MotionProfile generate(double distanceInRotations, int points) {
		MotionProfile profile = new MotionProfile(0);
		
		// Ramp time 
		double p = 0, v = 0, lastV = 0, t = 0, step = 0, Vmax = this.maxRPS, dt = (double)this.itp / 1000;
		
		// write the first point
		profile.add(p,v);
		
		double rampTime = 0.5;	// TODO - set me in constructor
		double freq = Math.PI / rampTime;
		// −(C19÷2)×SIN(C18×C23)÷C23+POWER(C19÷4,2)
		double rampDistance = -Vmax/2 * Math.sin(rampTime * freq) / freq + Math.pow(Vmax / 4, 2);
		
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
//				LOGGER.warning("Maxmimum points reached");
//				break;
			}
			t += dt;			
		}
		return profile;
	}
	
	public MotionProfile generate(double distanceInRotations) {
		return generate(distanceInRotations, MAX_POINTS);		
	}
	
}
