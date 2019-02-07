/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;

public class DriveForward extends Command {

  /** Tracking variables */
  boolean _firstCall = false;
  boolean _state = false;
  double _targetAngle = 0;
  double distance;


  public DriveForward(double _distance) {
    requires(Robot.drivetrain);
    distance = _distance;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    /* Disable all motor controllers */
		Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, 0);
		Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, 0);
		
		/* Configure the left Talon's selected sensor as local QuadEncoder */
		Drivetrain.frontLeftTSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,				// Local Feedback Source
													RobotMap.PID_PRIMARY,					// PID Slot for Source [0, 1]
													RobotMap.kTimeoutMs);					// Configuration Timeout

		/* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
		Drivetrain.frontRightTSRX.configRemoteFeedbackFilter(Drivetrain.frontLeftTSRX.getDeviceID(),					// Device ID of Source
												RemoteSensorSource.TalonSRX_SelectedSensor,	// Remote Feedback Source
												RobotMap.REMOTE_0,							// Source number [0, 1]
												RobotMap.kTimeoutMs);						// Configuration Timeout
		
		/* Configure the Pigeon IMU to the other Remote Slot on the Right Talon */
		Drivetrain.frontRightTSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(),
												RemoteSensorSource.Pigeon_Yaw,
												RobotMap.REMOTE_1,	
												RobotMap.kTimeoutMs);
		
		/* Setup Sum signal to be used for Distance */
		Drivetrain.frontRightTSRX.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, RobotMap.kTimeoutMs);	// Feedback Device of Remote Talon
		Drivetrain.frontRightTSRX.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, RobotMap.kTimeoutMs);	// Quadrature Encoder of current Talon

		/* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
		Drivetrain.frontRightTSRX.configSelectedFeedbackSensor(	FeedbackDevice.SensorSum, 
													RobotMap.PID_PRIMARY,
													RobotMap.kTimeoutMs);
		
		/* Scale Feedback by 0.5 to half the sum of Distance */
		Drivetrain.frontRightTSRX.configSelectedFeedbackCoefficient(	0.5, 						// Coefficient
														RobotMap.PID_PRIMARY,		// PID Slot of Source 
														RobotMap.kTimeoutMs);		// Configuration Timeout
		
		/* Configure Remote Slot 1 [Pigeon IMU's Yaw] to be used for Auxiliary PID Index */
		Drivetrain.frontRightTSRX.configSelectedFeedbackSensor(	FeedbackDevice.RemoteSensor1,
													RobotMap.PID_TURN,
													RobotMap.kTimeoutMs);
		
		/* Scale the Feedback Sensor using a coefficient */
		Drivetrain.frontRightTSRX.configSelectedFeedbackCoefficient(	1,
														RobotMap.PID_TURN,
														RobotMap.kTimeoutMs);
		
		/* Configure output and sensor direction */
		Drivetrain.frontLeftTSRX.setInverted(false);
		Drivetrain.frontLeftTSRX.setSensorPhase(false);
		Drivetrain.frontRightTSRX.setInverted(true);
		Drivetrain.frontRightTSRX.setSensorPhase(true);
		
		/* Set status frame periods to ensure we don't have stale data */
		Drivetrain.frontRightTSRX.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTSRX.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.kTimeoutMs);

		/* Configure neutral deadband */
		Drivetrain.frontRightTSRX.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTSRX.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		/** 
		 * Max out the peak output (for all modes).  
		 * However you can limit the output of a given PID object with configClosedLoopPeakOutput().
		 */
		Drivetrain.frontLeftTSRX.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontLeftTSRX.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);

		/* FPID Gains for distance servo */
		Drivetrain.frontRightTSRX.config_kP(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kP, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kI(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kI, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kD(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kD, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kF(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kF, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_IntegralZone(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kIzone, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.configClosedLoopPeakOutput(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kPeakOutput, RobotMap.kTimeoutMs);

		/* FPID Gains for turn servo */
		Drivetrain.frontRightTSRX.config_kP(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kP, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kI(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kI, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kD(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kD, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_kF(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kF, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.config_IntegralZone(RobotMap.kSlot_Turning, (int)RobotMap.kGains_Turning.kIzone, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.configClosedLoopPeakOutput(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kPeakOutput, RobotMap.kTimeoutMs);
			
		/**
		 * 1ms per loop.  PID loop can be slowed down if need be.
		 * For example,
		 * - if sensor updates are too slow
		 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
		 * - sensor movement is very slow causing the derivative error to be near zero.
		 */
        int closedLoopTimeMs = 1;
        Drivetrain.frontRightTSRX.configClosedLoopPeriod(0, closedLoopTimeMs, RobotMap.kTimeoutMs);
        Drivetrain.frontRightTSRX.configClosedLoopPeriod(1, closedLoopTimeMs, RobotMap.kTimeoutMs);

		/**
		 * configAuxPIDPolarity(boolean invert, int timeoutMs)
		 * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
		 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
		 */
		Drivetrain.frontRightTSRX.configAuxPIDPolarity(false, RobotMap.kTimeoutMs);

		/* Initialize */
		_firstCall = true;
		_state = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    _targetAngle = Drivetrain.frontRightTSRX.getSelectedSensorPosition(1);

    if (_firstCall) {
      System.out.println("This is Drive Straight Distance with the Auxiliary PID using the Pigeon yaw.");
      System.out.println("Servo [-6, 6] rotations while also maintaining a straight heading.\n");
	  zeroDistance();
	  zeroSensors();
      
      /* Determine which slot affects which PID */
      Drivetrain.frontRightTSRX.selectProfileSlot(RobotMap.kSlot_Distanc, RobotMap.PID_PRIMARY);
      Drivetrain.frontRightTSRX.selectProfileSlot(RobotMap.kSlot_Turning, RobotMap.PID_TURN);
    }
    
    /* Calculate targets from gamepad inputs */
    double target_sensorUnits = convertToTicks(distance);
    double target_turn = _targetAngle;
    
    /* Configured for Position Closed loop on Quad Encoders' Sum and Auxiliary PID on Pigeon's Yaw */
    Drivetrain.frontRightTSRX.set(ControlMode.Position, target_sensorUnits, DemandType.AuxPID, target_turn);
    Drivetrain.frontLeftTSRX.follow(Drivetrain.frontRightTSRX, FollowerType.AuxOutput1);
	_firstCall = false;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, 0);
	Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, 0);
	Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, 0);
}

  private double convertToTicks(double inches ) {
    return 1024 * (inches / (6 * Math.PI));
  }

  	/** Zero all sensors, both Pigeon and Talons */
	void zeroSensors() {
		Drivetrain.frontLeftTSRX.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);
		Drivetrain.pigeon.setYaw(0, RobotMap.kTimeoutMs);
		Drivetrain.pigeon.setAccumZAngle(0, RobotMap.kTimeoutMs);
		System.out.println("[Quadrature Encoders + Position] All sensors are zeroed.\n");
  }
  
  	/** Zero quadrature encoders on Talon */
	void zeroDistance(){
		Drivetrain.frontLeftTSRX.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);
		Drivetrain.frontRightTSRX.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);
		System.out.println("[Quadrature Encoders] All encoders are zeroed.\n");
	}
}
