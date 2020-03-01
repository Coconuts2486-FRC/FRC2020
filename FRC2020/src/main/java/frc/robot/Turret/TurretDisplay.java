package frc.robot.Turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Vision.LimeLight;

/**
 * TurretDisplay
 */
public class TurretDisplay {
    public static void display(){
        //SmartDashboard.putNumber("LimeLight X: ", LimeLight.getX());
        //SmartDashboard.putNumber("LimeLight Y: ", LimeLight.getY());
        SmartDashboard.putBoolean("LimeLight Target:", LimeLight.isTarget());
        //SmartDashboard.putNumber("Lime Val", LimeLight.table.getEntry("tv").getDouble(-5));
        SmartDashboard.putNumber("Angle: ", TurretMotion.Rotation.getDegrees());
        SmartDashboard.putBoolean("Ready to Fire!", Targeting.readyToFire());
        //SmartDashboard.putNumber("Ticks: ", TurretMotion.Rotation.getPosition());
        SmartDashboard.putNumber("Velocity: ", TurretMotion.Launcher.getVelocity());
        SmartDashboard.putNumber("Manual Velocity: ", TurretControl.manuelVelocity);
        SmartDashboard.putNumber("Error: ", Math.abs(LimeLight.getX()));
    }
    public static void learningDisplay(double setvelocity,double actualvelocity,double limelighty){
        SmartDashboard.putNumber("Learning Set Velocity: ", setvelocity);
        SmartDashboard.putNumber("Learning Actual Velocity: ", actualvelocity);
        SmartDashboard.putNumber("Learning LimeLight Y: ", limelighty);
    }
}