/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class PickupBall extends Command {
  public PickupBall() {
    requires(Robot.intake);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Intake.intakeTRSX1.set(.6);
    Intake.intakeTRSX2.set(.6);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Intake.intakeSwitch.get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Intake.intakeTRSX1.stopMotor();
    Intake.intakeTRSX2.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Intake.intakeTRSX1.stopMotor();
    Intake.intakeTRSX2.stopMotor();

  }
}
