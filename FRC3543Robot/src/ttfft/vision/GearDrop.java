package ttfft.vision;

import java.util.logging.Logger;

import org.opencv.core.KeyPoint;
import org.usfirst.frc3543.robot2017.Robot;
import org.usfirst.frc3543.robot2017.World;

/**
 * Models the Gear Drop relative to the robot.
 * 
 * @author MK
 */
public class GearDrop {
	public static final Logger LOGGER = Logger.getLogger(GearDrop.class.getName());
	
	public KeyPoint left;						
	public KeyPoint right;
	
	public long timestamp;

	public double skewAngle;					// angle to stright-on of the gear drop
	public double angleToGround;				// angle of the line from center-a to center-b
	public double sizeDiff;						// ratio of smallest to largest circle
	public double leftRightSizeDiff;			// ratio of size of leftmost blob to rightmost
	public double ratioOfDistanceToDiameter;	// ratio of distance between centers to average diameter
	public double averageDiameter;				// average circle diameter

	public long offset[] = {0,0};				// how far left or right 
	public double offsetAsPercentage[] = {0.0,0.0};	// offset as percentage of width
	public long centerSpanInPixels = 0;
	public long[] gearDropPoint = {0,0};			// point where gear drop is
	public double distanceFromTarget = 0.0;
	
	public double offsetFromCenter = 0.0;			// offset from center
		
	private Settings settings;				// settings for the image transform
	
	GearDrop(KeyPoint a, KeyPoint b, Settings settings) {
		// leftmost one is the one with the leftmost x
		timestamp = System.currentTimeMillis();
		this.settings = settings;
		if (a.pt.x < b.pt.x) {
    		left = a;
    		right = b;    			
		}
		else {
			left = b;
			right = a;
		}
		recompute();
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s}", gearDropPoint[0], gearDropPoint[1]);
	}

	public void recompute() {
		angleToGround = computeAngleToGround(left, right);
		// TODO - compute this - we are not using it after all
		skewAngle = 0;

		sizeDiff = computeSizeDiff(left, right);
		averageDiameter = (left.size + right.size) / 2;
		leftRightSizeDiff = computeLeftRightSizeDiff(left, right);
		ratioOfDistanceToDiameter = computeRatioOfDistanceToDiameter(left, right);
		gearDropPoint[0] = Math.round(right.pt.x + left.pt.x)/2;
		gearDropPoint[1] = Math.round(right.pt.y + left.pt.y)/2;
		centerSpanInPixels = Math.round(Math.sqrt(Math.pow(right.pt.x-left.pt.x,2)+Math.pow(right.pt.y-left.pt.y, 2)));
		offset[0] = gearDropPoint[0] - (settings.outputImageWidth/2);
		offset[1] = gearDropPoint[1] - (settings.outputImageHeight/2);

		offsetAsPercentage[0] = (double)offset[0]/settings.outputImageWidth;
		offsetAsPercentage[1] = (double)offset[1]/settings.outputImageHeight;
		
		// distance from target
		/*
		 * We know the relative span rho of the target vs the overall field width (wt/wf) (in pixels)
		 * We know the FOV 1/2 angle in radians (theta)
		 * We know half the width of the target in m (wt)
		 * We know half the FOV width in pixels (wf)
		 * 
		 * we know wf / d = tan (theta)
		 * so wf = wt / rho
		 * so d = wt / (rho tan (theta))
		 */
		double theta = Settings.FOV_ANGLE;
		double rho = (double)centerSpanInPixels / settings.outputImageWidth;
		double wt = Settings.CENTER_SPAN_IN_INCHES / 2;
//		LOGGER.info(String.format("theta = %.2f rho = %.2f wt = %.2f (%s, %s)", theta, rho, wt, centerSpanInPixels, settings.outputImageWidth));
		distanceFromTarget = wt / (rho * Math.tan(theta)) - World.GEAR_DROP_POST + World.GEAR_DROP_POST_TARGET - Robot.geometry.cameraZ;
		double width = distanceFromTarget * Math.tan(theta);
		offsetFromCenter = (double)offset[0] / (double)settings.outputImageWidth * width;
	}
	
	/**
	 * Compute the horizontal angle (to the ground) from a to b
	 * @param a
	 * @param b
	 * @return
	 */
	public static double computeAngleToGround(KeyPoint a, KeyPoint b) {
		// angle = atan(y2 -  y1)/ (x2 - x1)
		double xdiff = b.pt.x - a.pt.x;
		double ydiff = b.pt.y - a.pt.y;
//		LOGGER.info(String.format("[%.3f, %.3f] and [%.3f, %.3f]", a.pt.x, a.pt.y, b.pt.x, b.pt.y));   
		double ret;
		if (Math.abs(xdiff) < 0.00000001) {
			ret = Math.PI / 2;
		}
		else {
			ret = Math.atan(-ydiff / xdiff);
		}
//		LOGGER.info(String.format("xdiff = %.2f ydiff = %.2f angle = %.1f", xdiff, ydiff, ret * 180 / Math.PI));
		return ret;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double computeSizeDiff(KeyPoint a, KeyPoint b) {
		if (a.size < b.size) {
			return a.size / b.size;
		}
		else {
			return b.size / a.size;
		}
	}
	
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double computeLeftRightSizeDiff(KeyPoint a, KeyPoint b) {    		
		return a.size / b.size;    		
	}    	
	
	public static double computeRatioOfDistanceToDiameter(KeyPoint a, KeyPoint b) {
		double avg_diameter = (a.size + b.size) / 2;
		if (avg_diameter < 0.00001) { // too small
			return (double)Integer.MAX_VALUE; // really big number
		}
		double rel_dist = Math.sqrt(Math.pow(a.pt.x - b.pt.x, 2) + Math.pow(a.pt.y - b.pt.y, 2));
//		LOGGER.info(String.format("Distance %.1f Diameter %.1f", rel_dist, avg_diameter));
		
		return rel_dist / avg_diameter;
	}
}
