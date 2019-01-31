/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Helpers.*;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PIDTurn extends Command{
  
  int counter;

  public PIDTurn() {
    requires(Robot.drivetrain);

  }

  @Override
  protected void initialize() {
    counter = 0;
    PigeonHelpers.resetPigeon();
    PigeonHelpers.pigeonPIDShuffleboard(1);
  }

  @Override
  protected void execute() {
    if(PigeonHelpers.pigeonPIDController1.onTarget()) counter ++; 
    else counter = 0;
  }

  @Override
  protected boolean isFinished() {
    return counter >= 25;
  }

  @Override
  protected void end() {
    PigeonHelpers.disablePigeonPIDController();
  }

  @Override
  protected void interrupted() {
    PigeonHelpers.disablePigeonPIDController();
  }
}
