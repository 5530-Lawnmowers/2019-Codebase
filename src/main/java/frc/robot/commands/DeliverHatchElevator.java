/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class DeliverHatchElevator extends Command {

  private static final double ELEVATOR_SPEED = .25;

  double commandStartPosition;

  public DeliverHatchElevator() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    commandStartPosition = Elevator.getInches();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Elevator.getInches() < (commandStartPosition + 15)){
      Elevator.elevatorSpark1.set(ELEVATOR_SPEED);
     } else {
      Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.getInches() - (commandStartPosition + 15))));
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
