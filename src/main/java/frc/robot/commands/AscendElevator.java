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

  private static final double k_LOW_HATCH_POSITION = 8.5;
  private static final double k_MID_HATCH_POSITION = 33;
  private static final double k_HIGH_HATCH_POSITION = 56;
  private static final double k_LOW_ROCKET_BALL_POSITION = 22;
  private static final double k_MID_ROCKET_BALL_POSITION = 44;
  private static final double k_HIGH_ROCKET_BALL_POSITION = 61;
  private static final double k_DELIVER_DISTANCE = 3;
  private static final double k_HIGH_DELIVER_DISTANCE = 2;
  private static final double k_SHUTTLE_BALL_POSITION = 32;
  private static final double k_MAX_ELEVATOR_UP_SPEED = .4;
  private static final double k_MAX_ELEVATOR_DOWN_SPEED = .15;
  private static final double k_PICKUP_HATCH_POSITION = 3;
  boolean isDone = false;

  double kDeliverDistanceAdjusted;


  public AscendElevator(String _position) {
    position = _position;
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    commandStartPosition = Elevator.elevatorSpark1.getEncoder().getPosition();
    isDone = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch(position){
      case "Bot":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) < -k_MAX_ELEVATOR_UP_SPEED){
          System.out.println("Running Max");
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          System.out.println("Not Running Max");
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - Elevator.startPosition)));
        }
        break;
      case "LowHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_HATCH_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_HATCH_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_HATCH_POSITION))));
        }
        break;
      case "MidHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_HATCH_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_HATCH_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_HATCH_POSITION))));
        }
        break;
      case "LowRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_ROCKET_BALL_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_ROCKET_BALL_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_LOW_ROCKET_BALL_POSITION))));
        }
        break;
      case "MidRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_ROCKET_BALL_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_ROCKET_BALL_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_MID_ROCKET_BALL_POSITION))));
        }
        break;
      case "HighRocketBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_ROCKET_BALL_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_ROCKET_BALL_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_ROCKET_BALL_POSITION))));
        }
        break;
      case "DeliverHatch":
        if(Elevator.elevatorSpark1.getEncoder().getPosition() < Elevator.startPosition - k_LOW_HATCH_POSITION - 4){
          kDeliverDistanceAdjusted = k_HIGH_DELIVER_DISTANCE;
        } else {
          kDeliverDistanceAdjusted = k_DELIVER_DISTANCE;
        }
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else{
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (commandStartPosition + kDeliverDistanceAdjusted))));
        }
        break;
      case "ShuttleBall":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_SHUTTLE_BALL_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_SHUTTLE_BALL_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_SHUTTLE_BALL_POSITION))));
        }
        break;
      case "HighHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_HATCH_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_HATCH_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_HIGH_HATCH_POSITION))));
        }
        break;
      case "PickupHatch":
        if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_PICKUP_HATCH_POSITION)))) < -k_MAX_ELEVATOR_UP_SPEED){
          Elevator.elevatorSpark1.set(-k_MAX_ELEVATOR_UP_SPEED);
        } else if((-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_PICKUP_HATCH_POSITION)))) > k_MAX_ELEVATOR_DOWN_SPEED){
          Elevator.elevatorSpark1.set(k_MAX_ELEVATOR_DOWN_SPEED);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.1 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (Elevator.startPosition - k_PICKUP_HATCH_POSITION))));
        }
        break;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone;
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
