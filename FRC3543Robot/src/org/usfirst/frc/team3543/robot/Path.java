package org.usfirst.frc.team3543.robot;

import java.util.List;
import java.util.ArrayList;

import org.usfirst.frc.team3543.robot.commands.PlaybackCommand;

public class Path {
	List<Point> points = new ArrayList<>();
	
	public static Path test() {
		return Path	.start()
					.add(1, 0);
	}
	
	public static Path start() {
		return new Path();
	}
	
	public List<Point> getPoints() {
		return this.points;
	}
	
	public Path addAll(Path p) {
		this.points.addAll(p.getPoints());
		return this;
	}
	
	public Path add(double magnitude, double curve) {
		this.points.add(new Point(magnitude, curve));
		return this;
	}
	
	public static class Point {
		public double magnitude = 0;
		public double curve = 0;	
		
		Point(double magnitude, double curve) {
			this.magnitude = magnitude;
			this.curve = curve;
		}
	}

	public boolean isDone() {
		return this.points.isEmpty();
	}
	
	public Point shift() {
		return this.points.remove(0);
	}
}
