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
    requires(Robot.elevator);
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = 0;
    counter = 0;
    Downavator.downavatorSpark2.follow(Downavator.downavatorSpark1);
    Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    Elevator.elevatorSpark1.set(.3);    
    Downavator.downavatorSpark1.set(.25);
    
    startDownavatorPosition = Downavator.downavatorSpark1.getEncoder().getPosition(); 
    startElevatorPosition = Elevator.elevatorSpark1.getEncoder().getPosition(); 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    System.out.println("Downavator: " + Downavator.downavatorSpark1.get() + "Elevator: " + Elevator.elevatorSpark1.get());
    Robot.lights.setLightStage(state);
    switch(state){
      case 0:
        Downavator.downavatorDrive.set(0.15);
        Elevator.elevatorSpark1.set(0.05 * ((Downavator.downavatorSpark1.getEncoder().getPosition() - startDownavatorPosition) - (Elevator.elevatorSpark1.getEncoder().getPosition() - startElevatorPosition)) + .3);
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
        Downavator.downavatorSpark1.set(.05 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (28.5 + startDownavatorPosition))));
        if( Elevator.elevatorSpark1.getEncoder().getPosition() > (startElevatorPosition - 1)){
          Elevator.elevatorSpark1.set(-0.3);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (startElevatorPosition - 1))));
          counter = 0;
          state = 3;
        }
        break;
      case 3:
        Elevator.elevatorSpark1.set(-0.03 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (startElevatorPosition - 1))));
        Downavator.downavatorDrive.set(0.5);
        Drivetrain.frontLeftTSRX.set(0.3);
        Drivetrain.frontRightTSRX.set(0.3);
        Downavator.downavatorSpark1.set(.07 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (28.5 + startDownavatorPosition))));
        counter ++;
        if (counter >= 100){
          Downavator.downavatorDrive.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          Drivetrain.frontLeftTSRX.stopMotor();
          state = 4;
        }
        break;
      case 4:
        Drivetrain.frontLeftTSRX.set(0.1);
        Drivetrain.frontRightTSRX.set(0.1);
        Downavator.downavatorSpark1.set(-0.25);
        if(!Downavator.downavatorTopSwitch.get()){
          Downavator.downavatorSpark1.stopMotor();
          Drivetrain.frontLeftTSRX.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          counter = 0;
          state = 5;
          }
        break;
      case 5:
        Drivetrain.frontRightTSRX.set(0.2);
        Drivetrain.frontLeftTSRX.set(0.2);
        counter ++;
        if( counter >= 25){
          Drivetrain.frontLeftTSRX.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          state = 6;
        }
        break;
      default:
        break;        
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return state == 6;
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
