/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.helpers.LimelightHelpers;
import frc.robot.subsystems.Drivetrain;

public class AlignHatch extends Command {

  private final double kp = .02;
  private final double kd = 0;
  private double previousError;

  public AlignHatch() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    previousError = LimelightHelpers.getLimelightValue("tx");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double error = LimelightHelpers.getLimelightValue("tx");
    double errorVelocity = error - previousError;
    Drivetrain.frontRightTSRX.set(.35 - kp*error - kd*errorVelocity);
    Drivetrain.frontLeftTSRX.set(.35 + kp*error + kd*errorVelocity);
    previousError = error;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.frontRightTSRX.stopMotor();
    Drivetrain.frontLeftTSRX.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Drivetrain.frontRightTSRX.stopMotor();
    Drivetrain.frontLeftTSRX.stopMotor();
  }
}
