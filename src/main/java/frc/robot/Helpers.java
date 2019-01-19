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
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Helpers {

  //// HELPER VARIABLES
  public static WPI_TalonSRX pigeonTalon = new WPI_TalonSRX(3);
  public static PigeonIMU pigeon = new PigeonIMU(pigeonTalon);
  

  //// HELPER FUNCTIONS
  
  //Shuffleboard Helpers
  public static SimpleWidget createWidget(String tabName, String widgetName, String widgetType, double defaultValue){
    SimpleWidget widget = Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue)
      .withWidget(widgetType);
    Shuffleboard.update();
    return widget;
  }

  public static void createComplexWidget(String tabName, String widgetName, Sendable sendable){
    Shuffleboard.getTab(tabName)
      .add(widgetName, sendable);
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

  public static NetworkTableEntry getWidgetEntry(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    return widget.getEntry();
  }

  public static double d_getWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    return widget.getEntry().getDouble(69);
  }

  public static boolean b_getWidget(String tabName, String widgetName){
    SimpleWidget widget = getWidget(tabName, widgetName);
    return widget.getEntry().getBoolean(false);
  }

  public static void d_setWidget(String tabName, String widgetName, double value){
    SimpleWidget widget = getWidget(tabName, widgetName);
    widget.getEntry().setDouble(value);
  }

  public static void b_setWidget(String tabName, String widgetName, boolean value){
    SimpleWidget widget = getWidget(tabName, widgetName);
    widget.getEntry().setBoolean(value);
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

  public static void calibratePigeon(){

  }

  public static double getPigeonRate(){
    return 0;
  }

  static pigeonWrapper pigeonWrapper = new pigeonWrapper();
  static PIDController pigeonTurnController = new PIDController(0, 0, 0, pigeonWrapper, pigeonTalon);

  

  public static void pigeonPIDWrite(){
  }

  
}

class pigeonWrapper extends GyroBase{

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
    // TODO : Fix calibratePigeon() helper
    Helpers.calibratePigeon();
  }

  @Override
  public double getRate() {
    // TODO: Fix getPigeonRate() helper
    return Helpers.getPigeonRate();
  }
}
