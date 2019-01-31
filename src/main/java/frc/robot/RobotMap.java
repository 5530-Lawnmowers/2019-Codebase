/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static int FR = 1; 
	public static int FL = 2; 
	public static int BR = 3;
	public static int BL = 4;

	// PID Slots
	private static double[] pidSlot1 = {0.004, 1e-20, 0};
	private static double[] pidSlot2 = {0, 0, 0};
	private static double[] pidSlot3 = {0, 0, 0};
	public static double[][] pidSlots = {pidSlot1, pidSlot2, pidSlot3};

}
