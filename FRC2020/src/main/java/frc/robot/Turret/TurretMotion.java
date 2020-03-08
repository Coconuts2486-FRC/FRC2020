package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Map;
import frc.robot.Vision.LimeLight;

/**
 * TurretMotion
 */
public class TurretMotion {
    public static void init() {
        Map.Turret.motors.follower.follow(Map.Turret.motors.launcher);
        Map.Turret.motors.rotation.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.Turret.motors.launcher.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Coast);
        Map.Turret.motors.launcher.setNeutralMode(NeutralMode.Coast);
        Map.Turret.motors.rotation.setInverted(true);
        Map.Turret.motors.follower.setInverted(true);
        LimeLight.LED.off();
        TurretMotion.Rotation.setPosition(0);

        Map.Turret.motors.launcher.config_kF(0, 0.01);
		Map.Turret.motors.launcher.config_kP(0, 0.1);
    
    }

    public static class Launcher {
        public static void setPercentSpeed(double speed) {
            Map.Turret.motors.launcher.set(ControlMode.PercentOutput, speed);
        }
        /*
        public static void testsetVelocity(double velocity){
            TurretSettings.launching.automatic.velocity = velocity;
            Thread thread = new Thread(){
                public void run(){
                    double target = TurretSettings.launching.automatic.velocity/TurretSettings.launching.general.maxVelocity;
                    double set = (target/TurretSettings.launching.general.maxVelocity)+0.3;
                    double current = getVelocity()/TurretSettings.launching.general.maxVelocity;
                    while(TurretSettings.launching.automatic.velocityIsRunning&&TurretSettings.launching.automatic.velocity>0){
                        target = TurretSettings.launching.automatic.velocity/TurretSettings.launching.general.maxVelocity;
                        current = getVelocity()/TurretSettings.launching.general.maxVelocity;
                        if(current<target&&set<=(1-TurretSettings.launching.automatic.adjustment)){
                            set+=TurretSettings.launching.automatic.adjustment;
                        }else if(current>target&&set>=(0+TurretSettings.launching.automatic.adjustment)){
                            set-=TurretSettings.launching.automatic.adjustment;
                        }
                        //TurretSettings.launching.automatic.adjustment = TurretSettings.launching.automatic.adjustment/2;
                        setPercentSpeed(set);
                    }
                    setPercentSpeed(0);
                    TurretSettings.launching.automatic.velocityIsRunning = false;
                }
            };
            if(!TurretSettings.launching.automatic.velocityIsRunning){
                TurretSettings.launching.automatic.velocityIsRunning = true;
                thread.start();
            }
        }
        */
        public static void setVelocity(double speed) {
            Map.Turret.motors.launcher.set(ControlMode.Velocity, speed);
        }

        public static double getVelocity() {
            return Map.Turret.motors.launcher.getSelectedSensorVelocity();
        }
    }

    public static class Rotation {
        public static void setPosition(int pos) {
            // Used to reset the known position of the turret
            Map.Turret.motors.rotation.setSelectedSensorPosition(pos);
        }

        public static void goToPosition(double pos) {
            // Turns turret to specific position
            TurretSettings.rotation.manual.manualGoTo = true;
            Thread thread = new Thread(){
                public void run(){
                    double abserror = Math.abs(getDegrees()-pos);
                    double error = getDegrees()-pos;
                    int errorCount = 100;
                    int currentErrorCount = 0;
                    while(TurretSettings.rotation.manual.manualGoTo&&currentErrorCount<errorCount){
                        abserror = Math.abs(pos-getDegrees());
                        error = pos-getDegrees();
                        if(abserror>TurretSettings.rotation.manual.slopePoint){
                            if(error>0){
                                turn(TurretSettings.rotation.manual.topSpeed);
                            }else{
                                turn(-TurretSettings.rotation.manual.topSpeed);
                            }
                        }else{
                            if(abserror<TurretSettings.rotation.manual.errorRange){
                                currentErrorCount++;
                            }
                            turn((error/TurretSettings.rotation.manual.slopePoint)*TurretSettings.rotation.manual.topSpeed);
                        }
                    }
                    turn(0);
                    TurretSettings.rotation.manual.manualGoTo = false;
                }
            };
            thread.start();
        }

        public static double getPosition() {
            // Gets current position in motor ticks
            return -Map.Turret.motors.rotation.getSelectedSensorPosition();
        }

        public static double getDegrees() {
            // Gets current position in Degrees
            return (getPosition() / TurretSettings.rotation.general.ticksInrevolution) * 360;
        }
        public static void overrideTurn(double pwr){
            Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
        }
        public static void turn(double pwr) {
            // Turns turret using pwr as input (-1 to 1)
            if (pwr > 0 && getDegrees() <= TurretSettings.rotation.general.maxDeg) {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
            } else if (pwr < 0 && getDegrees() > TurretSettings.rotation.general.minDeg) {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, pwr);
            } else {
                Map.Turret.motors.rotation.set(ControlMode.PercentOutput, 0);
            }

        }
    }
}