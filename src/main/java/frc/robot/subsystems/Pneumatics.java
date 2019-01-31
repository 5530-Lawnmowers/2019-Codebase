/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
/**
 * Add your docs here.
 */
public class Pneumatics extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Compressor compressor = new Compressor(0);
  DoubleSolenoid solenoid1 = new DoubleSolenoid(1, 2);
  DoubleSolenoid solenoid2 = new DoubleSolenoid(4, 5);
  BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void power(boolean on){
    compressor.setClosedLoopControl(on);
  }
  public void High(){
    solenoid1.set(DoubleSolenoid.Value.kForward);
    solenoid2.set(DoubleSolenoid.Value.kForward);
    }
  public void Low(){
    solenoid1.set(DoubleSolenoid.Value.kReverse);
    solenoid2.set(DoubleSolenoid.Value.kReverse);
  }
}
