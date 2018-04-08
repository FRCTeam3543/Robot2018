package team3543.robot.path;

public class XYTheta {

	public static final XYTheta ZERO = new XYTheta(0,0,0);
	
	private double x = 0;
	private double y = 0;
	private double theta = 0;
	
	public XYTheta(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}

	public XYTheta delta(XYTheta toPosition) {
		return new XYTheta(toPosition.x - this.x, toPosition.y - y, toPosition.theta - theta);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getTheta() {
		return theta;
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public XYTheta translate(double deltaX, double deltaY) {
		return new XYTheta(this.x + deltaX, this.y + deltaY, this.theta);
	}
	
	public XYTheta rotateBy(double angle) {
		double s = Math.sin(angle), c = Math.cos(angle);
		return new XYTheta(this.x * c- this.y * s, this.y * c + this.x * s, this.theta + angle);
	}
	
	public XYTheta rotateAround(double x, double y, double angle) {
		return this.translate(-x, -y).rotateBy(angle).translate(x, y);
	}
	public static XYTheta fromPolar(double magnitude, double theta) {		
		return new XYTheta(magnitude * Math.cos(Math.PI/2 - theta), magnitude * Math.sin(Math.PI / 2 - theta), theta);
	}
	
	public String toString() {
		return String.format("%f,%f,%f", x,y,theta);
	}
}
