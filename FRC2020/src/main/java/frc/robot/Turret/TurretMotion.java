package frc.robot.Turret;

import frc.robot.Map;

/**
 * TurretMotion
 */
public class TurretMotion {
    public static void init(){
        Map.Turret.follower.follow(Map.Turret.launcher);
    }
    public static void setPercentSpeed(double speed){
        
    }
    public static void setVelocitySpeed(double speed){

    }
}