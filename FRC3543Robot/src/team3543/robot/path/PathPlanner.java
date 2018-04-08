package team3543.robot.path;

import java.util.logging.Logger;

/**
 * This doesn't work yet, don't use
 * 
 * @author mk
 */
public class PathPlanner {

	private static final Logger LOGGER = Logger.getLogger(PathPlanner.class.getSimpleName());
	
	protected double maxSpeed;
	protected double maxAcceleration;
	protected double wheelBase;
	protected double ramp;
	
	public PathPlanner(double maxSpeed, double maxAcceleration, double wheelBase) {
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.wheelBase = wheelBase;
	}
	
	public Path plan(double x1, double y1, double theta1, double x2, double y2, double theta2, int steps) {
		return plan(PositionAndVelocity.pose(x1, y1, theta1), PositionAndVelocity.pose(x2, y2, theta2), steps);
	}

	/**
	 * Returns a Path
	 * 
	 * @param from
	 * @param to
	 * @param maxSteps - the number of interpolations to do.  Default is 3
	 * @return
	 */
	public Path plan(PositionAndVelocity from, PositionAndVelocity to, int maxSteps) {
		// TODO - handle when a path requires more than a 90 degree rotation in either direction.  If so, we need
		// to align on heading first and then plan the path, or introduce a waypoint.		
		Path path = new Path();		
		path.add(from);
		path.add(to);
		return interpolate(path, maxSteps);
	}
	
	protected Path interpolate(Path path, int step) {
		if (step <= 0) return path;
		Path out = new Path();
		PositionAndVelocity prev = null;
		for (PositionAndVelocity pv : path) {			
			if (prev != null) {
				out.add(prev);
				out.add(interpolatePV(prev,pv));	
				out.add(pv);
			}
			prev = pv;
		}
		return interpolate(out, step-1);
	}
	
	/**
	 * Based on a smooth arc and max velocity and acceleration, produces a midpoint PositionAndVelocity
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public PositionAndVelocity interpolatePV(PositionAndVelocity from, PositionAndVelocity to) {
		// here's the math.  First we figure out the arc between the start and end points,
		// just using the positions.  The position vectors should be considered tangents
		// on a circle of radius r.  
		XYTheta positionDelta = from.position.delta(to.position);	
		
		// this is the radius of the circle.  
		double radius = 
				Math.sqrt(Math.pow(positionDelta.getX(), 2) + Math.pow(positionDelta.getY(), 2))
				/ (2 * Math.sin(positionDelta.getTheta() / 2));
		
		// now we can figure out the radius of the inner and outer wheels.
		double innerWheelRadius = radius - wheelBase;
		double outerWheelRadius = radius + wheelBase;
		// the outer wheel radius is the one we need to constrain velocity-wise.  
		// we use arc = r x theta to get the arc length
		double outerArcLength = outerWheelRadius * positionDelta.getTheta();
		
		LOGGER.info(String.format("Radius %f outerArcLength %f", radius, outerArcLength));
		
		// so now, we can pick position targets along the arc for the outer wheel,
		// based on maximum desired velocity for the motors
		
		// start by assuming we want to be going at peak velocity halfway along the outer arc, in order for the
		// path driven to be smooth.  We need to know if we can get to this peak velocity based 
		// on the max acceleration we ant.  
		// Similarly, we want to know that we can decelerate to the end velocity
		
		double vStart = from.velocity.magnitude();
		double vEnd = to.velocity.magnitude();
		double vMid = maxSpeed;
		double vDiffStart = vMid - vStart;
		double halfArc = outerArcLength / 2;
		double halfTheta = positionDelta.getTheta() / 2;
		// remember distance = v1*t + 0.5*a*t^2
		// so we need to go from vstart to vmid in halfArc without accelerating more than maxAcceleration
		double maxMidpointVStart = Math.sqrt(maxAcceleration * halfArc) + vStart;
		double maxMidpointVEnd = vEnd - Math.sqrt(maxAcceleration * halfArc);
		// the one we want is the lesser of the two
		vMid = Math.min(maxSpeed, Math.min(maxMidpointVStart, maxMidpointVEnd));
		
		// now calculate the mid PositionAndVelocity
		// the velocity vector is the components of the midpoint angle, which is deltaTheta/2
		double midTheta = from.position.getTheta() + positionDelta.getTheta() / 2;
		
		// midPos needs to be along the arc halfway, rotated from the first around the centerpoint 
		// of the arc circle
		// so, we need to translate the starting point so the radius is at the origin,  then rotate, 
		// then translate back
		// this is the origin for the radius
		// BUT we assume the original from point was at the origin, rotate it around the radius,
		// then add back the original from
		XYTheta midPos = XYTheta.ZERO.rotateAround(
							radius * Math.cos(from.position.getTheta()), 
							radius * Math.sin(from.position.getTheta()), 
									halfTheta)
						.translate(from.position.getX(), from.position.getY());
		
		XYTheta midVel = XYTheta.fromPolar(vMid, midTheta);
		// check
		double rsq = Math.pow(radius, 2);	
		
		PositionAndVelocity ret = new PositionAndVelocity(midPos, midVel);
		return ret;
	}
	
	public static void main(String[] args) {
		// follow a path
		PathPlanner pp = new PathPlanner(2, 0.5, 0.5);
		Path path = pp.plan(1, 1, 0, 5, 2.5, -Math.PI/2, 1);
		System.out.println(path);
		(new Plotter()).plot(path, 0.25);
	}
	
}
