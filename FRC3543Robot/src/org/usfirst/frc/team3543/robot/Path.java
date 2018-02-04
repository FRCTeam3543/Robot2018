package org.usfirst.frc.team3543.robot;

import java.util.List;
import java.util.ArrayList;

public class Path {
	List<Point> points = new ArrayList<>();
	
	
	public static Path test() {
		return Path.parse(PATH_TEST);
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
	
	public Path add(double left, double right) {
		this.points.add(new Point(left, right));
		return this;
	}
	
	public String export() {
		StringBuilder sb = new StringBuilder("PATH|");
		for (Point p : this.points) {
			sb.append(String.format("%.4f,%.4f|", p.left, p.right));
		}
		return sb.toString();
	}
	
	public static Path parse(String s) {
		String[] pairs = s.split("|");
		String[] lr;
		Path p = Path.start();
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
	
	///////////// Paths /////////////////
	public static final String NONE = "PATH|0,0|";
	public static final String PATH_TEST = "PATH|1,0|"; // FIXME, paste the path here

}
