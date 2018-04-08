package com.usfirst.frc.team3543.robot.pathrecording;

import java.util.List;
import java.util.ArrayList;

public class RecordedPath {
	List<Point> points = new ArrayList<>();
	private String name;

	RecordedPath() {
		this("PATH");
	}
	
	RecordedPath(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		if (name.contains(";")) {
			throw new IllegalArgumentException("Path name cannot contain a ; char");
		}	
		this.name = name;
	}
	
	public static RecordedPath start() {
		return new RecordedPath();
	}
	
	public static RecordedPath start(String name) {
		return new RecordedPath(name);
	}
	
	public List<Point> getPoints() {
		return this.points;
	}
	
	public RecordedPath addAll(RecordedPath p) {
		if (p != null)
			this.points.addAll(p.getPoints());
		return this;
	}
	
	public RecordedPath add(double left, double right) {
		this.points.add(new Point(left, right));
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public String export() {
		StringBuilder sb = new StringBuilder(getName());
		sb.append(";");
		for (Point p : this.points) {
			sb.append(String.format("%.4f,%.4f;", p.left, p.right));
		}
		return sb.toString();
	}
	
	public static RecordedPath parse(String s) {
//		Robot.log("PARSE "+s);
		
		String[] pairs = s.split(";");
		String[] lr;
		RecordedPath p = RecordedPath.start(pairs[0]);
		// from 1 to N-1 because of first and last delim
		for (int i=1; i<pairs.length-1; i++) {
			lr = pairs[i].split(",");
			try {				
				p.add(Double.parseDouble(lr[0]), Double.parseDouble(lr[1]));
			}
			catch (Exception ex) {
				System.err.println(pairs[i] + ": " + ex.getMessage());
			}
		}
		return p;
	}
	
	public static class Point {
		public double left = 0;
		public double right = 0;	
		
		Point(double left, double right) {
			this.left = left;
			this.right = right;
		}
	}

	public boolean isDone() {
		return this.points.isEmpty();
	}
	
	public Point shift() {
		return this.points.remove(0);
	}	
}
