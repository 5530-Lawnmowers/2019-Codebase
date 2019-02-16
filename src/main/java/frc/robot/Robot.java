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
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.helpers.*;
import frc.robot.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
  public static OI oi;

  // Declare Subsystems
  public static Elevator frontElevator = new Elevator();
  public static Downavator downavator = new Downavator();
  public static Drivetrain drivetrain = new Drivetrain();
  public static Intake intake = new Intake();

  // Declare Commands


  @Override
  public void robotInit() {
    oi = new OI();
    // Creating Shuffleboard Objects
    ShuffleboardHelpers.createComplexWidget("TestingDT", "FR Talon", Drivetrain.frontRightTSRX);
    ShuffleboardHelpers.createComplexWidget("TestingDT", "FL Talon", Drivetrain.frontLeftTSRX);
    ShuffleboardHelpers.createComplexWidget("TestingDT", "BR Talon", Drivetrain.backRightTSRX);
    ShuffleboardHelpers.createComplexWidget("TestingDT", "BL Talon", Drivetrain.backLeftTSRX);
    ShuffleboardHelpers.createComplexWidget("TestingIN", "Intake 1", Intake.intakeTRSX1);
    ShuffleboardHelpers.createComplexWidget("TestingIN", "Intake 2", Intake.intakeTRSX2);
    ShuffleboardHelpers.createSimpleWidget("TestingIN", "Intake Switch", false);
    ShuffleboardHelpers.createSimpleWidget("TestingEL", "UpEncoder", 0);
    ShuffleboardHelpers.createSimpleWidget("TestingEL", "DownEncoder", 0);
    ShuffleboardHelpers.createSimpleWidget("TestingEL", "Elevator", 0);
    ShuffleboardHelpers.createSimpleWidget("TestingEL", "Downavator", 0);

    
  
    CameraServer.getInstance().startAutomaticCapture();
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
    //Initializing Stuff
    Drivetrain.backLeftTSRX.configFactoryDefault();
    Drivetrain.backRightTSRX.configFactoryDefault();
    Drivetrain.frontLeftTSRX.configFactoryDefault();
    Drivetrain.frontRightTSRX.configFactoryDefault();
    Drivetrain.frontRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.pigeon.configFactoryDefault();

    LimelightHelpers.limelightDisabled = true;

    Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    Downavator.downavatorSpark2.follow(Downavator.downavatorSpark1);

    Elevator.elevatorSpark1.setIdleMode(IdleMode.kBrake);
    Elevator.elevatorSpark2.setIdleMode(IdleMode.kBrake);

    Downavator.downavatorSpark1.setIdleMode(IdleMode.kBrake);
    Downavator.downavatorSpark2.setIdleMode(IdleMode.kBrake);

    Drivetrain.frontLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backLeftTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backRightTSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTSRX.setInverted(true);
    Drivetrain.backRightTSRX.setInverted(true);
    Drivetrain.backLeftTSRX.setInverted(true);
    Intake.intakeTRSX2.setInverted(true);
    Drivetrain.backRightTSRX.follow(Drivetrain.frontRightTSRX);
    Drivetrain.backLeftTSRX.follow(Drivetrain.frontLeftTSRX);
    Drivetrain.frontRightTSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    Drivetrain.frontLeftTSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    // PigeonHelpers.resetPigeon();
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    ShuffleboardHelpers.setWidgetValue("TestingEL", "UpEncoder", Elevator.elevatorSpark2.getEncoder().getPosition());
    ShuffleboardHelpers.setWidgetValue("TestingEL", "DownEncoder", Downavator.downavatorSpark1.getEncoder().getPosition());
    ShuffleboardHelpers.setWidgetValue("TestingIN", "Intake Switch", Intake.intakeSwitch.get());
    ShuffleboardHelpers.setWidgetValue("TestingEL", "Downavator", Downavator.downavatorSpark1.get());
    ShuffleboardHelpers.setWidgetValue("TestingEL", "Elevator", Elevator.elevatorSpark1.get());
  }


  @Override
  public void testPeriodic() {
    
  }
}
