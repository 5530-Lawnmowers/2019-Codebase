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
	public static double kHLspeed = .75;
    public static int FR = 1; 
	public static int FL = 2; 
	public static int BR = 3;
	public static int BL = 4;

	public static int D1 = 5; // Downavator #
	public static int D2 = 6;
	public static int DD = 7;
	// Digital Input
	public static int DBS = 8; // Downavator Bottom Switch
	public static int DTS = 9; // Downavator Top Switch

	public static int E1 = 8; // Elevator #
	public static int E2 = 9;
	// Digital Input
	public static int ES1 = 6; // Elevator Switch Bot #
	public static int ES2 = 2;

	public static int I1 = 9; // Intake #
	public static int I2 = 10;
	public static int A = 11; // Arm
	public static int AP = 0; //Arm potentiameter
	// Digital Input
	public static int IS = 5; // Intake Switch 

	//IR Sensors
	public static int IRL = 4; //IR left
	public static int IRR = 5; //IR right


	// PID Slots
	private static double[] pidSlot1 = {0.004, 1e-20, 0}; //Encoder Drive PID
	private static double[] pidSlot2 = {.4, 0, 0}; //Turn PID
	private static double[] pidSlot3 = {0, 0, 0}; //Elevator PID
	public static double[][] pidSlots = {pidSlot1, pidSlot2, pidSlot3};

	/**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
	public final static int kTimeoutMs = 30;

	/**
	 * Motor neutral dead-band, set to the minimum 0.1%.
	 */
	public final static double kNeutralDeadband = 0.001;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop.
     * kF: 1023 represents output value to Talon at 100%, 6800 represents Velocity units at 100% output
     * Not all set of Gains are used in this project and may be removed as desired.
     * 
	 * 	                                    			  kP   kI   kD   kF               Iz    PeakOut */
	public final static Gains kGains_Distanc = new Gains( 0.1, 0.0,  0.0, 0.0,            100,  0.50 );
	public final static Gains kGains_Turning = new Gains( 2.0, 0.0,  4.0, 0.0,            200,  1.00 );
	public final static Gains kGains_Velocit = new Gains( 0.1, 0.0, 20.0, 1023.0/6800.0,  300,  0.50 );
	public final static Gains kGains_MotProf = new Gains( 1.0, 0.0,  0.0, 1023.0/6800.0,  400,  1.00 );

	/** ---- Flat constants, you should not need to change these ---- */
	/* We allow either a 0 or 1 when selecting an ordinal for remote devices [You can have up to 2 devices assigned remotely to a talon/victor] */
	public final static int REMOTE_0 = 0;
	public final static int REMOTE_1 = 1;
	/* We allow either a 0 or 1 when selecting a PID Index, where 0 is primary and 1 is auxiliary */
	public final static int PID_PRIMARY = 0;
	public final static int PID_TURN = 1;
	/* Firmware currently supports slots [0, 3] and can be used for either PID Set */
	public final static int SLOT_0 = 0;
	public final static int SLOT_1 = 1;
	public final static int SLOT_2 = 2;
	public final static int SLOT_3 = 3;
	/* ---- Named slots, used to clarify code ---- */
	public final static int kSlot_Distanc = SLOT_0;
	public final static int kSlot_Turning = SLOT_1;
	public final static int kSlot_Velocit = SLOT_2;
	public final static int kSlot_MotProf = SLOT_3;

}
