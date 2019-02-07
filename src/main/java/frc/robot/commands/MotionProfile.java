/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.*;
import frc.robot.helpers.*;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class MotionProfile extends Command {
  MPHelper mpHelper;
  public MotionProfile(String filename) {
    requires(Robot.drivetrain);
    mpHelper = new MPHelper(filename);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mpHelper.reset();
    mpHelper.startMotionProfile();
    Drivetrain.frontRightTSRX.config_kP(0, 0.05);
    Drivetrain.frontLeftTSRX.config_kP(0, 0.05);
    Drivetrain.frontRightTSRX.set(ControlMode.MotionProfile, mpHelper.getSetValue().value);
    Drivetrain.frontLeftTSRX.set(ControlMode.MotionProfile, mpHelper.getSetValue().value);
    mpHelper.control();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(mpHelper.getSetValue());
    Drivetrain.frontRightTSRX.set(ControlMode.MotionProfile, mpHelper.getSetValue().value);
		Drivetrain.frontLeftTSRX.set(ControlMode.MotionProfile, mpHelper.getSetValue().value);
    mpHelper.control();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return mpHelper.isDone();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, 0);
    Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, 0);
    Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, 0);
  }
}
