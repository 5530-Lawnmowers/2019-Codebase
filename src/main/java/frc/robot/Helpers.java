/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.networktables.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

/**
 * Contains helpers for widgets and gyroscopes
 */

public class Helpers {

  //// HELPER VARIABLES
  public static WPI_TalonSRX pigeonTalon = new WPI_TalonSRX(3);
  public static PigeonIMU pigeon = new PigeonIMU(pigeonTalon);
  static PigeonWrapper pigeonWrapper = new PigeonWrapper();
  static LimelightWrapper limelightWrapper = new LimelightWrapper("tx");
  static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  static PIDController pigeonPIDController1;
  static PIDController pigeonPIDController2;
  static PIDController pigeonPIDController3;
  static PIDController limelightPIDController;

  
  //// HELPER FUNCTIONS
  
  //Shuffleboard Helpers---------------------------------------------------------------------------------------------
  /**
   * Creates a {@code SimpleWidget}.
   * @param tabName the name of the shuffleboard
   * @param widgetName the name of the widget
   * @param widgetType the type of the widget
   * @param defaultValue the default value of the widget
   * @return the {@code SimpleWidget} created
   */
  public static SimpleWidget createSimpleWidget(String tabName, String widgetName, String widgetType, Object defaultValue){
    SimpleWidget widget = Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue)
      .withWidget(widgetType);
    Shuffleboard.update();
    return widget;
  }

  /**
   * Creates a {@code ComplexWidget}.
   * @param tabName the name of the shuffleboard
   * @param widgetName the name of the widget
   * @param sendable the object that sends data for the widget
   */
  public static void createComplexWidget(String tabName, String widgetName, Sendable sendable){
    Shuffleboard.getTab(tabName)
      .add(widgetName, sendable);
  }

  /**
   * Finds the simple widget that matches the description
   * @param tabname the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @return the {@code SimpleWidget} matching the shuffleboard and name given
   * @throws IllegalStateException if the widget could not be found
   */
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

  /**
   * Returns an {@code Object} of the value in the named widget.
   * @param tabName the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @return the value of the widget
   * @throws ClassCastException if the widget does not contain a {@code boolean}, 
   * {@code double}, or {@code String}
   */
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

  /**
   * Sets the value of a widget to the specfied value.
   * @param tabName the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @param value the new value for the widget
   * @throws ClassCastException if the type of the specified value is not the same as that of the widget,
   * or the widget does not have type {@code boolean}, {@code double}, or {@code String}.
   */
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

  // Pigeon Helpers-----------------------------------------------------------------------------------------
  /**
   * Returns the number of degrees the gyroscope has turned in the compass plane. Numbers do not wrap around
   * below 0 or above 360, and will continue to climb or decline.
   * @return the number of degrees turned from the last reset
   */
  public static double getPigeonYaw(){
    double[] output = new double[3];
    pigeon.getYawPitchRoll(output);
    return output[0];
  }

  /**
   * Returns the current heading of the gyroscope in the compass plane. Ranges from 0 to 360.
   * @return the heading of the gyroscope
   */
  public static double getPigeonCompassHeading(){
    if(getPigeonYaw() < 0) {return getPigeonYaw() % 360.0 + 360.0;}
    return getPigeonYaw() % 360.0;

  }

  /**
   * Resets the gyroscope so the current heading is 0. {@code getPigeonYaw()} is also reset to 0.
   */
  public static void resetPigeon(){
    pigeon.setYaw(0);
  }

  // TODO: Fix calibratePigeon() helper
  /**
   * Calibrates the gyroscope by taking a sequence of measurements and taking the average to be the offset
   * from 0.
   */
  public static void calibratePigeon(){

  }

  // TODO: Fix getPigeonRate() helper
  /**
   * Returns the rate at which the gyroscope is rotating in [INSERT UNITS HERE]
   * @return the angular velocity
   */
  public static double getPigeonRate(){
    return 0;
  }

  /**
   * Sets the setpoint in degrees to turn to with PID
   * @param talon The talon to control with PID
   * @param controllerID The id of the PIDController to use
   * @param setpoint The point to be set for the PIDController to use
   */
  public static void pigeonPIDWrite(WPI_TalonSRX talon, int controllerID, double setpoint){

    switch(controllerID){
      case 1:
        pigeonPIDController1 = new PIDController(0, 0, 0, pigeonWrapper, talon);
        pigeonPIDController1.setSetpoint(setpoint);
        pigeonPIDController1.enable();
      case 2:
        pigeonPIDController2 = new PIDController(0, 0, 0, pigeonWrapper, talon);
        pigeonPIDController2.setSetpoint(setpoint);
        pigeonPIDController2.enable();
      case 3:
        pigeonPIDController3 = new PIDController(0, 0, 0, pigeonWrapper, talon);
        pigeonPIDController3.setSetpoint(setpoint);
        pigeonPIDController3.enable();
      default:
       System.out.println("Controller with ID " + controllerID + " does not exist");
    }

  }

  /**
   * Disables the pigeonPIDController
   * @param controllerID The ID of the PIDController to use
   */
  public static void disablePigeonPIDController(int controllerID){
    switch(controllerID){
      case 1:
        if(pigeonPIDController1 != null){
          pigeonPIDController1.disable();
        }
      case 2:
        if(pigeonPIDController1 != null){
          pigeonPIDController1.disable();
        }
      case 3:
        if(pigeonPIDController1 != null){
          pigeonPIDController1.disable();
       }
      default:
        System.out.println("Controller with ID " + controllerID + " does not exist");
    }

  }

  // Limelight Helpers------------------------------------------------------------------------------------
  
  /**
   * 
   * @param talon The talon to control with PID
   * @param setpoint The point to be set for the PIDController to use
   */
  public static void limelightPIDWrite(WPI_TalonSRX talon, double setpoint){
    
    limelightPIDController = new PIDController(0, 0, 0, limelightWrapper, talon);

    limelightPIDController.setSetpoint(setpoint);
    limelightPIDController.enable();
  }

  /**
   * Disable limelight PIDController
   */
  public static void disableLimelightPIDController(){
    limelightPIDController.disable();
  }
  
}

/**
 * This class wraps the gyroscope as a sendable for widget purposes and PID.
 */
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


class LimelightWrapper implements PIDSource{

  PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
  double pidSourceValue;

  /**
   * 
   * @param sourceValue The value get from the limelight and use as the PIDSource value
   * <ul>
   * <li>"tx": Horizontal offset from crosshair to target(-27 degrees to 27 degrees)<br>
   * <li>"ty": Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)<br>
   * <li>"ta": Target area (0% of the image to 100% of the image)
   * </ul>
   */
  LimelightWrapper(String sourceValue){
    if(sourceValue == "ta" || sourceValue == "tx" || sourceValue == "tx"){
      pidSourceValue = Helpers.table.getEntry(sourceValue).getDouble(0.0);
    } else {
      System.out.println("Invalid Limelight value. Setting to default of 'tx'");
      pidSourceValue = Helpers.table.getEntry("tx").getDouble(0.0);
    }
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return pidSourceType;
  }

  @Override
  public double pidGet() {
    return pidSourceValue;
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    pidSourceType = pidSource;
  }
}

class Pneumatics {

}