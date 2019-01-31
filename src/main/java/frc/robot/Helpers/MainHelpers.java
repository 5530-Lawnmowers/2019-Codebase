/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Helpers;

import frc.robot.subsystems.Drivetrain;
import frc.robot.*;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.networktables.*;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;

/**
 * Contains helpers for widgets and gyroscopes
 */

public class MainHelpers {

  public static void initializeRobot(){
    Drivetrain.frontRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.set(ControlMode.Follower, RobotMap.FR);
    Drivetrain.backLeftTalonSRX.set(ControlMode.Follower, RobotMap.FL);
    Drivetrain.frontRightTalonSRX.configRemoteFeedbackFilter(MainHelpers.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    Drivetrain.frontLeftTalonSRX.configRemoteFeedbackFilter(MainHelpers.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
  }

  //// HELPER VARIABLES
  public static PigeonIMU pigeon = new PigeonIMU(15);
  public static PigeonWrapper pigeonWrapper = new PigeonWrapper();
  static LimelightWrapper limelightWrapper = new LimelightWrapper("tx");
  public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  public static PIDController pigeonPIDController1 = new PIDController(0, 0, 0, pigeonWrapper, Drivetrain.frontLeftTalonSRX);
  public static PIDController pigeonPIDController2 = new PIDController(0, 0, 0, pigeonWrapper, Drivetrain.frontRightTalonSRX);
  public static PIDController limelightPIDController1 = new PIDController(0, 0, 0, limelightWrapper, Drivetrain.frontRightTalonSRX);
  public static PIDController limelightPIDController2 = new PIDController(0, 0, 0, limelightWrapper, Drivetrain.frontLeftTalonSRX);


  //// HELPER FUNCTIONS
  
  //Shuffleboard Helpers---------------------------------------------------------------------------------------------
  /**
   * Creates a {@code SimpleWidget}.
   * @param tabName the name of the shuffleboard
   * @param widgetName the name of the widget
   * @param defaultValue the default value of the widget
   * @return the {@code SimpleWidget} created
   */
  public static SimpleWidget createSimpleWidget(String tabName, String widgetName, Object defaultValue){
    SimpleWidget widget = Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue);
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
    if(getPigeonYaw() < 0) {return 360 - (getPigeonYaw() % 360.0 + 360.0);}
    return 360 - (getPigeonYaw() % 360.0);

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


  public static void setupPigeon(){
    pigeonPIDController1.setInputRange(0, 360);
    pigeonPIDController2.setInputRange(0, 360);
    pigeonPIDController1.setContinuous(true);
    pigeonPIDController2.setContinuous(true);
  }

  /**
   * Sets the setpoint in degrees to turn to with PID with the gyro. PID controllers are continous.
   * @param absoluteTolerance The tolerance range to finish the PID
   * @param pidSlot The PID coefficient slot to use.
   * @param setpoint The point to be set for the PIDController to use
   */
  public static void pigeonPIDWrite(double absoluteTolerance, int pidSlot, double setpoint){
    setupPigeon();

    Drivetrain.frontRightTalonSRX.setInverted(false);
    Drivetrain.backRightTalonSRX.setInverted(false);

    pigeonPIDController1.setPID(RobotMap.pidSlots[pidSlot][0], RobotMap.pidSlots[pidSlot][1], RobotMap.pidSlots[pidSlot][2]);
    pigeonPIDController2.setPID(RobotMap.pidSlots[pidSlot][0], RobotMap.pidSlots[pidSlot][1], RobotMap.pidSlots[pidSlot][2]);
    pigeonPIDController1.setSetpoint(setpoint);
    pigeonPIDController2.setSetpoint(setpoint);
    pigeonPIDController1.setAbsoluteTolerance(absoluteTolerance);
    pigeonPIDController2.setAbsoluteTolerance(absoluteTolerance);
    pigeonPIDController1.enable();
    pigeonPIDController2.enable();

  }


  /**
   * Sets the setpoint in degrees to turn to with PID with the gyro. PID controllers are continous. 
   * Uses Shuffleboard values for PID.
   * @param absoluteTolerance The tolerance range to finish the PID
   */
  public static void pigeonPIDShuffleboard(double absoluteTolerance){
    setupPigeon();
    
    Drivetrain.frontRightTalonSRX.setInverted(false);
    Drivetrain.backRightTalonSRX.setInverted(false);

    pigeonPIDController1.setPercentTolerance(absoluteTolerance);
    pigeonPIDController2.setPercentTolerance(absoluteTolerance);
    pigeonPIDController1.enable();
    pigeonPIDController2.enable();

  }

  /**
   * Disables the pigeonPIDController
   */
  public static void disablePigeonPIDController(){
    pigeonPIDController1.disable();
    pigeonPIDController2.disable();
    Drivetrain.frontRightTalonSRX.setInverted(false);
    Drivetrain.backRightTalonSRX.setInverted(false);


  }

  // Limelight Helpers------------------------------------------------------------------------------------
  
  /**
   * Sets the setpoint to stop using PID with Limelight.
   * @param setpoint The point to be set for the PIDController to use
   */
  public static void limelightPIDWrite(double setpoint){

    limelightPIDController1.setSetpoint(setpoint);
    limelightPIDController2.setSetpoint(setpoint);
    limelightPIDController1.enable();
    limelightPIDController2.enable();
  }

  /**
   * Disable limelight PIDController
   */
  public static void disableLimelightPIDController(){
    limelightPIDController1.disable();
    limelightPIDController2.disable();
    //System.out.println("Tried to disable limelight.");
  }

  public static double getLimelightValue(String sourceValue){
    double output;
    MainHelpers.table = NetworkTableInstance.getDefault().getTable("limelight");
    if (sourceValue == "ta" || sourceValue == "tx" || sourceValue == "tx" || sourceValue == "tx0" || sourceValue == "tv"){
      output = MainHelpers.table.getEntry(sourceValue).getDouble(0.0);
    } else {
      System.out.println("Invalid Limelight value. Setting to default of 'tx'");
      output = MainHelpers.table.getEntry("tx").getDouble(0.0);
    }

    return output;
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
      return MainHelpers.getPigeonCompassHeading();
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
      pidSourceType = pidSource;
  }

  //Gyro Interface
  @Override
  public double getAngle() {
    return MainHelpers.getPigeonCompassHeading();
  }

  @Override
  public void reset() {
    MainHelpers.resetPigeon();
  }

  @Override
  public void calibrate() {
    MainHelpers.calibratePigeon();
  }

  @Override
  public double getRate() {
    
    return MainHelpers.getPigeonRate();
  }

}

/**
 * Wraps the Limelight as a sendable for widget and PID purposes
 */
class LimelightWrapper implements PIDSource{

  PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
  String pidSourceValue;

  /**
   * Constructor. Sets the value from the limelight to use for PID.
   * @param sourceValue The value get from the limelight and use as the PIDSource value
   * <ul>
   * <li>"tx": Horizontal offset from crosshair to target(-27 degrees to 27 degrees)<br>
   * <li>"ty": Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)<br>
   * <li>"ta": Target area (0% of the image to 100% of the image)
   * </ul>
   */
  LimelightWrapper(String sourceValue){
    pidSourceValue = sourceValue;
  }

  //PIDSource Interface
  @Override
  public PIDSourceType getPIDSourceType() {
    return pidSourceType;
  }

  @Override
  public double pidGet() {
    return -MainHelpers.getLimelightValue(pidSourceValue);
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    pidSourceType = pidSource;
  }
}

class Pneumatics {

}