/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Sendable;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FrontElevator;

public class Robot extends TimedRobot {
  public static OI oi;

  // Declare Subsystems
  public static FrontElevator frontElevator = new FrontElevator();
  public static Drivetrain drivetrain = new Drivetrain();

  // Declare Commands
  
  // Creating Shuffleboard Objects


  @Override
  public void robotInit() {
    oi = new OI();
    Helpers.createComplexWidget("PID Control", "PIDController", Helpers.pigeonTurnController);
    Helpers.createComplexWidget("PID Control", "TalonView", Helpers.pigeonTalon);
    Helpers.createComplexWidget("PID Control", "PigeonIMU", Helpers.pigeonWrapper);
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
    System.out.println(Helpers.getPigeonYaw());
    
    
  }


  @Override
  public void testPeriodic() {
  }
}
