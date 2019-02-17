/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;

public class SimpleDriveForward extends Command {
  int counter;
  int max;
  public SimpleDriveForward(int time) {
    requires(Robot.drivetrain);
    max = time;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    counter = 0;
    Drivetrain.frontLeftTSRX.set(0.25);
    Drivetrain.frontRightTSRX.set(0.25);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    counter ++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return counter >= max;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.frontLeftTSRX.stopMotor();
    Drivetrain.frontRightTSRX.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Drivetrain.frontLeftTSRX.stopMotor();
    Drivetrain.frontRightTSRX.stopMotor();

  }
}
