package team3543.robot.cal;

public class PIDF {

	public double kP = 0.0;	// proportional gain
	public double kI = 0.0; // integral gain
	public double kD = 0.0; // derivative gain
	public double kF = 0.0; // feed-forward gain
	public double tolerance = 0.001;	// tolerance
	
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
}
