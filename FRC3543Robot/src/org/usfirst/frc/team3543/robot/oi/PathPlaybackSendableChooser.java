package org.usfirst.frc.team3543.robot.oi;

import org.usfirst.frc.team3543.robot.Path;
import org.usfirst.frc.team3543.robot.PathProvider;
import org.usfirst.frc.team3543.robot.RecordedPaths;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class PathPlaybackSendableChooser extends SendableChooser<Path> implements PathProvider {

	public PathPlaybackSendableChooser() {
		super();
		int i = 0;
		Path p;
		// all the stored paths
		for (String path : RecordedPaths.PATHS) {
			p = Path.parse(RecordedPaths.PATHS[i]);
			if (i++ == 0) {				
				addDefault(p.getName(),p); // NONE path		
			}
			else {
				addObject(p.getName(), p);
			}
		}
	}
	
	@Override
	public void addDefault(String name, Path object) {
		super.addDefault(name, object);
	}
	
	public void addPath(Path path) {
		addObject(path.getName(), path);
	}

	@Override
	public Path getPath() {
		return getSelected();
	}
	
}
