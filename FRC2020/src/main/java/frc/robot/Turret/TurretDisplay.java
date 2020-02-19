package frc.robot.Turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Vision.LimeLight;

/**
 * TurretDisplay
 */
public class TurretDisplay {
    public static void display(){
        SmartDashboard.putNumber("LimeLight X: ", LimeLight.getX());
        SmartDashboard.putNumber("LimeLight Y: ", LimeLight.getY());
        SmartDashboard.putBoolean("LimeLight Target:", LimeLight.isTarget());
        SmartDashboard.putNumber("Lime Val", LimeLight.table.getEntry("tv").getDouble(-5));
        SmartDashboard.putNumber("Position!!! ", TurretMotion.Rotation.getDegrees());
    }
}