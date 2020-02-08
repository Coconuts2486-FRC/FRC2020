package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Map;

/**
 * TurretMotion
 */
public class TurretMotion {
    public static class Launcher{
        public static void init(){
            Map.Turret.follower.follow(Map.Turret.launcher);
        }
        public static void setPercentSpeed(double speed){
            Map.Turret.launcher.set(ControlMode.PercentOutput, speed);
        }
        public static void setVelocity(double speed){
            Map.Turret.launcher.set(ControlMode.Velocity, speed);
        }
        public static double getVelocity(){
            return 0;
        }
    }
    public static class Rotation{
        public static void goToPosition(double pos){
            // Turns turret to specific position 
        }
        public static double getPosition(){
            // Gets current position in motor ticks
            return 0;
        }
        public static double getDegrees(){
            // Gets current position in Degrees
            return 0;
        }
        public static void turn(double pwr){
            // Turns turret using pwr as input (-1 to 1)
        }
    }
}