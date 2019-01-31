/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;

public class DriveForward extends Command {

  /** Tracking variables */
  boolean _firstCall = false;
  boolean _state = false;
  double _targetAngle = 0;


  public DriveForward() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    /* Disable all motor controllers */
		Drivetrain.frontRightTalonSRX.set(ControlMode.PercentOutput, 0);
		Drivetrain.frontLeftTalonSRX.set(ControlMode.PercentOutput, 0);
		
		/* Configure the left Talon's selected sensor as local QuadEncoder */
		Drivetrain.frontLeftTalonSRX.configSelectedFeedbackSensor(	FeedbackDevice.QuadEncoder,				// Local Feedback Source
													RobotMap.PID_PRIMARY,					// PID Slot for Source [0, 1]
													RobotMap.kTimeoutMs);					// Configuration Timeout

		/* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
		Drivetrain.frontRightTalonSRX.configRemoteFeedbackFilter(Drivetrain.frontLeftTalonSRX.getDeviceID(),					// Device ID of Source
												RemoteSensorSource.TalonSRX_SelectedSensor,	// Remote Feedback Source
												RobotMap.REMOTE_0,							// Source number [0, 1]
												RobotMap.kTimeoutMs);						// Configuration Timeout
		
		/* Configure the Pigeon IMU to the other Remote Slot on the Right Talon */
		Drivetrain.frontRightTalonSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(),
												RemoteSensorSource.Pigeon_Yaw,
												RobotMap.REMOTE_1,	
												RobotMap.kTimeoutMs);
		
		/* Setup Sum signal to be used for Distance */
		Drivetrain.frontRightTalonSRX.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, RobotMap.kTimeoutMs);	// Feedback Device of Remote Talon
		Drivetrain.frontRightTalonSRX.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, RobotMap.kTimeoutMs);	// Quadrature Encoder of current Talon
		
		/* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
		Drivetrain.frontRightTalonSRX.configSelectedFeedbackSensor(	FeedbackDevice.SensorSum, 
													RobotMap.PID_PRIMARY,
													RobotMap.kTimeoutMs);
		
		/* Scale Feedback by 0.5 to half the sum of Distance */
		Drivetrain.frontRightTalonSRX.configSelectedFeedbackCoefficient(	0.5, 						// Coefficient
														RobotMap.PID_PRIMARY,		// PID Slot of Source 
														RobotMap.kTimeoutMs);		// Configuration Timeout
		
		/* Configure Remote Slot 1 [Pigeon IMU's Yaw] to be used for Auxiliary PID Index */
		Drivetrain.frontRightTalonSRX.configSelectedFeedbackSensor(	FeedbackDevice.RemoteSensor1,
													RobotMap.PID_TURN,
													RobotMap.kTimeoutMs);
		
		/* Scale the Feedback Sensor using a coefficient */
		Drivetrain.frontRightTalonSRX.configSelectedFeedbackCoefficient(	1,
														RobotMap.PID_TURN,
														RobotMap.kTimeoutMs);
		
		/* Configure output and sensor direction */
		Drivetrain.frontLeftTalonSRX.setInverted(false);
		Drivetrain.frontLeftTalonSRX.setSensorPhase(true);
		Drivetrain.frontRightTalonSRX.setInverted(true);
		Drivetrain.frontRightTalonSRX.setSensorPhase(true);
		
		/* Set status frame periods to ensure we don't have stale data */
		Drivetrain.frontRightTalonSRX.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTalonSRX.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.kTimeoutMs);

		/* Configure neutral deadband */
		Drivetrain.frontRightTalonSRX.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTalonSRX.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		/** 
		 * Max out the peak output (for all modes).  
		 * However you can limit the output of a given PID object with configClosedLoopPeakOutput().
		 */
		Drivetrain.frontLeftTalonSRX.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTalonSRX.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);

		/* FPID Gains for distance servo */
		Drivetrain.frontRightTalonSRX.config_kP(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kP, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kI(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kI, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kD(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kD, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kF(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kF, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_IntegralZone(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kIzone, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.configClosedLoopPeakOutput(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kPeakOutput, RobotMap.kTimeoutMs);

		/* FPID Gains for turn servo */
		Drivetrain.frontRightTalonSRX.config_kP(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kP, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kI(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kI, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kD(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kD, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_kF(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kF, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.config_IntegralZone(RobotMap.kSlot_Turning, (int)RobotMap.kGains_Turning.kIzone, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTalonSRX.configClosedLoopPeakOutput(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kPeakOutput, RobotMap.kTimeoutMs);
			
		/**
		 * 1ms per loop.  PID loop can be slowed down if need be.
		 * For example,
		 * - if sensor updates are too slow
		 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
		 * - sensor movement is very slow causing the derivative error to be near zero.
		 */
        int closedLoopTimeMs = 1;
        Drivetrain.frontRightTalonSRX.configClosedLoopPeriod(0, closedLoopTimeMs, RobotMap.kTimeoutMs);
        Drivetrain.frontRightTalonSRX.configClosedLoopPeriod(1, closedLoopTimeMs, RobotMap.kTimeoutMs);

		/**
		 * configAuxPIDPolarity(boolean invert, int timeoutMs)
		 * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
		 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
		 */
		Drivetrain.frontRightTalonSRX.configAuxPIDPolarity(false, RobotMap.kTimeoutMs);

		/* Initialize */
		_firstCall = true;
		_state = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
