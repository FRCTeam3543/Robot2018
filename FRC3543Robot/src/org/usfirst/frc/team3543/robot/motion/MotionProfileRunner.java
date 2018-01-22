package org.usfirst.frc.team3543.robot.motion;

import java.util.List;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Adapted from https://github.com/CrossTheRoadElec/FRC-Examples-STEAMWORKS/blob/master/JAVA_MotionProfileExample/src/org/usfirst/frc/team3539/robot/MotionProfileExample.java
 * 
 * Example logic for firing and managing motion profiles.
 * This example sends MPs, waits for them to finish
 * Although this code uses a CANTalon, nowhere in this module do we changeMode() or call set() to change the output.
 * This is done in Robot.java to demonstrate how to change control modes on the fly.
 * 
 * The only routines we call on Talon are....
 * 
 * changeMotionControlFramePeriod
 * 
 * getMotionProfileStatus		
 * clearMotionProfileHasUnderrun     to get status and potentially clear the error flag.
 * 
 * pushMotionProfileTrajectory
 * clearMotionProfileTrajectories
 * processMotionProfileBuffer,   to push/clear, and process the trajectory points.
 * 
 * getControlMode, to check if we are in Motion Profile Control mode.
 * 
 * Example of advanced features not demonstrated here...
 * [1] Calling pushMotionProfileTrajectory() continuously while the Talon executes the motion profile, thereby keeping it going indefinitely.
 * [2] Instead of setting the sensor position to zero at the start of each MP, the program could offset the MP's position based on current position. 
 */

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

public class MotionProfileRunner {

	enum State { IDLE, WAITING_FOR_MP, ACTIVE };
	/**
	 * The status of the motion profile executer and buffer inside the Talon.
	 * Instead of creating a new one every time we call getMotionProfileStatus,
	 * keep one copy.
	 */
	private MotionProfileStatus _status = new MotionProfileStatus();

	/**
	 * reference to the talon we plan on manipulating. We will not changeMode()
	 * or call set(), just get motion profile status and make decisions based on
	 * motion profile.
	 */
	private WPI_TalonSRX _talon;

	/**
	 * State machine to make sure we let enough of the motion profile stream to
	 * talon before we fire it.
	 */
	private State _state = State.IDLE;
	
	/**
	 * Any time you have a state machine that waits for external events, its a
	 * good idea to add a timeout. Set to -1 to disable. Set to nonzero to count
	 * down to '0' which will print an error message. Counting loops is not a
	 * very accurate method of tracking timeout, but this is just conservative
	 * timeout. Getting time-stamps would certainly work too, this is just
	 * simple (no need to worry about timer overflows).
	 */
	private int _loopTimeout = -1;
	
	/**
	 * If start() gets called, this flag is set and in the control() we will
	 * service it.
	 */
	private boolean _bStart = false;

	/**
	 * Since the CANTalon.set() routine is mode specific, deduce what we want
	 * the set value to be and let the calling module apply it whenever we
	 * decide to switch to MP mode.
	 */
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;

	/**
	 * How many trajectory points do we wait for before firing the motion
	 * profile.
	 */
	private static final int kMinPointsInTalon = 5;
	
	/**
	 * Just a state timeout to make sure we don't get stuck anywhere. Each loop
	 * is about 20ms.
	 */
	private static final int kNumLoopsTimeout = 10;
	
	/**
	 * Lets create a periodic task to funnel our trajectory points into our talon.
	 * It doesn't need to be very accurate, just needs to keep pace with the motion
	 * profiler executer.  Now if you're trajectory points are slow, there is no need
	 * to do this, just call _talon.processMotionProfileBuffer() in your teleop loop.
	 * Generally speaking you want to call it at least twice as fast as the duration
	 * of your trajectory points.  So if they are firing every 20ms, you should call 
	 * every 10ms.
	 */
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  
	    		_talon.processMotionProfileBuffer();    	    			
	    	}
	}
	Notifier _notifier;	
	MotionProfile profile = null;
	double frameRate = 5;
	
	/**
	 * C'tor
	 * 
	 * @param talon
	 *            reference to Talon object to fetch motion profile status from.
	 */
	public MotionProfileRunner(WPI_TalonSRX talon, double frameRate) {
		_talon = talon;
		/*
		 * since our MP is 10ms per point, set the control frame rate and the
		 * notifer to half that
		 */
		this.frameRate = frameRate;
		_talon.changeMotionControlFramePeriod((int) frameRate);
		_notifier = new Notifier(new PeriodicRunnable());
	}

	/**
	 * Called to clear Motion profile buffer and reset state info during
	 * disabled and when Talon is not in MP control mode.
	 */
	public void reset() {		
		_notifier.stop();
		
		/*
		 * Let's clear the buffer just in case user decided to disable in the
		 * middle of an MP, and now we have the second half of a profile just
		 * sitting in memory.
		 */
		_talon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		
//		_setValue = CANTalon.SetValueMotionProfile.Disable;
		/* When we do start running our state machine start at the beginning. */
		_state = State.IDLE;
		_loopTimeout = -1;
		/*
		 * If application wanted to start an MP before, ignore and wait for next
		 * button press
		 */
		_bStart = false;
	}

	/**
	 * Called every loop.
	 */
	public void control() {
		/* Get the motion profile status every loop */
		_talon.getMotionProfileStatus(_status);
		/*
		 * track time, this is rudimentary but that's okay, we just want to make
		 * sure things never get stuck.
		 */
		if (_loopTimeout < 0) {
			/* do nothing, timeout is disabled */
		} else {
			/* our timeout is nonzero */
			if (_loopTimeout == 0) {
				/*
				 * something is wrong. Talon is not present, unplugged, breaker
				 * tripped
				 */
//				instrumentation.OnNoProgress();
			} else {
				--_loopTimeout;
			}
		}

		/* first check if we are in MP mode */
		if (_talon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around
			 * using gamepads or some other mode.
			 */
			_state = State.IDLE;
			_loopTimeout = -1;
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp
			 * progress, and possibly interrupting MPs if thats what you want to
			 * do.
			 */
			switch (_state) {
				case IDLE: /* wait for application to tell us to start an MP */
					if (_bStart) {
						_bStart = false;
						_setValue = SetValueMotionProfile.Disable;
						startFilling(this.profile);
						/*
						 * MP is being sent to CAN bus, wait a small amount of time
						 */
						_state = State.WAITING_FOR_MP;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case WAITING_FOR_MP: 
					/*
					 * wait for MP to stream to Talon, really just the first few
					 * points
					 */
					/* do we have a minimum numberof points in Talon */
					if (_status.btmBufferCnt > kMinPointsInTalon) {
						/* start (once) the motion profile */
						_setValue = SetValueMotionProfile.Enable;
						/* MP will start once the control frame gets scheduled */
						_state = State.ACTIVE;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case ACTIVE: /* check the status of the MP */
					/*
					 * if talon is reporting things are good, keep adding to our
					 * timeout. Really this is so that you can unplug your talon in
					 * the middle of an MP and react to it.
					 */
					if (_status.isUnderrun == false) {
						_loopTimeout = kNumLoopsTimeout;
					}
					/*
					 * If we are executing an MP and the MP finished, start loading
					 * another. We will go into hold state so robot servo's
					 * position.
					 */
					if (_status.activePointValid && _status.isLast) {
						/*
						 * because we set the last point's isLast to true, we will
						 * get here when the MP is done
						 */
						_setValue = SetValueMotionProfile.Hold;
						_state = State.IDLE;
						_loopTimeout = -1;
					}
					break;
			}
		}
		/* printfs and/or logging */
//		instrumentation.process(_status);
	}

	/** Start filling the MPs to all of the involved Talons. */
	private void startFilling(MotionProfile profile) {
		// start the notifier here

		/* did we get an underrun condition since last time we checked ? */
		if (_status.hasUnderrun) {
			/* better log it so we know about it */
//			instrumentation.OnUnderrun();
			/*
			 * clear the error. This flag does not auto clear, this way 
			 * we never miss logging it.
			 */
			_talon.clearMotionProfileHasUnderrun(_loopTimeout);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer
		 * points in memory, clear it.
		 */
		_talon.clearMotionProfileTrajectories();

		/* This is fast since it's just into our TOP buffer */
		for (TrajectoryPoint point : profile.getPoints()) {
			_talon.pushMotionProfileTrajectory(point);
		}
	}

	/**
	 * Called by application to signal Talon to start the buffered MP (when it's
	 * able to).
	 * FIXME - this won't be thread safe
	 */
	public void startMotionProfile(MotionProfile profile) {
		this.profile = profile;
		_bStart = true;
		_notifier.startPeriodic(frameRate / 1000);
	}

	public WPI_TalonSRX getTalon() {
		return _talon;
	}

	/**
	 * 
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
	public SetValueMotionProfile getSetValue() {
		return _setValue;
	}
	
}