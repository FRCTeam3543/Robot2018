package org.usfirst.frc3543.robot2017.commands;

import org.usfirst.frc3543.robot2017.OI;
import org.usfirst.frc3543.robot2017.RobotMap;
import org.usfirst.frc3543.robot2017.util.DegreesToRadiansNumberProvider;
import org.usfirst.frc3543.robot2017.util.NumberProvider;
import org.usfirst.frc3543.robot2017.util.SmartDashboardNumberProvider;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command to package up the full autonomous gear drop command.
 * 
 * - drives forward by a fixed distance (established by the mode switches on the bot)
 * - turns by a fixed angle (also established by mode)
 * - activates the FeedbackApporachGearDropCommand to finish docking
 * 
 * @author MK
 *
 */
public class GearDropAutonomousCommandGroup extends CommandGroup {
	DriveForwardByDistanceCommand driveForward;
	RotateByAngleCommand rotate;
	
	public GearDropAutonomousCommandGroup(NumberProvider distanceProvider, NumberProvider angleInRadiansProvider) {
		driveForward = new DriveForwardByDistanceCommand(distanceProvider, new SmartDashboardNumberProvider(OI.DEFAULT_LINEAR_GAIN, RobotMap.DEFAULT_LINEAR_GAIN));
		rotate = new RotateByAngleCommand(angleInRadiansProvider, new SmartDashboardNumberProvider(OI.DEFAULT_ROTATION_GAIN, RobotMap.DEFAULT_ROTATION_GAIN));
		addSequential(driveForward);
		addSequential(rotate);
		addSequential(new FeedbackApproachGearDropCommand());
	}
	
	public GearDropAutonomousCommandGroup() {
		this(new SmartDashboardNumberProvider(OI.GEARDROP_APPROACH_INIITAL_STATIC_DISTANCE, 0),
			new DegreesToRadiansNumberProvider(new SmartDashboardNumberProvider(OI.GEARDROP_APPROACH_INITIAL_STATIC_ROTATION, 0))
		);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
	}
}
