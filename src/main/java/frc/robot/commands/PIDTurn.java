/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Helpers;
import frc.robot.Robot;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class PIDTurn extends Command{
 

  public PIDTurn() {
    requires(Robot.drivetrain);

  }

  @Override
  protected void initialize() {
    Drivetrain.backRightTalonSRX.follow(Drivetrain.frontRightTalonSRX);
    Drivetrain.backLeftTalonSRX.follow(Drivetrain.frontLeftTalonSRX);
    Helpers.pigeonPIDWrite(Drivetrain.frontRightTalonSRX, 1, 1, 90);
    Helpers.pigeonPIDWrite(Drivetrain.frontLeftTalonSRX, 2, 1, 90);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return Helpers.pigeonPIDController1.onTarget();
  }

  @Override
  protected void end() {
    Helpers.disablePigeonPIDController(1);
    Helpers.disablePigeonPIDController(2);
  }

  @Override
  protected void interrupted() {
    Helpers.disablePigeonPIDController(1);
    Helpers.disablePigeonPIDController(2);
  }
}
