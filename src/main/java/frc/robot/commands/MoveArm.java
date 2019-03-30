/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Arm;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;;

public class MoveArm extends Command {
  	String position;
	double maxTime = 200;
  /**
   * 
   * @param _position The position to move the armTRSX1 to: Bot, Top
   */
  public MoveArm(String _position) {
    requires(Robot.arm);
    position = _position;
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
		if (position.equalsIgnoreCase("Top")) {
			Arm.armTRSX1.set(55 * (Arm.armPot.get() - Arm.TARGET_HOLD_HEIGHT) + 0.03);
		}else if (position.equalsIgnoreCase("Bot")) {
			Arm.armTRSX1.set(-.4);
		}else if (position.equalsIgnoreCase("Mid")) {
			Arm.armTRSX1.set(60 * (Arm.armPot.get() - Arm.MID_ARM_HEIGHT) + 0.03);
		}else System.out.println("Incorrect Parameter");
	}
	protected boolean isFinished() {
		if(position.equalsIgnoreCase("Bot") && Arm.armPot.get() >= Arm.MIN_ARM_HEIGHT) return true;
		return false;
	}
	protected void end() {
		Arm.armTRSX1.set(0);
	}
	protected void interrupted() {
		Arm.armTRSX1.set(0);
	}
}
