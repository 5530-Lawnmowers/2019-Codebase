/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.*;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Helpers {

  //// HELPER VARIABLES
  public static WPI_TalonSRX pigeonTalon = new WPI_TalonSRX(3);
  public static PigeonIMU pigeon = new PigeonIMU(pigeonTalon);
  static PigeonWrapper pigeonWrapper = new PigeonWrapper();
  static PIDController pigeonTurnController = new PIDController(0, 0, 0, pigeonWrapper, pigeonTalon);
  
  //// HELPER FUNCTIONS
  
  //Shuffleboard Helpers
  /**
   * Creates a simple widget.
   * @param tabName the <code>String</code> name of the shuffleboard
   * @param widgetName the <code>String</code> name of the widget
   * @param widgetType the <code>String</code> type of the widget
   * @param defaultValue the <code>double</code> default value of the widget
   * 
   */
  public static SimpleWidget createSimpleWidget(String tabName, String widgetName, String widgetType, Object defaultValue){
    SimpleWidget widget = Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue)
      .withWidget(widgetType);
    Shuffleboard.update();
    return widget;
  }

  /**
   * Creates a complex widget.
   */
  public static void createComplexWidget(String tabName, String widgetName, Sendable sendable){
    Shuffleboard.getTab(tabName)
      .add(widgetName, sendable);
  }

  private static SimpleWidget getSimpleWidget(String tabName, String widgetName){
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

  public static Object getWidgetValue(String tabName, String widgetName){
    SimpleWidget widget = getSimpleWidget(tabName, widgetName);
    if(widget.getEntry().getValue().isBoolean()){
      return widget.getEntry().getBoolean(false);
    } else if(widget.getEntry().getValue().isDouble()){
      return widget.getEntry().getDouble(0);
    } else if(widget.getEntry().getValue().isString()){
      return widget.getEntry().getString("");
    } else{
      throw new ClassCastException();
    }
  }

  public static void setWidgetValue(String tabName, String widgetName, Object value){
    SimpleWidget widget = getSimpleWidget(tabName, widgetName);
    if(widget.getEntry().getValue().isBoolean()){
      try {
        widget.getEntry().setBoolean((boolean) value);
      } catch (Exception e) {
        System.out.println("Did not get expected type of Boolean: " + e);
      }
      
    } else if(widget.getEntry().getValue().isDouble()){
      try {
        widget.getEntry().setDouble(((Number) value).doubleValue());
      } catch (Exception e) {
        System.out.println("Did not get expected type of Number: " + e);
      }
    } else if(widget.getEntry().getValue().isString()){
      try {
        widget.getEntry().setString((String) value);
      } catch (Exception e) {
        System.out.println("Did not get expected type of String: " + e);
      }
    } else{
      throw new ClassCastException();
    }
  }

  // Pigeon Helpers
  public static double getPigeonYaw(){
    double[] output = new double[3];
    pigeon.getYawPitchRoll(output);
    return output[0];
  }

  public static double getPigeonCompassHeading(){
    if(getPigeonYaw() < 0) {return getPigeonYaw() % 360.0 + 360.0;}
    return getPigeonYaw() % 360.0;

  }

  public static void resetPigeon(){
    pigeon.setYaw(0);
  }

  // TODO: Fix calibratePigeon() helper
  public static void calibratePigeon(){

  }

  // TODO: Fix getPigeonRate() helper
  public static double getPigeonRate(){
    return 0;
  }

  

  public static void pigeonPIDWrite(){
  }

  
}

class PigeonWrapper extends GyroBase{

  PIDSourceType pidSourceType = PIDSourceType.kDisplacement;


  //PIDSource Interface
  @Override
  public PIDSourceType getPIDSourceType() {
      return pidSourceType;
  }

  @Override
  public double pidGet() {
      return Helpers.getPigeonYaw();
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
      pidSourceType = pidSource;
  }

  //Gyro Interface
  @Override
  public double getAngle() {
    return Helpers.getPigeonCompassHeading();
  }

  @Override
  public void reset() {
    Helpers.resetPigeon();
  }

  @Override
  public void calibrate() {
    Helpers.calibratePigeon();
  }

  @Override
  public double getRate() {
    
    return Helpers.getPigeonRate();
  }
}
