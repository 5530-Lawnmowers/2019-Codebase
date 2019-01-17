/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Helpers {

  //// HELPER VARIABLES
  private static WPI_TalonSRX pigeonTalon = new WPI_TalonSRX(3);
  private static PigeonIMU pigeon = new PigeonIMU(pigeonTalon);
  

  //// HELPER FUNCTIONS
  
  //Shuffleboard Helpers
  public static void createWidget(String tabName, String widgetName, String widgetType, double defaultValue){
    Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue)
      .withWidget(widgetType);
  }

  private static SimpleWidget getWidget(String tabName, String widgetName){
    int indexOfWidget = Shuffleboard.getTab(tabName)
      .getComponents()
      .size();
    for(int i = 0; i <= indexOfWidget; i++){
      if(Shuffleboard.getTab(tabName).getComponents().get(i).getTitle() == widgetName){ 
        return (SimpleWidget) Shuffleboard.getTab(tabName).getComponents().get(i);
      }
    }
    throw new IllegalStateException();
  }

  public static double d_getWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    return widget.getEntry().getDouble(69);
  }

  public static boolean b_getWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    return widget.getEntry().getBoolean(false);
  }

  public static void d_setWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    widget.getEntry().setDouble(69);
  }

  public static void b_setWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    widget.getEntry().setBoolean(false);
  }

  public static double getPigeonYaw(){
    double[] output = new double[3];
    pigeon.getYawPitchRoll(output);
    return output[0];
  }

  public static void resetPigeon(){
    pigeon.setYaw(0);
  }
}
