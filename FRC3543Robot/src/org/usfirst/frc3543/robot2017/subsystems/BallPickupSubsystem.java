// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3543.robot2017.subsystems;

import org.usfirst.frc3543.robot2017.RobotMap;
import org.usfirst.frc3543.robot2017.commands.*;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for the ball pickup/dump task.  Includes the pickup motor and the dumper servos.
 * 
 * @author MK
 */
public class BallPickupSubsystem extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final Servo ballDumpServo1 = RobotMap.ballPickupSubsystemBallDumpServo1;
    private final Servo ballDumpServo2 = RobotMap.ballPickupSubsystemBallDumpServo2;
    private final SpeedController victorSpeedController = RobotMap.ballPickupSubsystemVictorSpeedController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void openDumper() {
    	this.ballDumpServo1.setAngle(180);
    	this.ballDumpServo2.setAngle(0);    	    	    	
    }
    
    public void closeDumper() {
    	this.ballDumpServo1.setAngle(90);
    	this.ballDumpServo2.setAngle(90);    	    	
    }
    
	public void enableMotor(boolean isOn) {
		if (isOn) {
			victorSpeedController.set(-0.5);
		}
		else {
			victorSpeedController.set(0);
		}
		
	}
}

