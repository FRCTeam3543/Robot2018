package org.usfirst.frc.team3543.robot;

import java.util.List;
import java.util.ArrayList;

public class Path {
	List<Point> points = new ArrayList<>();
	private String name;

	Path() {
		this("PATH");
	}
	
	Path(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		if (name.contains(";")) {
			throw new IllegalArgumentException("Path name cannot contain a ; char");
		}	
		this.name = name;
	}
	
	public static Path start() {
		return new Path();
	}
	
	public static Path start(String name) {
		return new Path(name);
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
	
	public static Path parse(String s) {
		Robot.log("PARSE "+s);
		
		String[] pairs = s.split(";");
		String[] lr;
		Path p = Path.start(pairs[0]);
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
	// Add recorded paths here
	public static final String [] PATHS = {
		"NONE;0,0;"
		,"TEST PATH;0.0000,0.0000;0.0000,0.0000;0.0000,0.0000;0.0000,0.0000;0.0000,0.0000;0.0000,0.0000;0.0000,0.0000;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0001,-0.0001;-0.0005,-0.0005;-0.0022,-0.0022;-0.0039,-0.0039;-0.0074,-0.0074;-0.0103,-0.0103;-0.0156,-0.0156;-0.0269,-0.0269;-0.0413,-0.0413;-0.0706,-0.0706;-0.0928,-0.0928;-0.0928,-0.0928;-0.1026,-0.0996;-0.1182,-0.1151;-0.1292,-0.1261;-0.1292,-0.1261;-0.1465,-0.1435;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1588,-0.1557;-0.1780,-0.1749;-0.1780,-0.1749;-0.1780,-0.1749;-0.1780,-0.1749;-0.1983,-0.1953;-0.1983,-0.1953;-0.1983,-0.1953;-0.1983,-0.1953;-0.1983,-0.1953;-0.1983,-0.1953;-0.2125,-0.2094;-0.2125,-0.2094;-0.2125,-0.2094;-0.2125,-0.2094;-0.2125,-0.2094;-0.2125,-0.2094;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2346,-0.2316;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2470;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.2500,-0.2450;-0.1983,-0.1933;-0.1588,-0.1537;-0.1588,-0.1585;-0.1182,-0.1182;-0.1026,-0.1026;-0.0791,-0.0791;-0.0587,-0.0587;-0.0198,-0.0198;-0.0039,-0.0039;0.0000,0.0000;0.0000,0.0000;0.0002,0.0002;0.0002,0.0002;0.0030,0.0030;0.0050,0.0050;0.0089,0.0089;0.0089,0.0089;0.0089,0.0089;0.0089,0.0089;0.0122,0.0122;0.0179,0.0179;0.0248,0.0248;0.0452,0.0452;0.0558,0.0558;0.0635,0.0635;0.0635,0.0635;0.0760,0.0760;0.0760,0.0760;0.0760,0.0760;0.0760,0.0760;0.0760,0.0760;0.0760,0.0760;0.0760,0.0760;0.0849,0.0849;0.0849,0.0849;0.0992,0.0992;0.0992,0.0992;0.0992,0.0992;0.1094,0.1094;0.1094,0.1094;0.1256,0.1256;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1550,0.1550;0.1742,0.1742;0.1711,0.1742;0.2055,0.2086;0.2055,0.2086;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2202,0.2232;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2509,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.2489,0.2540;0.1876,0.1876;0.1742,0.1742;0.1742,0.1742;0.1742,0.1742;0.1550,0.1550;0.1428,0.1428;0.1428,0.1428;0.1428,0.1428;0.1256,0.1256;0.1094,0.1094;0.0760,0.0760;0.0760,0.0760;0.0452,0.0452;0.0050,0.0050;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;0.0001,-0.0001;"
	};
}
