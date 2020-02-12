package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Map;

/**
 * TurretMotion
 */
public class TurretMotion {
    public static class Launcher{
        public static void init(){
            Map.Turret.motors.follower.follow(Map.Turret.motors.launcher);
        }
        public static void setPercentSpeed(double speed){
            Map.Turret.motors.launcher.set(ControlMode.PercentOutput, speed);
        }
        public static void setVelocity(double speed){
            Map.Turret.motors.launcher.set(ControlMode.Velocity, speed);
        }
        public static double getVelocity(){
            return Map.Turret.motors.launcher.getSelectedSensorVelocity();
        }
    }
    public static class Rotation{

        private static int ticksInrevolution = 5000; // needs to be changed to actual number
        
        public static void setPosition(int pos){
            // Used to reset the known position of the turret
            Map.Turret.motors.rotation.setSelectedSensorPosition(pos);
        }
        public static void goToPosition(double pos){
            // Turns turret to specific position 
            Map.Turret.motors.rotation.set(ControlMode.Position, degreesToTicks(pos));
        }
        private static int degreesToTicks(double degrees){
            return (int) ((degrees / 360) * ticksInrevolution);
        }
        public static double getPosition(){
            // Gets current position in motor ticks
            return Map.Turret.motors.rotation.getSelectedSensorPosition();
        }
        public static double getDegrees(){
            // Gets current position in Degrees
            return (getPosition()/ticksInrevolution)*360;
        }
        public static void turn(double pwr){
            // Turns turret using pwr as input (-1 to 1)
            Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
        }
    }
}