package frc.robot.helpers;

import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;


public class LimelightHelpers{
    public static boolean limelightDisabled = false;
    public static NetworkTable table;
    static LimelightWrapper limelightWrapper = new LimelightWrapper("tx");
    public static PIDController limelightPIDController1 = new PIDController(0, 0, 0, limelightWrapper, Drivetrain.frontRightTSRX);
    public static PIDController limelightPIDController2 = new PIDController(0, 0, 0, limelightWrapper, Drivetrain.frontLeftTSRX);
  
    /**
     * Sets the setpoint to stop using PID with Limelight.
     * @param setpoint The point to be set for the PIDController to use
     */
    public static void limelightPIDWrite(double setpoint){
        if(limelightDisabled){
          System.out.println("limelightPIDWrite() failed: Limelight Disabled");
        } else {
          limelightPIDController1.setSetpoint(setpoint);
          limelightPIDController2.setSetpoint(setpoint);
          LimelightHelpers.limelightPIDController1.setPercentTolerance(10);
          LimelightHelpers.limelightPIDController2.setPercentTolerance(10);
          limelightPIDController1.enable();
          limelightPIDController2.enable();
        }


    }

    /**
     * Disable limelight PIDController
     */
    public static void disableLimelightPIDController(){
      if(limelightDisabled){
        System.out.println("disableLimelight() failed: Limelight Disabled");
      } else {
        limelightPIDController1.disable();
        limelightPIDController2.disable();
      }
        //System.out.println("Tried to disable limelight.");
    }

    public static double getLimelightValue(String sourceValue){
      if(limelightDisabled){
        System.out.println("limelightPIDWrite() failed: Limelight Disabled");
        return 0;
      } else {
        double output;
        table = NetworkTableInstance.getDefault().getTable("limelight");
        if (sourceValue == "ta" || sourceValue == "tx" || sourceValue == "tx" || sourceValue == "tx0" || sourceValue == "tv"){
        output = table.getEntry(sourceValue).getDouble(0.0);
        } else {
        System.out.println("Invalid Limelight value. Setting to default of 'tx'");
        output = table.getEntry("tx").getDouble(0.0);
        }
        return output;
      }

    }
  
}



/**
 * Wraps the Limelight as a sendable for widget and PID purposes
 */
class LimelightWrapper implements PIDSource{

  PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
  String pidSourceValue;
  double output;

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
    try {
      LimelightHelpers.table = NetworkTableInstance.getDefault().getTable("limelight");
    } catch (Exception e) {
      System.out.println(e);
    }
    output = 0;
    pidSourceValue = sourceValue;
    
  }

  //PIDSource Interface
  @Override
  public PIDSourceType getPIDSourceType() {
    return pidSourceType;
  }

  @Override
  public double pidGet() {
    if(LimelightHelpers.table.getEntry("tv").getDouble(0) == 1){
      output = LimelightHelpers.getLimelightValue(pidSourceValue);
    }
    return output;
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    pidSourceType = pidSource;
  }
}
