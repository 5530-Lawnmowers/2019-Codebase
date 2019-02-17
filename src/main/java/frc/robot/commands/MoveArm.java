/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;;

public class MoveArm extends Command {
  String position;
	boolean flag = false;
	double counter;
	double maxTime = 200;
  /**
   * 
   * @param _position The position to move the armTRSX1 to: Bot, Top
   */
  public MoveArm(String _position) {
    requires(Robot.intake);
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
			Intake.armTRSX1.set(-15 * (Intake.armPot.get() - .97) - 0.06);
			// Intake.armTRSX1.set(ControlMode.PercentOutput, .0057*(Intake.armPot.get() - Intake.maxArmHeight) + .16); 
			if(counter < maxTime) counter ++;
			else flag = true;
		}else if (position.equalsIgnoreCase("Bot")) {
			if (Intake.armPot.get() <= Intake.minArmHeight) {
				Intake.armTRSX1.set(-1);
			}else {
				Intake.armTRSX1.stopMotor();
				flag = true;
			}
		}else System.out.println("Incorrect Parameter");
	}
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
		Intake.armTRSX1.set(0);
	}
	protected void interrupted() {
		Intake.armTRSX1.set(0);
	}
}
