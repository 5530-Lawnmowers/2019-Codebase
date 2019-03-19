/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.OI;

import edu.wpi.first.wpilibj.command.Command;


public class ManualArm extends Command {
  public ManualArm() {
    requires(Robot.arm);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if( OI.buttons[1].get()){
      if( Arm.armPot.get() < 0.966 & OI.stick.getY() >= 0){
        Arm.armTRSX1.set(OI.stick.getY() * 100 * (Arm.armPot.get() - Arm.TARGET_HOLD_HEIGHT));
      } else if(Arm.armPot.get() > 0.974 & OI.stick.getY() <= 0) {
        Arm.armTRSX1.set(OI.stick.getY() * 100 * (Arm.MIN_ARM_HEIGHT - Arm.armPot.get()) + .05);
      } else if(Arm.armPot.get() > 0.974 & OI.stick.getY() > 0){
        Arm.armTRSX1.set(OI.stick.getY() + 0.05);
      } else{
        Arm.armTRSX1.set(OI.stick.getY());

      }
    } else {
      if(Arm.armPot.get() > 0.978){
        Arm.armTRSX1.set(0.05);
      } else {
        Arm.armTRSX1.stopMotor();
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
