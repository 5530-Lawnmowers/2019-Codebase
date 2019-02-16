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
  private final double dropDownavatorDistance = 7.71;
  private final double dropElevatorDistance = 7.71;  
  private final double range = 0.1;

  public DropDownavator() {
    requires(Robot.downavator);
    requires(Robot.frontElevator);
    counter = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = 0;
    initialDownavatorPosition = Downavator.downavatorSpark1.getEncoder().getPosition();
    intitialElevatorPosition = Elevator.elevatorSpark1.getEncoder().getPosition();
    Downavator.downavatorSpark1.getPIDController().setP(RobotMap.pidSlots[1][0]);
    Downavator.downavatorSpark1.getPIDController().setI(RobotMap.pidSlots[1][1]);
    Downavator.downavatorSpark1.getPIDController().setD(RobotMap.pidSlots[1][2]);
    Elevator.elevatorSpark1.getPIDController().setP(RobotMap.pidSlots[2][0]);
    Elevator.elevatorSpark1.getPIDController().setI(RobotMap.pidSlots[2][1]);
    Elevator.elevatorSpark1.getPIDController().setD(RobotMap.pidSlots[2][2]);
    Downavator.downavatorSpark2.follow(Downavator.downavatorSpark1);
    Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    Downavator.downavatorSpark1.getPIDController().setReference(initialDownavatorPosition + dropDownavatorDistance, ControlType.kPosition);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(state == 0){ // Checks if the Downavator is within the designated range using encoder values
      if(Downavator.downavatorSpark1.getEncoder().getPosition() >= ((initialDownavatorPosition + dropDistance) - range) && 
      Downavator.downavatorSpark1.getEncoder().getPosition() <= ((initialDownavatorPosition + dropDistance) + range)){
        counter ++;
      } else {
        counter = 0;
      }
      if(counter >= 20){
        state = 1;
      }
    } else if(state == 1) { // Sets the PID for the elevator
      Elevator.elevatorSpark1.getPIDController().setReference(intitialElevatorPosition + dropDistance, ControlType.kPosition);
      counter = 0;
      state = 2;
    } else if(state == 2){ // Checks if the Elevator is within the designated range using encoder values 
        if(Elevator.elevatorSpark1.getEncoder().getPosition() >= ((intitialElevatorPosition + dropDistance) - range) && 
        Elevator.elevatorSpark1.getEncoder().getPosition() <= ((intitialElevatorPosition + dropDistance) + range)){
          counter ++;
        } else {
          counter = 0;
        }
        if(counter >= 20){
          state = 3;
        }
    }
  }

  @Override
  protected boolean isFinished() {
    return state == 3;

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
