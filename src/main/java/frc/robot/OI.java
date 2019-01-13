/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// SHUFFLEBOARD FUNCTIONS
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


  
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
