package ttfft.vision;

/**
 * A place for utility functions
 * 
 * @author MK
 *
 */
public class Utils {

	public static boolean isApproximately(double value, double target, double percentError) {
		return value >= (target * (1 - percentError)) && value <= (target * (1+percentError));
	}

}
