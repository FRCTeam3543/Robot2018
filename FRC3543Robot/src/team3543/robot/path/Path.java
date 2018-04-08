package team3543.robot.path;

import java.util.ArrayList;

public class Path extends ArrayList<PositionAndVelocity> {
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (PositionAndVelocity pv : this) {
			sb.append(String.format("%d,%s\n", i++, pv));
		}
		return sb.toString();
	}
}
