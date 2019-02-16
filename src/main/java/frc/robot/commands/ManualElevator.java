/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualElevator extends Command {
  public ManualElevator() {
    super("ManualAscendRobot");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.frontElevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    
    Elevator.elevatorSpark1.stopMotor();
    Elevator.elevatorSpark2.stopMotor();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(OI.buttons[0].get()){
      Elevator.elevatorSpark1.set(Math.pow(-OI.stick.getY(), 3));
      Elevator.elevatorSpark2.set(Math.pow(-OI.stick.getY(), 3));
    } else {
      if( Elevator.elevatorSpark2.getEncoder().getPosition() < -5){
        Elevator.elevatorSpark1.set(-0.036);
        Elevator.elevatorSpark2.set(-0.036);
      } else {
        Elevator.elevatorSpark1.set(0);
        Elevator.elevatorSpark2.set(0);
      }

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
