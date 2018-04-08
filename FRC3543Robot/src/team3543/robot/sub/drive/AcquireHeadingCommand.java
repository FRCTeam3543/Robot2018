package team3543.robot.sub.drive;

import org.usfirst.frc.team3543.robot.Calibration;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3543.robot.cal.PIDF;
import team3543.robot.sub.DriveLine;

/**
 * Command to acquire a heading.  
 * 
 * This uses PID to acquire, but isFinished once onTarget (which means the PID
 * is not used to hold at the target, just to find it).
 * 
 * @author mk
 *
 */
public class AcquireHeadingCommand extends DriveModeCommand {
	private HeadingPIDController headingPID;
	
	public AcquireHeadingCommand(DriveLine driveLine) {
		super(driveLine);
		headingPID = new HeadingPIDController(driveLine);
		headingPID.disable();
	}
	
	public void setTarget(double degrees) {
		this.headingPID.setSetpoint(degrees);
	}
	
	@Override
	protected void initialize() {
		this.headingPID.enable();
	}
		
	@Override
	public void execute() {
		// do nothing, maybe we should update the SmartDashboard
		SmartDashboard.putNumber(getName() + " target", headingPID.getSetpoint());
		SmartDashboard.putNumber(getName() + " actual", headingPID.get());
		SmartDashboard.putNumber(getName() + " error", headingPID.getError());		
	}

	@Override
	public boolean isFinished() {
		return headingPID.onTarget();
	}
    
	protected void disablePID() {
		headingPID.disable();
	}
	
    @Override
	public synchronized void cancel() {
    		disablePID();
    		super.cancel();
	}

    @Override
    protected void end() {
    		disablePID();
    		super.end();
    }
    
    /**
     * The PID Controller for heading
     * 
     * @author mk
     */
	static class HeadingPIDController extends PIDController {
		DriveLine driveLine;
		
		HeadingPIDController(DriveLine driveLine) {
			super(	DriveLine.headingPIDF.kP,
					DriveLine.headingPIDF.kI,
					DriveLine.headingPIDF.kD,
					DriveLine.headingPIDF.kF,					
					driveLine.getGyro(),
					new PIDOutput() {
						@Override
						public void pidWrite(double output) {
							driveLine.setMotors(ControlMode.Position, output, -output);						
						}						
					});
					
			this.setAbsoluteTolerance(DriveLine.headingPIDF.tolerance);
			this.driveLine = driveLine;    			
		}
	}    
}
