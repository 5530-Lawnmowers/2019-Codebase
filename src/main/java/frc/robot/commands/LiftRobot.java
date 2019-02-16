/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.*;

public class LiftRobot extends Command {

  double startDownavatorPosition;
  double startElevatorPosition;
  double elevatorVelocity;
  double downavatorVelocity;
  int state;
  int counter;

  public LiftRobot() {
    requires(Robot.downavator);
    requires(Robot.frontElevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = 0;
    counter = 0;
    Downavator.downavatorSpark2.follow(Downavator.downavatorSpark1);
    Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    Elevator.elevatorSpark1.set(.175);    
    Downavator.downavatorSpark1.set(.11);
    
    startDownavatorPosition = Downavator.downavatorSpark1.getEncoder().getPosition(); 
    startElevatorPosition = Elevator.elevatorSpark1.getEncoder().getPosition(); 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("Downavator: " + Downavator.downavatorSpark1.get() + "Elevator: " + Elevator.elevatorSpark1.get());
    switch(state){
      case 0:
        Elevator.elevatorSpark1.set(0.05 * ((Downavator.downavatorSpark1.getEncoder().getPosition() - startDownavatorPosition) - (Elevator.elevatorSpark1.getEncoder().getPosition() - startElevatorPosition)) + .175);
        if (Downavator.downavatorSpark1.getEncoder().getPosition() >= startDownavatorPosition + 28.5) {
          Downavator.downavatorSpark1.set(0.05);
        }
        if (Elevator.elevatorSpark1.getEncoder().getPosition() >= startElevatorPosition + 29.5){
          Elevator.elevatorSpark1.set(0.0875);
        }
        if ((Downavator.downavatorSpark1.getEncoder().getPosition() >= startDownavatorPosition + 28.5) && (Elevator.elevatorSpark1.getEncoder().getPosition() >= startElevatorPosition + 29.5)) {
          
          Downavator.downavatorDrive.set(.5);
          state = 1;
        }
        break;
      case 1:
        counter ++;
        Elevator.elevatorSpark1.set(0.0875 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (29.5 + startElevatorPosition))));
        Downavator.downavatorSpark1.set(.05 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (28.5 + startDownavatorPosition))));
        if(counter == 100){
          Downavator.downavatorDrive.stopMotor();
          state = 2;
        }
        break;
      case 2:
        break;
      default:
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
    System.out.println("FINISHED LIFT");
    Elevator.elevatorSpark1.stopMotor();
    Downavator.downavatorSpark1.stopMotor();
    Downavator.downavatorDrive.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Elevator.elevatorSpark1.stopMotor();
    Downavator.downavatorSpark1.stopMotor();
    Downavator.downavatorDrive.stopMotor();
  }
}
