/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;

import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.Notifier;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class MPHelper{
	
	private MotionProfileStatus _status = new MotionProfileStatus();
	private CSVHelper _profile;
	private int _state = 0;
	private int _loopTimeout = -1;
	private boolean _bStart = false;
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
	private static final int kMinPointsInTalon = 5;
	private static final int kNumLoopsTimeout = 10;
	
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  
	    		Drivetrain.frontRightTSRX.processMotionProfileBuffer();
	    		Drivetrain.frontLeftTSRX.processMotionProfileBuffer();
	    }
	}
	
	Notifier _notifier = new Notifier(new PeriodicRunnable());
	
    public MPHelper(String filename) {
    		Drivetrain.frontRightTSRX.changeMotionControlFramePeriod(5);
			Drivetrain.frontLeftTSRX.changeMotionControlFramePeriod(5);
    		_notifier.startPeriodic(0.005);
    		_profile = new CSVHelper(filename);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    public void reset() {
    	Drivetrain.frontRightTSRX.clearMotionProfileTrajectories();
		Drivetrain.frontLeftTSRX.clearMotionProfileTrajectories();
		_setValue = SetValueMotionProfile.Disable;
		_loopTimeout = -1;
		_state = 0;
		_bStart = false;
    	
    }
    
    public void control() {
    		Drivetrain.frontRightTSRX.getMotionProfileStatus(_status);
    		Drivetrain.frontLeftTSRX.getMotionProfileStatus(_status);
    		
    		if (_loopTimeout < 0) {
    			
    		} else {
    			
    			if (_loopTimeout == 0) {
    				System.out.println("Something's run");
    			} else {
    				--_loopTimeout;
    			}
    		}
    		
    		if (Drivetrain.frontRightTSRX.getControlMode() != ControlMode.MotionProfile || Drivetrain.frontRightTSRX.getControlMode() != ControlMode.MotionProfile) {
    			System.out.println("NO PASS");
    			_state = 0;
    			_loopTimeout = kNumLoopsTimeout;
    		} else {
    			switch(_state ) {
    			case 0:
    				//Waiting for start to Motion Profile and if we are starting then start filling the talon
    				if (_bStart ) {
    					_bStart = false;
    					_setValue = SetValueMotionProfile.Disable;
    					startFilling();
    					_state = 1;
    					_loopTimeout = -1;
    					System.out.println("STATE 0");
    				}
    				break;
    			case 1:
    				//Checking if we have enough points in the talon to start 
    				if (_status.btmBufferCnt > kMinPointsInTalon) {
    					_setValue = SetValueMotionProfile.Enable;
    					_state = 2;
    					_loopTimeout = kNumLoopsTimeout;
    					System.out.println("STATE 1");
    				}
    				break;
    			case 2:
    				//Checking to make sure there are still enough points in the talon buffer
    				if (_status.isUnderrun == false) {
    					_loopTimeout = kNumLoopsTimeout;
    				}
    				//Checking if the motion profile is done
    				if (_status.activePointValid && _status.isLast) {
    					_setValue = SetValueMotionProfile.Hold;
    					_state = 0;
    					_loopTimeout = -1;
    					
    				}
    				break;
    			}
    		}
    }
    
    private void startFilling() {
    		startFilling(_profile, _profile.getLength());
    }
    
    private double convertToTicks(double inches ) {
    		return 1024 * (inches / (6 * Math.PI));
    }
    
    private double convertVelocity(double inchesPerSecond){
    	return 1024 * (inchesPerSecond / (60 * Math.PI));
    }
    
    private void startFilling (CSVHelper profile, int totalCnt) {
    		TrajectoryPoint rightPoint = new TrajectoryPoint();
    		TrajectoryPoint leftPoint = new TrajectoryPoint();
    		if (_status.hasUnderrun) {
    			System.out.println("Has Underrun");
    			Drivetrain.frontRightTSRX.clearMotionProfileHasUnderrun(0);
    			Drivetrain.frontLeftTSRX.clearMotionProfileHasUnderrun(0);
    			
    		}
		Drivetrain.frontLeftTSRX.clearMotionProfileTrajectories();
		Drivetrain.frontRightTSRX.clearMotionProfileTrajectories();
		
		for (int i = 0; i < totalCnt; ++i) {
			rightPoint.position = convertToTicks(profile.getRightDistance(i));
			rightPoint.velocity = convertVelocity(profile.getRightVelocity(i));
			leftPoint.position = convertToTicks(profile.getLeftDistance(i));
			leftPoint.velocity = convertVelocity(profile.getLeftVelocity(i));
			//TODO: Check this profile Slot Select
			rightPoint.profileSlotSelect0 = 0;
			rightPoint.headingDeg = 0;
			rightPoint.zeroPos = false;
			// rightPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
			leftPoint.profileSlotSelect0 = 0;
			leftPoint.headingDeg = 0;
			leftPoint.zeroPos = false;
			// leftPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
			if (i == 0) {
				rightPoint.zeroPos = true;
				leftPoint.zeroPos = true;
			}
				
			
			
			rightPoint.isLastPoint = false;
			leftPoint.isLastPoint = false;
			if ((i + 1) == totalCnt) {
				rightPoint.isLastPoint = true;
				leftPoint.isLastPoint = true;
			}
			System.out.println("Pusing point: " + rightPoint.velocity + ", " + leftPoint.velocity);
			
			Drivetrain.frontRightTSRX.pushMotionProfileTrajectory(rightPoint);
			Drivetrain.frontLeftTSRX.pushMotionProfileTrajectory(leftPoint);
		}
    }
    
    public void startMotionProfile() {
			Drivetrain.frontRightTSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			Drivetrain.frontLeftTSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			Drivetrain.frontRightTSRX.configMotionProfileTrajectoryPeriod(10, 30);
			Drivetrain.frontLeftTSRX.configMotionProfileTrajectoryPeriod(10, 30);
    		_bStart = true;
    }
    
    public SetValueMotionProfile getSetValue() {
    		return _setValue;
	}
	
	public boolean isDone(){
		return _status.activePointValid && _status.isLast;
	}
}
