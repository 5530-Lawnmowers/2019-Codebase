/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//This is the code for switching drive trains
//TODO define constant kHLspeed
/* if(RobotMap.kHLspeed >= Robot.m_pneumatics.getSpeed()){
      SmartDashboard.putBoolean("low", false);
      Robot.m_pneumatics.High();
      SmartDashboard.updateValues();

    }
    else{
    SmartDashboard.putBoolean("low", true); 
    SmartDashboard.updateValues();
    Robot.m_pneumatics.Low();
    Robot.cosmetics.on();
    }*/
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {
  public Drive() {
    requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Drivetrain.frontLeftTalonSRX.set(0.1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.frontLeftTalonSRX.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Drivetrain.frontLeftTalonSRX.set(0);
  }
}
