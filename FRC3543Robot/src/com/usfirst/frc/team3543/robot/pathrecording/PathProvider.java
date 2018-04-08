package com.usfirst.frc.team3543.robot.pathrecording;

public interface PathProvider {

	RecordedPath getPath();
	
	static PathProvider forPath(final RecordedPath path) {
		return new PathProvider() {

			@Override
			public RecordedPath getPath() {
				return path;
			}
		};
	}
	
	static PathProvider forPath(String path) {
		return forPath(RecordedPath.parse(path));
	}
}
