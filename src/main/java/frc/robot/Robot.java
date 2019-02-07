/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;

import frc.robot.helpers.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class Robot extends TimedRobot {
  public static OI oi;

  // Declare Subsystems
  public static Elevator frontElevator = new Elevator();
  public static Drivetrain drivetrain = new Drivetrain();

  // Declare Commands


  @Override
  public void robotInit() {
    oi = new OI();
    // Creating Shuffleboard Objects
    ShuffleboardHelpers.createComplexWidget("DriveStraight", "Right Talon", Drivetrain.frontRightTSRX);
    ShuffleboardHelpers.createComplexWidget("DriveStraight", "Left Talon", Drivetrain.frontLeftTSRX);

    //Initializing Stuff
    Drivetrain.backLeftTSRX.configFactoryDefault();
    Drivetrain.backRightTSRX.configFactoryDefault();
    Drivetrain.frontLeftTSRX.configFactoryDefault();
    Drivetrain.frontRightTSRX.configFactoryDefault();
    Drivetrain.pigeon.configFactoryDefault();

    LimelightHelpers.limelightDisabled = true;

    Drivetrain.frontLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTSRX.setInverted(true);
    Drivetrain.backRightTSRX.setInverted(true);
    Drivetrain.backRightTSRX.set(ControlMode.Follower, RobotMap.FR);
    Drivetrain.backLeftTSRX.set(ControlMode.Follower, RobotMap.FL);
    Drivetrain.frontRightTSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    Drivetrain.frontLeftTSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
  }


  @Override
  public void robotPeriodic() {
  }


  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void autonomousInit() {


  }


  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    PigeonHelpers.resetPigeon();
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void testPeriodic() {
    
  }
}
