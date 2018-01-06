package ttfft.vision;

public class Settings implements Cloneable {
	public int outputImageWidth = 320;
	public int outputImageHeight = 200;
	
	public int inputImageWidth = 640;
	public int inputImageHeight = 480;

	public double targetAngle = 15.0/180 * Math.PI;
	public double targetSizeDiff = 0.75; // 80% same size
	public double targetRelativeDistance = 2.3; // distance between centers as percentage of average diameter
	public long targetCenterSpan = 90; // TODO pixels, move to RobotMap or some vision constants file
	
	/// Statics
	public static final String OPENCV_LIBRARY_VERSION = "opencv_java310";
	/**
	 * Distance between tape centers in M
	 */
	public static final double CENTER_SPAN_IN_INCHES = 8.25; 	// 25.4 mm per inch, span is 7.5in	
	public static final double CENTER_SPAN_IN_M = CENTER_SPAN_IN_INCHES * 25.4 / 1000; 	// 25.4 mm per inch, span is 7.5in
	
	/**
	 * Tape height in M
	 */	
	public static final double BLOB_DIAMETER_IN_INCHES = 5 * 25.4 / 1000;		// tape is 5in long
	public static final double BLOB_DIAMETER_IN_M = BLOB_DIAMETER_IN_INCHES * 25.4 / 1000;		// tape is 5in long

	/**
	 * Field of view angle, in radians (estimated as 37.5 degrees)
	 */
	public static final double FOV_ANGLE = 30.75 * Math.PI/180; // CALIBRATED
				
	/**
	 * Target distance from the gear drop face
	 * TODO - measure this
	 */	
	public static final double TARGET_DISTANCE_IN_INCHES = 6;	
	public static final double TARGET_DISTANCE_IN_M = TARGET_DISTANCE_IN_INCHES * 25.4 / 1000;
	
	/**
	 * Target center span distance, in percentage of image width
	 * TODO - measure this using camera capture
	 */
	public static final double TARGET_CENTER_SPAN_IN_PERCENT = 0.5;	


}
