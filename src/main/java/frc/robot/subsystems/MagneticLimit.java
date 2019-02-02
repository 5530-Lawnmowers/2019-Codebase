/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Add your docs here.
 */
   
public class MagneticLimit extends Subsystem {
  AnalogInput MagnetLimit = new AnalogInput(3);
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public double test(){
    return MagnetLimit.getValue();
  }
  //The magnetic sensor is a analog device and so if there is no magnet 
  public boolean Status(){
    if(MagnetLimit.getValue() >= 560){
      return false;
    }
    else if(MagnetLimit.getValue() <= 300){
      return true;
    }
    else{
      return false;
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}

