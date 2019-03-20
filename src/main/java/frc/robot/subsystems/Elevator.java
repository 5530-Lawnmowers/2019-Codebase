/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.*;

import com.revrobotics.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static final double ENCODER_PER_INCH = 0.9143; //TBD. Change as need be

  public static CANSparkMax elevatorSpark1 = new CANSparkMax(RobotMap.E1, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax elevatorSpark2 = new CANSparkMax(RobotMap.E2, CANSparkMaxLowLevel.MotorType.kBrushless);
  
  public static DigitalInput elevatorTopSwitch = new DigitalInput(RobotMap.ES1);
  public static DigitalInput elevatorBotSwitch = new DigitalInput(RobotMap.ES2);

  public static double startPosition;
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ManualElevator());
  }

  /**
   * Gets the height of the elevator in inches
   * @return the height in inches of elevator
   */
  public static double getInches() {
    return elevatorSpark1.getEncoder().getPosition() * ENCODER_PER_INCH;
  }

  /**
   * Converts height in inches to encoder units
   * @param inches the height in inches
   * @return the corresponding encoder value
   */
  public static double convertToEncoder(double inches) {
    return inches / ENCODER_PER_INCH;
  }
}
