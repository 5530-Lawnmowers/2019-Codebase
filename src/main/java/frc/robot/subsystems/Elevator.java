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
}
