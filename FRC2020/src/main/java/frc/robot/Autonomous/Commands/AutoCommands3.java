package frc.robot.Autonomous.Commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Cartridge.FullSensor;
import frc.robot.Turret.Targeting;
import frc.robot.Turret.TurretMotion;
import frc.robot.Turret.TurretSettings;

/**
 * AutoCommands3
 */
public class AutoCommands3 {
    public static void endAuto(){
        Turret.stop();
        stop();
        Piston.off();
        Loading.stop();
    }
    public static void goDistance(double feet){
        Distance.setEncoders(0);
        double distanceTravelled = Distance.distanceTravelled();
        while(distanceTravelled<feet){
            distanceTravelled = Distance.distanceTravelled();
            Distance.drive(0.1);
        }
        stop();
    }
    public static void turnToAngle(double angle){
        angle = 0-angle;
        double[] ypr = new double[3];
        Map.driveTrain.gyro.getYawPitchRoll(ypr);
        double currentAngle = ypr[0];
        while(!Gyro.isAtAngle(currentAngle,angle)){
            Map.driveTrain.gyro.getYawPitchRoll(ypr);
            currentAngle = ypr[0];
            SmartDashboard.putNumber("Angle", currentAngle);
            if(Math.abs(currentAngle-angle)>Gyro.slopePoint){
                if(currentAngle>angle){
                   turn(-Gyro.maxSpeed);
                }else{
                    turn(Gyro.maxSpeed);
                }
            }else{
                turn(-((currentAngle-angle)/(Gyro.slopePoint+angle)));
            }
        }
        stop();
    }
    private static void turn(double pwr){
        Map.driveTrain.lf.set(pwr);
        Map.driveTrain.rf.set(-pwr);
        Map.driveTrain.lr.set(pwr);
        Map.driveTrain.rr.set(-pwr);
    }
    public static class Turret{
        public static boolean hasLaunched = false;
        public static void goTo(double angle){
            TurretMotion.Rotation.goToPosition(angle);
        }
        public static void init(){
            Targeting.initilize();
        }
        public static void stop(){
            Targeting.stop();
        }
        public static void launch(int amount){
            Thread thread = new Thread(){
                public void run(){
                    hasLaunched = false;
                    for(int i=0;i<amount;i++){
                        while(!Targeting.readyToFire()){
                            //do nothing
                        }
                        loadBall();
                    }
                    hasLaunched = true;
                }
            };
            thread.start();
        }
        public static void loadBall(){
            double shutoffVelocity = TurretMotion.Launcher.getVelocity() - TurretSettings.launching.automatic.launchDrop;
            while(TurretMotion.Launcher.getVelocity()>shutoffVelocity){
                runConvayers();
            }
            stopConvayers();
        }
        private static void runConvayers(){
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.5);
            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 1);
            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 1);
        }
        private static void stopConvayers(){
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
        }
    }
    public static class Piston{
        private static boolean piston = false;
        public static void on(){
            Thread thread = new Thread(){
                public void run(){
                    piston = true;
                    while(piston){
                        Map.Cartridge.RightPiston.set(true);
                    }
                    Map.Cartridge.RightPiston.set(false);
                }
            };
            thread.start();
        }
        public static void off(){
            piston = false;
        }
    }
    public static class Loading{
        private static boolean load = false;
        public static void load(){
            Thread thread = new Thread(){
                public void run(){
                    while(load){
                        if (FullSensor.getSensorValue()) {
                            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.3); // adjust speeds
                            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0.1);
                            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0.1);
                        } else {
                            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
                            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
                            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
                        }
                    }
                    Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 0);
                    Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
                    Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
                    Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
                }
            };
            thread.start();
        }
        public static void stop(){
            load=false;
        }
    }
    private static class Distance{
        private static void drive(double pwr){
            Map.driveTrain.lf.set(-pwr);
            Map.driveTrain.rf.set(-pwr);
            Map.driveTrain.lr.set(-pwr);
            Map.driveTrain.rr.set(-pwr);
        }
        private static double ticksToFeet(double ticks){
            double ticksToFeet = (6 * Math.PI) / 84;
            return ticksToFeet*ticks;
        }
        private static double distanceTravelled() {
            double average = ticksToFeet((Map.driveTrain.lfEncoder.getPosition()+Map.driveTrain.rfEncoder.getPosition()+Map.driveTrain.lrEncoder.getPosition()+Map.driveTrain.rrEncoder.getPosition())/4);
            return -average;
        }
        private static void setEncoders(double set){
            Map.driveTrain.lfEncoder.setPosition(set);
            Map.driveTrain.rfEncoder.setPosition(set);
            Map.driveTrain.lrEncoder.setPosition(set);
            Map.driveTrain.rrEncoder.setPosition(set);
        }
    }
    private static class Gyro{
        private static double slopePoint = 90; //degrees
        private static double maxSpeed = 1; // 0-1.00 percent
        private static double error = 3; //angle error
        private static boolean isAtAngle(double currentAngle, double targetAngle){
            double absError = Math.abs(currentAngle-targetAngle);
            if(absError>error){
                return false;
            }else{
                return true;
            }
        }
    }
    private static void stop(){
        Map.driveTrain.lf.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rr.set(0);
    }
}