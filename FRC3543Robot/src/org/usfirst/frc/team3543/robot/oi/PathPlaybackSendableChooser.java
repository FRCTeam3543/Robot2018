package org.usfirst.frc.team3543.robot.oi;

import org.usfirst.frc.team3543.robot.Path;
import org.usfirst.frc.team3543.robot.PathProvider;
import org.usfirst.frc.team3543.robot.RecordedPaths;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This powers the pick list for recorded paths
 * 
 * @see org.usfirst.frc.team3543.robot.RecordedPaths
 * @see org.usfirst.frc.team3543.robot.Path
 */
public class PathPlaybackSendableChooser extends SendableChooser<Path> implements PathProvider {

	public PathPlaybackSendableChooser() {
		super();
		Path p;
		// all the stored paths
		String[] starts = { "LEFT", "MIDDLE", "RIGHT" };
		String[] paths;
		addDefault("DEFAULT (NONE)", Path.parse("DEFAULT-NONE;0.0,0.0;"));
		for (int i=0; i<starts.length; i++) {
			paths = RecordedPaths.AUTONOMOUS[i];
			for (int j=0; j<paths.length; j++) {
				p = Path.parse(paths[j]);
				addObject(starts[i] + ' ' + j + ": " + p.getName(), p);
			}
		}
	}

	public void addPath(Path path) {
		addObject(path.getName(), path);
	}

	@Override
	public Path getPath() {
		return getSelected();
	}
	
}
