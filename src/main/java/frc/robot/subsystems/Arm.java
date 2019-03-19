/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;



/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static final double minArmHeight = 0.981;
  public static final double midArmHeight = 0.972;
  public static final double maxArmHeight = 0.964;
  public static final double HORIZONTAL_POSITION = 0.979; //TBD. Should be potentiameter at horizontal
  public static final double VERTICAL_POSITION = 0.950;   //TBD, Should be potentiameter at vertical
  public static final double ARM_LENGTH = 20;             //TBD, placeholder

  public static WPI_TalonSRX armTRSX1 = new WPI_TalonSRX(RobotMap.A);
  public static AnalogPotentiometer armPot = new AnalogPotentiometer(RobotMap.AP);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    // setDefaultCommand(new ManualArm());
  }

  /**
   * Converts from an angle in degrees to potentiameter reading. 0 degrees is horizontal.
   * @param degrees the angle in degrees of the position
   * @return the potentiometer reading of the position
   *  */
  public static double convertToPotentiometer(double degrees) {
    return ((VERTICAL_POSITION - HORIZONTAL_POSITION)/90.0) * degrees + HORIZONTAL_POSITION;
  }
  /**
   * Gets the current arm position in degrees. 0 degrees is horizontal
   * @return the arm position in degrees
   */
  public static double getDegrees() {
    return (90.0/(VERTICAL_POSITION - HORIZONTAL_POSITION)) * (armPot.get() - HORIZONTAL_POSITION);
  }
}
