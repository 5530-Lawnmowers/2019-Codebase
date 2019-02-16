/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class AscendElevator extends Command {
  public AscendElevator() {
    requires(Robot.frontElevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Elevator.elevatorSpark1.set(.1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Elevator.elevatorTopSwitch.get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Elevator.elevatorSpark1.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Elevator.elevatorSpark1.stopMotor();
  }
}
