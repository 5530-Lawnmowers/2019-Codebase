/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.*;


public class ManualDownavator extends Command {
  public ManualDownavator() {
    requires(Robot.downavator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    Downavator.downavatorSpark1.stopMotor();
    Downavator.downavatorSpark2.stopMotor();

    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(OI.buttons[1].get()){
      Downavator.downavatorSpark1.set(Math.pow(-OI.stick.getY(), 3));
      Downavator.downavatorSpark2.set(Math.pow(-OI.stick.getY(), 3));
    } else {
      Downavator.downavatorSpark1.stopMotor();
      Downavator.downavatorSpark2.stopMotor();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
