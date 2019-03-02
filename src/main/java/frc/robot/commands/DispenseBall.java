/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

public class DispenseBall extends Command {
  int counter;
  public DispenseBall() {
    requires(Robot.intake);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    counter = 0;
    Intake.intakeTRSX1.set(-0.6);
    Intake.intakeTRSX2.set(-0.6);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    counter ++;
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return counter >= 40;
    
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lights.setInball(false);
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
