package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Map;

/**
 * TurretMotion
 */
public class TurretMotion {
    private static double maxVelocity = 100000;
    public static void init() {
        Map.Turret.motors.follower.follow(Map.Turret.motors.launcher);
        Map.Turret.motors.rotation.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.Turret.motors.launcher.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
        Map.Turret.motors.launcher.setNeutralMode(NeutralMode.Coast);
        Map.Turret.motors.rotation.setInverted(true);
        Map.Turret.motors.follower.setInverted(true);
        TurretMotion.Rotation.setPosition(0);
    }

    public static class Launcher {
        public static void setPercentSpeed(double speed) {
            Map.Turret.motors.launcher.set(ControlMode.PercentOutput, speed);
        }

        public static void setVelocity(double speed) {
            //Map.Turret.motors.launcher.set(ControlMode.Velocity, speed);
            double s=speed/maxVelocity;
            Map.Turret.motors.launcher.set(ControlMode.PercentOutput, s);
        }

        public static double getVelocity() {
            return Map.Turret.motors.launcher.getSelectedSensorVelocity();
        }
    }

    public static class Rotation {

        private static int ticksInrevolution = 4120; // needs to be changed to actual number
        private static int maxDeg = 180;
        private static int minDeg = 0;

        private static double errorRange = 1; // in degrees
        public static boolean goTo = false;
        private static double slopePoint = 5;
        private static double topSpeed = 0.5;

        public static void setPosition(int pos) {
            // Used to reset the known position of the turret
            Map.Turret.motors.rotation.setSelectedSensorPosition(pos);
        }

        public static void goToPosition(double pos) {
            // Turns turret to specific position
            goTo = true;
            Thread thread = new Thread(){
                public void run(){
                    double abserror = Math.abs(getDegrees()-pos);
                    double error = getDegrees()-pos;
                    while(goTo&&abserror>errorRange){
                        abserror = Math.abs(pos-getDegrees());
                        error = pos-getDegrees();
                        if(abserror>slopePoint){
                            if(error>0){
                                turn(topSpeed);
                            }else{
                                turn(-topSpeed);
                            }
                        }else{
                            turn((error/slopePoint)*topSpeed);
                        }
                    }
                    turn(0);
                    goTo = false;
                }
            };
            thread.start();
            //Map.Turret.motors.rotation.set(ControlMode.Position, degreesToTicks(pos));
        }

        public static double getPosition() {
            // Gets current position in motor ticks
            return -Map.Turret.motors.rotation.getSelectedSensorPosition();
        }

        public static double getDegrees() {
            // Gets current position in Degrees
            return (getPosition() / ticksInrevolution) * 360;
        }
        public static void overrideTurn(double pwr){
            Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
        }
        public static void turn(double pwr) {
            // Turns turret using pwr as input (-1 to 1)
            // Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
            if (pwr > 0 && getDegrees() <= maxDeg) {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
            } else if (pwr < 0 && getDegrees() > minDeg) {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
            } else {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, 0);
            }

        }
    }
}