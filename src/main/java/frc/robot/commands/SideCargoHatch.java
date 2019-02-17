/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;

public class SideCargoHatch extends Command {
    
    boolean left;

    /**
     * @param side "L" for left side, "R" for right side
     */
    public SideCargoHatch(String side) {
        requires(Robot.drivetrain);
        if(side.equalsIgnoreCase("L")) {
            left = true;
        } else {
            left = false;
        }
    }

    /**
     * Initializes drive train to drive forward
     */
    @Override
    protected void initialize() {
        Drivetrain.frontLeftTSRX.set(0.1);
        Drivetrain.frontRightTSRX.set(0.1);
    }

    @Override
    protected void execute() {

    }

    /**
     * @return true when infrared triggered
     * TODO: assumes infrared is true when triggered
     */
    @Override
    protected boolean isFinished() {
        if(left) {
            return Drivetrain.infraredLeft.get();
        } else {
            return Drivetrain.infraredRight.get();
        }
    }

    /**
     * Stops motors
     */
    @Override
    protected void end() {
        Drivetrain.frontLeftTSRX.stopMotor();
        Drivetrain.frontRightTSRX.stopMotor();
    }

    /**
     * Stops motors
     */
    @Override
    protected void interrupted() {
        Drivetrain.frontLeftTSRX.stopMotor();
        Drivetrain.frontRightTSRX.stopMotor();
    }
}