package org.usfirst.frc.team3543.robot.motion;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motion.*;

@SuppressWarnings("deprecation")
public class MotionProfile {

	public static final int MAX_POINTS = 128;	 // max for Talon SRX motion controller buffer

	private ArrayList<TrajectoryPoint> points = new ArrayList<>();
	private int profileSlot = 0;
	
	public MotionProfile(int profileSlotSelect) {
		this.profileSlot = profileSlotSelect;
	}
	
	public MotionProfile() {
		this(0);
	}
	
	public void clear() {
		this.points.clear();
	}
	
	public int size() {
		return this.points.size();
	}
	
	public void add(TrajectoryPoint point) {
//		if (this.size() >= MAX_POINTS) {
//			throw new IllegalStateException("Maximum size reached");
//		}
		// fix the point
		if (this.size() == 0) {
			point.zeroPos = true;
		}
		else { // last point no longer last point
			points.get(points.size() - 1).isLastPoint = false;
		}
		point.isLastPoint = true;
		point.profileSlotSelect = this.profileSlot;
		this.points.add(point);
	}
	
	public void add(double position, double velocity) {
		TrajectoryPoint point = new TrajectoryPoint();		
		point.position = position;
		point.velocity = velocity;		
		this.add(point);
	}	
	
	public void pushTo(TalonSRX talon) {
		for (TrajectoryPoint point : this.points) {
			talon.pushMotionProfileTrajectory(point);
		}
	}
	
	public void dump() {
		int i = 0;
		System.out.println("num, vel, pos");
		for (TrajectoryPoint point : this.points) {
			System.out.println(String.format("%d, %.3f, %.3f", (i+1), point.velocity, point.position));
			i++;
		} 
	}
	
	public static void main(String [] args) {
		MotionProfileGenerator gen = new MotionProfileGenerator();
		double distance = 48 / 18.8;	//inches per rotation
		
		MotionProfile profile = gen.generate(distance);
		profile.dump();
	}
}
