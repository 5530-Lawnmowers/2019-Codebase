/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick.AxisType;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS-----------------------------------------------------
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  public static Joystick stick = new Joystick(0);
  public static XboxController XBController = new XboxController(1);
  JoystickButton xba = new JoystickButton(XBController, 1);
  JoystickButton xbb = new JoystickButton(XBController, 2);
  JoystickButton xbrb = new JoystickButton(XBController, 6);
  JoystickButton xby  = new JoystickButton(XBController, 4);
  JoystickButton xbx = new JoystickButton(XBController, 3);
  JoystickButton xbstart = new JoystickButton(XBController, 8);
  JoystickButton xbback = new JoystickButton(XBController, 7);
  public static JoystickButton xblb = new JoystickButton(XBController, 5);

  public static Button[] buttons = new Button[12];
  

  OI() {
    for(int i=1; i <= 12; i++) {
      buttons[i-1] = new JoystickButton(stick, i);
    }
    //Climb
    buttons[7].toggleWhenPressed(new AscendElevator("HighHatch"));
    buttons[6].toggleWhenPressed(new DropDownavator());
    //Hatch
    buttons[2].toggleWhenPressed(new AscendElevator("LowHatch"));
    buttons[4].toggleWhenPressed(new AscendElevator("Bot"));
    buttons[5].toggleWhenPressed(new AscendElevator("DeliverHatch"));
    buttons[3].toggleWhenPressed(new AscendElevator("MidHatch"));
    //Cargo
    buttons[10].toggleWhenPressed(new AscendElevator("ShuttleBall"));
    buttons[11].toggleWhenPressed(new AscendElevator("LowRocketBall"));
    buttons[8].toggleWhenPressed(new AscendElevator("MidRocketBall"));
    buttons[9].toggleWhenPressed(new AscendElevator("HighRocketBall"));
    //Interrupts
    buttons[0].toggleWhenPressed(new InterruptElevator());
    buttons[1].toggleWhenPressed(new InterruptArm());

    xba.toggleWhenPressed(new PickupBall());
    xbb.toggleWhenPressed(new DispenseBall());
    xbrb.toggleWhenPressed(new AlignHatch());
    xbx.toggleWhenPressed(new MoveArm("Bot"));
    xby.toggleWhenPressed(new MoveArm("Top"));
    xbback.toggleWhenPressed(new Level2Climb());
    xbstart.toggleWhenPressed(new LiftRobot());
    xblb.toggleWhenPressed(new AlignRocketSide());

}







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
