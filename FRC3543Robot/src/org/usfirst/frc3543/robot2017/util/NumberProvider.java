package org.usfirst.frc3543.robot2017.util;

/**
 * Interface to something that provides input to a command.  
 * 
 * Intended to be used by an initialize() method to get the inputs/gains etc. 
 * to the command.  This was created so that sources of values could be
 * provided in the constructor, but then the actual values deferred to the
 * execute() or initialize() methods.  This, for example, allows outputs from
 * the previous steps in a command group to provide inputs to the subsequent 
 * commands.
 * 
 * It also allows re-use of commands.  For instance the "drive forward" command
 * is used by the feedback gear approach command using a dynamic but fixed distance 
 * argument, but is also used by the SmartDashboard calibration tools. The former 
 * uses a fixed value NumberProvider, but the latter uses one provided by the
 * field in SmartDashboard.
 * 
 * @author MK
 *
 */
public interface NumberProvider {

	public double getValue();
	
	public static NumberProvider fixedValue(double value) {
		return new NumberProvider() {

			@Override
			public double getValue() {
				return value;
			}
		};
	}
}
