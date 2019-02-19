package frc.robot.helpers;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDController;

import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotMap;

public class PigeonHelpers{

    public static PigeonWrapper pigeonWrapper = new PigeonWrapper();
    public static PIDController pigeonPIDController1 = new PIDController(0, 0, 0, pigeonWrapper, Drivetrain.frontLeftTSRX);
    public static PIDController pigeonPIDController2 = new PIDController(0, 0, 0, pigeonWrapper, Drivetrain.frontRightTSRX);


    /**
   * Returns the number of degrees the gyroscope has turned in the compass plane. Numbers do not wrap around
   * below 0 or above 360, and will continue to climb or decline.
   * @return the number of degrees turned from the last reset
   */
  public static double getPigeonYaw(){
    double[] output = new double[3];
    Drivetrain.pigeon.getYawPitchRoll(output);
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
    Drivetrain.pigeon.setYaw(0);
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

    Drivetrain.frontRightTSRX.setInverted(false);
    Drivetrain.backRightTSRX.setInverted(false);

    pigeonPIDController1.setOutputRange(-.35, .35);
    pigeonPIDController2.setOutputRange(-.35, .35);
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
    
    Drivetrain.frontRightTSRX.setInverted(false);
    Drivetrain.backRightTSRX.setInverted(false);

    pigeonPIDController1.setOutputRange(-.35, .35);
    pigeonPIDController2.setOutputRange(-.35, .35);
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
    Drivetrain.frontRightTSRX.setInverted(false);
    Drivetrain.backRightTSRX.setInverted(false);

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
        return PigeonHelpers.getPigeonCompassHeading();
    }
  
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        pidSourceType = pidSource;
    }
  
    //Gyro Interface
    @Override
    public double getAngle() {
      return PigeonHelpers.getPigeonCompassHeading();
    }
  
    @Override
    public void reset() {
      PigeonHelpers.resetPigeon();
    }
  
    @Override
    public void calibrate() {
      PigeonHelpers.calibratePigeon();
    }
  
    @Override
    public double getRate() {
      
      return PigeonHelpers.getPigeonRate();
    }
    
  }