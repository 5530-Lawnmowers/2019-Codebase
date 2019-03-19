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
import frc.robot.RobotMap;
import frc.robot.subsystems.*;

public class DropDownavator extends Command {

  private double initialDownavatorPosition;
  private double intitialElevatorPosition;
  private double counter;
  private int state;
  private final double DROP_DISTANCE = 3; 

  public DropDownavator() {
    requires(Robot.downavator);
    requires(Robot.elevator);
    counter = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = 0;
    initialDownavatorPosition = Downavator.downavatorSpark1.getEncoder().getPosition();
    intitialElevatorPosition = Elevator.elevatorSpark1.getEncoder().getPosition();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(state == 0){ //drops downavator
      Downavator.downavatorSpark1.set(.4);
      if(Downavator.downavatorSpark1.getEncoder().getPosition() >= initialDownavatorPosition + DROP_DISTANCE) {
        Downavator.downavatorSpark1.stopMotor();
        state = 1;
      }
    } else if(state == 1) { // drops elevator
      Elevator.elevatorSpark1.set(.1);
      if(Elevator.elevatorSpark1.getEncoder().getPosition() >= intitialElevatorPosition + DROP_DISTANCE - 1) {
        Elevator.elevatorSpark1.stopMotor();
        state = 2;
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return state == 2;

  }

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
