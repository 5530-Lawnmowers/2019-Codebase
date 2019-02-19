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
    boolean prior;
    int targetTape;
    int currentTape;

    /**
     * @param side "L" for left side, "R" for right side
     * @param count the targeted hatch
     */
    public SideCargoHatch(String side, int count) {
        requires(Robot.drivetrain);
        prior = false;
        currentTape = 0;

        //2 tapes per hatch, so the 2n-1th tape is our target.
        targetTape = count * 2 - 1;


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
        if(left) {
            if(Drivetrain.infraredLeft.get() && prior == false) {
                currentTape++;
                prior = true;
            } else if(!Drivetrain.infraredLeft.get() && prior == true) {
                prior = false;
            }
        } else {
            if(Drivetrain.infraredRight.get() && prior == false) {
                currentTape++;
                prior = true;
            } else if(!Drivetrain.infraredRight.get() && prior == true) {
                prior = false;
            }
        }
    }

    /**
     * @return true when infrared triggered
     * TODO: assumes infrared is true when triggered
     */
    @Override
    protected boolean isFinished() {
        return currentTape == targetTape;
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