/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

  public static WPI_TalonSRX frontRightTalonSRX  = new WPI_TalonSRX(RobotMap.FR);
  public static WPI_TalonSRX frontLeftTalonSRX  = new WPI_TalonSRX(RobotMap.FL);
  public static WPI_TalonSRX backRightTalonSRX  = new WPI_TalonSRX(RobotMap.BR);
  public static WPI_TalonSRX backLeftTalonSRX  = new WPI_TalonSRX(RobotMap.BL);

  public static PigeonIMU pigeon = new PigeonIMU(15);
   

    public double driveSpeed(){
      double x = Robot.oi.stick.getX();
      SmartDashboard.putNumber("x", x);
      x *= 1.7;
      x = Math.pow(x, 2);
      if(!Robot.pneumatics.getGear()){
        return x;
      }
      else{
        return Math.pow(Robot.oi.stick.getX(), 2);
      }
    }
  
  

	public void initDefaultCommand() {
		// setDefaultCommand(new MySpecialCommand());
	
	}
}
