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
public class Lights extends Subsystem {
  Spark leds = new Spark(5);
  private double value = 0;
  public void setLightValue(double amount){
    leds.set(amount);
  }
  public void FlashFast(){
    value = -.59;
  }
  public void GreenBlue(){
    value = -.9;
  }
  public void test(double amount){
    //leds.set(amount);
  }
  public void SlowRainbow(){
    value = -.45;
  }
  public void SolidBlue(){
    value = .85;
  }
  public void SolidGreen(){
    value = .74;
  }
  public void SolidRed(){
    value = .59;
  }
  public void BlinkinRed(){
    value = -.25;
  }
  public void BlinkinBlue(){
    value = -.23;
  }
  public void RainbowStatic(){
    value = -.42;
  }
  public void BreathRed(){
    value = -.17;
  }
  public void BreathBlue(){
    value = -.15;
  }
  public void StrobeRed(){
    value = -.11;
  }
  public void StrobeBlue(){
    value = -.09;
  }
  public void SolidPink(){
    value = .57;
  }
  public void GreenBlueSinelon(){
    value = .55;
  }
  public void RainbowBounce(){
    value = -.79;
  }
  public void SemiSolidRed(){
    value = -.31;
  }
  public void SemiSolidBlue(){
    value = -.29;
  }
  public void BlueShot(){
    value = -.83;
  }
  public void RedShot(){
    value = -.85;
  }
  

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
