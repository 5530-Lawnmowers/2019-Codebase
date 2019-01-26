/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.RobotMap;

public class ToggleDriveChain extends Command {
  
  public ToggleDriveChain() {
    super("ToggleDriveChain");
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_pneumatics);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_pneumatics.power(true);
    if(RobotMap.kHLspeed >= Robot.m_pneumatics.getSpeed()){
      SmartDashboard.putBoolean("low", false);
      Robot.m_pneumatics.High();
    }
    else{
    SmartDashboard.putBoolean("low", true); 
    Robot.m_pneumatics.Low();
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
