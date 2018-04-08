package team3543.robot.cal;

public class PIDF {
	public static final double DEFAULT_TOLERANCE = 1.0;
	
	public double kP 		= 0.0; // proportional gain
	public double kI 		= 0.0; // integral gain
	public double kD 		= 0.0; // derivative gain
	public double kF 		= 0.0; // feed-forward gain
	public double tolerance = 1.0; // tolerance as percentage
	
	public PIDF(final double p, final double i, final double d, final double f, final double tolerance) {
		this.kP = p;
		this.kI = i;
		this.kD = d;
		this.kF = f;
		this.tolerance = tolerance;		
	}

	public PIDF() {
		this(0,0,0,0,1);
	}
	
	public PIDF(double p, double i, double d, double f) {
		this(p,i,d,f,DEFAULT_TOLERANCE);
	}

	public PIDF(double p, double i, double d) {
		this(p,i,d,0,DEFAULT_TOLERANCE);
	}

}
