package ttfft.vision;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

/**
 * Subclass of GripPipeline - use this one in the code
 * 
 * This class exists for two reasons: first, so that we can regenerate the 
 * underlying GRIP class without overwriting custom code.  Second, so that
 * the pipeline produces a GearDrop object, which GRIP routines cannot do.
 * 
 * @author MK
 *
 */
public class GearDropPipeline extends GripPipeline {
	public static final Logger LOGGER = Logger.getLogger(GearDropPipeline.class.getName());
	
	private GearDrop foundGearDrop = null;
	private Settings settings = new Settings();
	
	public GearDropPipeline() {
		super();
	}
	
	public GearDropPipeline(Settings settings) {
		this();
		this.settings = settings;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public void setSettings(Settings s) {
		this.settings = s;
	}
	
	@Override
	public void process(Mat image) {
		foundGearDrop = null;
		this.getSettings().inputImageWidth = image.cols();
		this.getSettings().inputImageHeight = image.rows();
//		LOGGER.info(String.format("Image is %d x %d", this.getSettings().inputImageWidth, this.getSettings().inputImageHeight));
		super.process(image);
		MatOfKeyPoint points = this.findBlobsOutput();
		float r = this.getSettings().inputImageWidth / this.getSettings().outputImageWidth;
		
		for (KeyPoint point: points.toList()) {
			// original image is twice as big as the processed one
			Imgproc.circle(image, new Point(point.pt.x * r, point.pt.y * r), Math.round(point.size * r  / 2), new Scalar(140,255,255), 2);
		}
		foundGearDrop = detectGearDrop(points);	
	}
	
	public GearDrop detectGearDropOutput() {
		return foundGearDrop;
	}
	
	public GearDrop detectGearDrop(MatOfKeyPoint points) {
		List<KeyPoint> list = points.toList();
		// sort from left to right
		list.sort(new Comparator<KeyPoint>() {

			@Override
			public int compare(KeyPoint p1, KeyPoint p2) {
				if (p1.pt.x < p2.pt.x) {
					return -1;
				}
				else if (p1.pt.x > p2.pt.x) {
					return 1;
				}
				else {
					return 0;
				}
			}			
		});
		LOGGER.info("\n----\nDETECT GEAR DROP have "+list.size()+" blobs\n----");
		
//		SmartDashboard.putNumber(OI.GEARFINDER_BLOB_COUNT, list.size());
		GearDrop ret = null;
		KeyPoint [] arr = list.toArray(new KeyPoint[list.size()]);
		// if there are more than four blobs or less than 2, we don't see anything
		if (arr.length < 20 && arr.length >= 2) {
			// look for two points, n% up in the display
			int i, j;
			// go thru each item.  Look at the items after it.  See if you find two blobs in a straight horizontal line
			//SmartDashboard.putBoolean(OI.GEARFINDER_FOUND_GEAR, false);
			
			for (i = 0; i<arr.length; i++) {
				for (j=i+1; j<arr.length; j++) {
					
					// -2.9/-91
					
					double angleToGround = Math.abs(GearDrop.computeAngleToGround(arr[i], arr[j]));
					double sizeDiff = GearDrop.computeSizeDiff(arr[i], arr[j]);
					double d2d = GearDrop.computeRatioOfDistanceToDiameter(arr[i], arr[j]);
					boolean approx = Utils.isApproximately(d2d, settings.targetRelativeDistance, 0.2);
					if (sizeDiff >= 0.7) {
						LOGGER.info(String.format("(%d, %d) (%.1f, %.1f, %.1f) (%.1f, %.1f, %.1f) Angle to ground %.2f (%.2f) sizeDiff %.1f (%.1f) D2D %.1f (%.1f) approx %s",i,j, arr[i].pt.x, arr[i].pt.y, arr[i].size, arr[j].pt.x, arr[j].pt.y, arr[j].size, angleToGround, settings.targetAngle, sizeDiff, settings.targetSizeDiff, d2d, settings.targetRelativeDistance, approx));
					}
					// this is more efficient - when things don't match only the first equation will eval
					if (	Math.abs(GearDrop.computeAngleToGround(arr[i], arr[j])) <= settings.targetAngle  	// relatively flat
							&& Math.abs(GearDrop.computeSizeDiff(arr[i], arr[j])) >= settings.targetSizeDiff				// same size blobs
							&& Utils.isApproximately(GearDrop.computeRatioOfDistanceToDiameter(arr[i], arr[j]), settings.targetRelativeDistance, 0.2)	// relative distance +/- percent
							) {					
						ret = new GearDrop(arr[i], arr[j], getSettings());					
						LOGGER.info(String.format("!!!!!!!!!!!!!!!!!!! FOUND THE GEAR DROP (%.2f in) !!!!!!!!!!!!!!!!!!!!!!!", ret.distanceFromTarget ));
						
						break;
					}
				}
				if (ret != null) break;
			}			
			
		}
//		SmartDashboard.putBoolean(OI.GEARFINDER_FOUND_GEAR, ret != null);
		if (ret == null) {				
			LOGGER.info("NOPE.  No gear drop");				
//			SmartDashboard.putString(OI.GEARFINDER_LOCATION, "NOT FOUND");						
		}
		else {
//			SmartDashboard.putString(OI.GEARFINDER_LOCATION, ret.toString());			
		}
		
		return ret;
	}
	
}
