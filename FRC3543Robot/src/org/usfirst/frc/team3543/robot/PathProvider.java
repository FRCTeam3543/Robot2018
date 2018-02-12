package org.usfirst.frc.team3543.robot;

public interface PathProvider {

	Path getPath();
	
	static PathProvider forPath(final Path path) {
		return new PathProvider() {

			@Override
			public Path getPath() {
				return path;
			}
		};
	}
	
	static PathProvider forPath(String path) {
		return forPath(Path.parse(path));
	}
}
