package com.team254.lib.util.control;

/**
 * Initialize these in calibration to override them
 * 
 * These were copied as-needed from com.team254.frc2017.Constants, they should be 
 * overridden by our own Calibration (static startup code in that class).
 * 
 * These are here so "hidden" static refs in the Path code that reference a constant compile.
 * TODO - refactor
 * 
 * @author mk
 */
public class PathConstants {
    public static double kPathFollowingMaxAccel = 120.0; // inches per second^2
    public static double kSegmentCompletionTolerance = 0.1; // inches    
}
