/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Helpers;

import frc.robot.subsystems.Drivetrain;
import frc.robot.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.networktables.*;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;

/**
 * Contains helpers for widgets and gyroscopes
 */

public class MainHelpers {

  public static void initializeRobot(){
    Drivetrain.backLeftTalonSRX.configFactoryDefault();
    Drivetrain.backRightTalonSRX.configFactoryDefault();
    Drivetrain.frontLeftTalonSRX.configFactoryDefault();
    Drivetrain.frontRightTalonSRX.configFactoryDefault();
    Drivetrain.pigeon.configFactoryDefault();

    Drivetrain.frontLeftTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backLeftTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.backRightTalonSRX.setNeutralMode(NeutralMode.Brake);
    Drivetrain.frontRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.setInverted(true);
    Drivetrain.backRightTalonSRX.set(ControlMode.Follower, RobotMap.FR);
    Drivetrain.backLeftTalonSRX.set(ControlMode.Follower, RobotMap.FL);
    Drivetrain.frontRightTalonSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    Drivetrain.frontLeftTalonSRX.configRemoteFeedbackFilter(Drivetrain.pigeon.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
  }

  //// HELPER VARIABLES

  //// HELPER FUNCTIONS

  // Pigeon Helpers-----------------------------------------------------------------------------------------
  

  // Limelight Helpers------------------------------------------------------------------------------------ 
}