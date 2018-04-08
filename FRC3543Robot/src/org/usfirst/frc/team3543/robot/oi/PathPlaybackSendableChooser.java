package org.usfirst.frc.team3543.robot.oi;

import com.usfirst.frc.team3543.robot.pathrecording.RecordedPath;
import com.usfirst.frc.team3543.robot.pathrecording.PathProvider;
import com.usfirst.frc.team3543.robot.pathrecording.RecordedPaths;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This powers the pick list for recorded paths
 * 
 * @see com.usfirst.frc.team3543.robot.pathrecording.RecordedPaths
 * @see com.usfirst.frc.team3543.robot.pathrecording.RecordedPath
 */
public class PathPlaybackSendableChooser extends SendableChooser<RecordedPath> implements PathProvider {

	public PathPlaybackSendableChooser() {
		super();
		RecordedPath p;
		// all the stored paths
		String[] starts = { "LEFT", "MIDDLE", "RIGHT" };
		String[] paths;
		addDefault("DEFAULT (NONE)", RecordedPath.parse("DEFAULT-NONE;0.0,0.0;"));
		for (int i=0; i<starts.length; i++) {
			paths = RecordedPaths.AUTONOMOUS[i];
			for (int j=0; j<paths.length; j++) {
				p = RecordedPath.parse(paths[j]);
				addObject(starts[i] + ' ' + j + ": " + p.getName(), p);
			}
		}
	}

	public void addPath(RecordedPath path) {
		addObject(path.getName(), path);
	}

	@Override
	public RecordedPath getPath() {
		return getSelected();
	}
	
}
