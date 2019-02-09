/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Downavator;

public class DropDownavator extends Command {

  private double initialPosition;
  private double counter;
  private final double dropDistance = 0;
  private final double range = 0;

  public DropDownavator() {
    requires(Robot.downavator);
    counter = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initialPosition = Downavator.downavatorSpark1.getEncoder().getPosition();
    Downavator.downavatorSpark1.getPIDController().setReference(initialPosition + dropDistance, ControlType.kPosition);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Downavator.downavatorSpark1.getEncoder().getPosition() >= ((initialPosition + dropDistance) - range) && 
      Downavator.downavatorSpark1.getEncoder().getPosition() <= ((initialPosition + dropDistance) + range)){
        counter ++;
      } else {
        counter = 0;
      }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return counter >= 20;

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Downavator.downavatorSpark1.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Downavator.downavatorSpark1.stopMotor();
  }
}
