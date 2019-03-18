/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//This is the code for switching drive trains
//TODO define constant kHLspeed

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Downavator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.OI;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Drive extends Command {
  public static double OutputOldR;
	public static double OutputOldL;
	private static double driveWeight = 0.85;
	private static double turnWeight = 0.85;
	
	public Drive() {
		super("Drive");
		requires(Robot.drivetrain);
	}
	
	//A method to limit an input double to the range -1.0 to 1.0
	public double limit(double prelimNumber){
		if(prelimNumber >= 1.0){
			return 1.0;
					
		}else if(prelimNumber <= -1.0){
			
			return -1.0;
		}else if(prelimNumber < 1.0 && prelimNumber >-1.0){
			
			return prelimNumber;
		}else{
			
			return 0;
		}
		
	}
	double GetPositionFilteredL(double RawValueReadFromHw){
		  double FilteredPosition = 0.09516*RawValueReadFromHw+0.9048*OutputOldL;
		  OutputOldL = FilteredPosition;
		  return FilteredPosition;
	} 
	double GetPositionFilteredR(double RawValueReadFromHw){
		  double FilteredPosition = 0.09516*RawValueReadFromHw+0.9048*OutputOldR;
		  OutputOldR = FilteredPosition;
		  return FilteredPosition;
	} 
	
	//get xAxis value of Xbox joystick; argument is stick side
	public double getStickHorizontal(char side){
		if(side == 'r'){
			return limit(OI.XBController.getX(Hand.kRight));
		
		}else if(side == 'l'){
			return limit(OI.XBController.getX(Hand.kLeft));
			
		}else{
			return 0;
		}
	}
	//get Trigger values; arguement is trigger side
	public double getTriggerValue(char side){
		if(side == 'r'){
			return OI.XBController.getTriggerAxis(Hand.kRight);
		
		}else if(side == 'l'){
			return OI.XBController.getTriggerAxis(Hand.kLeft);
			
		}else{
			return 0;
			
		}
	}
	//Calculates right speed based on controller output
		public double XBControllerR(double lStick, double rTrigger, double lTrigger) {
			//speed of left side = amount Accelerator is pushed down minus
			//amount Deccelerator is pushed down - lateral input from left Joystick
			if(rTrigger >= lTrigger){
				return driveWeight * limit(rTrigger - lTrigger - lStick);
			}
			return driveWeight * limit(rTrigger - lTrigger + lStick);
		}
		
		//Calculates left speed based on Controller output
		public double XBControllerL(double lStick, double rTrigger, double lTrigger){
			//speed of left side = amount Accelerator is pushed down minus
			//amount Deccelerator is pushed down + lateral input from left Joystick
			if(rTrigger >= lTrigger){
				return driveWeight * limit(rTrigger - lTrigger + lStick);
			}
			return driveWeight * limit(rTrigger - lTrigger - lStick);
		
		}
		//Sets the speed for both sides using XBController methods
		public void setSpeeds(double lStick, double rTrigger, double lTrigger){
			
			if (Math.abs(OutputOldR - XBControllerR(lStick, rTrigger, lTrigger)) > .2) 
				Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, GetPositionFilteredR((double)XBControllerR(lStick, rTrigger, lTrigger)));
			else {
				Drivetrain.frontRightTSRX.set(ControlMode.PercentOutput, (double)XBControllerR(lStick, rTrigger, lTrigger));
			}
				if (Math.abs(OutputOldL - XBControllerL(lStick, rTrigger, lTrigger)) > .2) {
				Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, GetPositionFilteredL((double)XBControllerL(lStick, rTrigger, lTrigger)));
			}
			else {
				Drivetrain.frontLeftTSRX.set(ControlMode.PercentOutput, (double)XBControllerL(lStick, rTrigger, lTrigger));
			}		
		}
	
	protected void initialize() {
		Drivetrain.frontRightTSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
	}
	//Whenever this command is called, setspeeds is called
	protected void execute() {
		if(OI.xblb.get()){
			driveWeight = 0.25 * (OI.stick.getThrottle() + 1);
			turnWeight = 0.5;
		} else {
			driveWeight = 0.85;
			turnWeight = 0.85;
		}
		setSpeeds(getStickHorizontal('l'), getTriggerValue('r'), getTriggerValue('l'));
		//Drivetrain.frontLeftTSRX.set(.1);
		//Drivetrain.frontRightTSRX.set(.1);
		//SmartDashboard.putNumber("Right Sensor Position", Drivetrain.FREncoder.getDistance());
		//SmartDashboard.putNumber("Right Sensor Velocity", Drivetrain.FREncoder.getRate());
	}
	protected boolean isFinished() {
		
		return true; // maybe true?
	}
	protected void end() {
		
	}
	protected void interrupted() {
		
	}
}
