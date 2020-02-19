package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;

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
        private static SensorCollection sensors = new Map.Turret.motors().rotation.getSensorCollection();
        private volatile int lastValue = Integer.MIN_VALUE;
        public int getPwmPosition() {
            int raw = sensors.getPulseWidthRiseToFallUs();
            if (raw == 0) {
            int lastValue = this.lastValue;
            if (lastValue == Integer.MIN_VALUE) {
            return 0;
            }
            return lastValue;
            }
            int actualValue = Math.min(4096, raw - 128);
            lastValue = actualValue;
            return actualValue;
            }

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
            /*
            if(pwr>0){
                if(getDegrees()<180){
                    Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
                }
            }else if(pwr<0){
                if(getDegrees()>0){
                    Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
                }
                
            }
            */
        }
    }
}