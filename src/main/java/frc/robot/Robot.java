/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.robot.Helpers.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FrontElevator;

public class Robot extends TimedRobot {
  public static OI oi;

  // Declare Subsystems
  public static FrontElevator frontElevator = new FrontElevator();
  public static Drivetrain drivetrain = new Drivetrain();

  // Declare Commands


  @Override
  public void robotInit() {
    oi = new OI();
    MainHelpers.initializeRobot();
    // Creating Shuffleboard Objects
    MainHelpers.createComplexWidget("PIDControl", "Gyro", MainHelpers.pigeonWrapper);
    MainHelpers.createComplexWidget("PIDControl", "PIDController1", MainHelpers.pigeonPIDController1);
    MainHelpers.createComplexWidget("PIDControl", "PIDController2", MainHelpers.pigeonPIDController2);
    MainHelpers.createComplexWidget("Limelight", "FRTalon", Drivetrain.frontRightTalonSRX);
    MainHelpers.createComplexWidget("Limelight", "FLTalon", Drivetrain.frontLeftTalonSRX);
    MainHelpers.createComplexWidget("Limelight", "PID1", MainHelpers.limelightPIDController1);
    MainHelpers.createComplexWidget("Limelight", "PID2", MainHelpers.limelightPIDController2);
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
    MainHelpers.resetPigeon();
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    System.out.println(MainHelpers.getLimelightValue("tx"));
    
  }


  @Override
  public void testPeriodic() {
  }
}
