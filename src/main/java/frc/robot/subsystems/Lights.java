/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Spark;

/**
 * Add your docs here.
 */
public class Lights extends Subsystem {
public boolean isHatchAlign = false;
  public double liftValue = 0;
  Spark rgb = new Spark(5);
  public boolean inball = false;
  public void setInball(boolean value){
     inball = value;
  }
  public void AHatch(boolean value) {
 isHatchAlign = value;
  }
  public void setLightStage(double value){
    if(value == 0){
      liftValue = 0.59;
    }
    
    else if(value == 1){
      liftValue = 0.61;
    }
    else if(value == 2){
      liftValue = 0.63;
    }
    else if(value == 3){
      liftValue = 0.65;
    }
    else if(value == 4){
      liftValue = 0.69;
    }
    else if(value == 5){
      liftValue = 0.55;
    }

  }
  public void run() {
    if(liftValue != 0){
    rgb.set(liftValue);
    }
    else if(isHatchAlign){
      rgb.set(.11);
    }
    else if(inball){
      rgb.set(-.99);

    }
    


  }
   

  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }







}
