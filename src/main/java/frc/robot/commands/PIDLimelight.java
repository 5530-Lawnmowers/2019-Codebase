/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.helpers.LimelightHelpers;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PIDLimelight extends Command {
  int counter;
  public PIDLimelight() {
    requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Drivetrain.backRightTSRX.follow(Drivetrain.frontRightTSRX);
    Drivetrain.backLeftTSRX.follow(Drivetrain.frontLeftTSRX);

    counter = 0;
    LimelightHelpers.limelightPIDWrite(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Drivetrain.frontRightTSRX.set(LimelightHelpers.limelightPIDController1.get());
    Drivetrain.frontLeftTSRX.set(LimelightHelpers.limelightPIDController1.get());
    if(LimelightHelpers.limelightPIDController1.onTarget()) {
      counter ++;
    } else {
      counter = 0;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return counter > 30;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    LimelightHelpers.disableLimelightPIDController();
    Drivetrain.frontRightTSRX.stopMotor();
    Drivetrain.frontLeftTSRX.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    LimelightHelpers.disableLimelightPIDController();
  }
}
