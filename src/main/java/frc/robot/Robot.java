/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;

import frc.robot.helpers.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FrontElevator;
import frc.robot.subsystems.Pneumatics;

public class Robot extends TimedRobot {
  public static OI oi;

  // Declare Subsystems
  public static FrontElevator frontElevator = new FrontElevator();
  public static Drivetrain drivetrain = new Drivetrain();
  public static Pneumatics pneumatics = new Pneumatics();

  // Declare Commands


  @Override
  public void robotInit() {
    Robot.pneumatics.power(true);
    oi = new OI();
    // Creating Shuffleboard Objects
    ShuffleboardHelpers.createComplexWidget("PIDControl", "Gyro", PigeonHelpers.pigeonWrapper);
    ShuffleboardHelpers.createComplexWidget("PIDControl", "PIDController1", PigeonHelpers.pigeonPIDController1);
    ShuffleboardHelpers.createComplexWidget("PIDControl", "PIDController2", PigeonHelpers.pigeonPIDController2);
    ShuffleboardHelpers.createComplexWidget("Limelight", "FRTalon", Drivetrain.frontRightTalonSRX);
    ShuffleboardHelpers.createComplexWidget("Limelight", "FLTalon", Drivetrain.frontLeftTalonSRX);
    ShuffleboardHelpers.createComplexWidget("Limelight", "PID1", LimelightHelpers.limelightPIDController1);
    ShuffleboardHelpers.createComplexWidget("Limelight", "PID2", LimelightHelpers.limelightPIDController2);

    //Initializing Stuff
    Drivetrain.backLeftTalonSRX.configFactoryDefault();
    Drivetrain.backRightTalonSRX.configFactoryDefault();
    Drivetrain.frontLeftTalonSRX.configFactoryDefault();
    Drivetrain.frontRightTalonSRX.configFactoryDefault();
    Drivetrain.pigeon.configFactoryDefault();

    Drivetrain.frontLeftTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backLeftTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backRightTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.set(ControlMode.Follower, RobotMap.FR);
    Drivetrain.backLeftTalonSRX.set(ControlMode.Follower, RobotMap.FL);
    Drivetrain.frontRightTalonSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    Drivetrain.frontLeftTalonSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
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
    System.out.println(LimelightHelpers.getLimelightValue("tx"));
    
  }


  @Override
  public void testPeriodic() {
    
  }
}
