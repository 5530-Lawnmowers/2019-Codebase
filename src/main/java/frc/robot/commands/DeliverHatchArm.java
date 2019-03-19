/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class DeliverHatchArm extends Command {
  final double SET_POINT_WEIGHT = 1;
  final double FINAL_ANGLE = 75;
  final double FINAL_ERROR_WEIGHT = 1;

  double startingDegree;
  double startingHeight;

  public DeliverHatchArm() {
    requires(Robot.arm);
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startingHeight = Elevator.getInches();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double deltaHeight = Elevator.getInches() - startingHeight;
    double degreeSetpoint = startingDegree + Math.asin(Arm.ARM_LENGTH/deltaHeight);
    double error = degreeSetpoint - Arm.getDegrees();
    double finalError = FINAL_ANGLE - Arm.getDegrees();

    Arm.armTRSX1.set((SET_POINT_WEIGHT/(FINAL_ERROR_WEIGHT * finalError)) * error);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return FINAL_ANGLE - Arm.getDegrees() < 5;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Arm.armTRSX1.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Arm.armTRSX1.set(0);
  }
}
