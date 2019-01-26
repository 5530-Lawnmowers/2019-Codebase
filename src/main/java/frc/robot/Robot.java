/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

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
    Helpers.initializeRobot();
    // Creating Shuffleboard Objects
    Helpers.createComplexWidget("PIDControl", "Gyro", Helpers.pigeonWrapper);
    Helpers.createComplexWidget("PIDControl", "PIDController1", Helpers.pigeonPIDController1);
    Helpers.createComplexWidget("PIDControl", "PIDController2", Helpers.pigeonPIDController2);
    Helpers.createComplexWidget("Limelight", "FRTalon", Drivetrain.frontRightTalonSRX);
    Helpers.createComplexWidget("Limelight", "FLTalon", Drivetrain.frontLeftTalonSRX);
    Helpers.createComplexWidget("Limelight", "PID1", Helpers.limelightPIDController1);
    Helpers.createComplexWidget("Limelight", "PID2", Helpers.limelightPIDController2);
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
    Helpers.resetPigeon();
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    System.out.println(Helpers.getLimelightValue("tx"));
    
  }


  @Override
  public void testPeriodic() {
  }
}
