/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class AscendElevator extends Command {
  String position;
  public AscendElevator(String _position) {
    position = _position;
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(position);
    switch(position){
      case "Bot":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition)));
        }
        break;
      case "LowHatch":

        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 8)))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 8)))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 8))));
        }
        break;
      case "MidHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 20)))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 20)))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 20))));
        }
        break;
      case "LowRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 22)))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 22)))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 22))));
        }
        break;
      case "MidRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 43.5)))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 43.5)))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 43.5))));
        }
        break;
      case "HighRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 61)))) < -0.3){
          Elevator.elevatorSpark1.set(-0.3);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 61)))) > 0.075){
          Elevator.elevatorSpark1.set(0.075);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - 61))));
        }
        break;
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
    System.out.println("Ended");
    Elevator.elevatorSpark1.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("Interrupted");
    Elevator.elevatorSpark1.stopMotor();
  }
}
