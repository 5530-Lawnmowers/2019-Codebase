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
  double commandStartPosition;

  private static final double kLowHatchPosition = 8.5;
  private static final double kMidHatchPosition = 33;
  private static final double kHighHatchPosition = 56;
  private static final double kLowRocketBallPosition = 22;
  private static final double kMidRocketBallPosition = 44;
  private static final double kHighRocketBallPosition = 61;
  private static final double kDeliverDistance = 3;
  private static final double kHighDeliverDistance = 2;
  private static final double kShuttleBallPosition = 32;
  double kDeliverDistanceAdjusted;
  private static final double kMaxElevatorUpSpeed = .4;
  private static final double kMaxElevatorDownSpeed = .15;


  public AscendElevator(String _position) {
    position = _position;
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    commandStartPosition = Elevator.elevatorSpark1.getEncoder().getPosition();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch(position){
      case "Bot":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) < -kMaxElevatorUpSpeed){
          System.out.println("Running Max");
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          System.out.println("Not Running Max");
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition)));
        }
        break;
      case "LowHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowHatchPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowHatchPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowHatchPosition))));
        }
        break;
      case "MidHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidHatchPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidHatchPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidHatchPosition))));
        }
        break;
      case "LowRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowRocketBallPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowRocketBallPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kLowRocketBallPosition))));
        }
        break;
      case "MidRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidRocketBallPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidRocketBallPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kMidRocketBallPosition))));
        }
        break;
      case "HighRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighRocketBallPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighRocketBallPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighRocketBallPosition))));
        }
        break;
      case "DeliverHatch":
        if(Elevator.elevatorSpark1.getEncoder().getPosition() < Elevator.startPosition - kLowHatchPosition - 4){
          kDeliverDistanceAdjusted = kHighDeliverDistance;
        } else {
          kDeliverDistanceAdjusted = kDeliverDistance;
        }
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted))));
        }
        break;
      case "ShuttleBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kShuttleBallPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kShuttleBallPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kShuttleBallPosition))));
        }
        break;
      case "HighHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighHatchPosition)))) < -kMaxElevatorUpSpeed){
          Elevator.elevatorSpark1.set(-kMaxElevatorUpSpeed);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighHatchPosition)))) > kMaxElevatorDownSpeed){
          Elevator.elevatorSpark1.set(kMaxElevatorDownSpeed);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - kHighHatchPosition))));
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
