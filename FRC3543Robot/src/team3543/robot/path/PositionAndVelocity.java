package team3543.robot.path;

public class PositionAndVelocity {

	XYTheta position = XYTheta.ZERO;
	XYTheta velocity = XYTheta.ZERO;
		
	public PositionAndVelocity(XYTheta pos, XYTheta vel) {
		this.position = pos;
		this.velocity = vel;
	}
	
	public PositionAndVelocity(XYTheta positionOnly) {
		this(positionOnly, XYTheta.ZERO);
	}
		
	public Path pathToTarget(PositionAndVelocity target, double maxSpeed, double maxAcceleration, double wheelBase, int maxSteps) {
		PathPlanner planner = new PathPlanner(maxSpeed, maxAcceleration, wheelBase);
		return planner.plan(this, target, maxSteps);
	}
	
	public String toString() {
		return String.format("%s,%s", position.toString(), velocity.toString());
	}
	
	public static PositionAndVelocity pose(double x, double y, double theta) {
		return new PositionAndVelocity(new XYTheta(x,y,theta));
	}
}
