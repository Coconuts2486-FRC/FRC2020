package frc.robot.Autonomous.Commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Autonomous.PID;
import frc.robot.Cartridge.FullSensor;
import frc.robot.Turret.Targeting;
import frc.robot.Turret.TurretMotion;
import frc.robot.Turret.TurretSettings;
import frc.robot.Utilities.Sleep;

/**
 * AutoCommands3
 */
public class AutoCommands3 {
    public static boolean ran = false;
    public static void endAuto(){
        ran = true;
        Turret.stop();
        stop();
        Map.Cartridge.RightPiston.set(false);
        Loading.load();
        Loading.load = false;
        //AutoCommands3.Turret.goTo(90);
    }
    public static void wait(boolean value, boolean toBe){
        while(!(value=toBe)){

        }
    }
    public static void goDistance(double feet,double speedModifier){
        Distance.setEncoders(0);
        Sleep.delay(100);
        feet = Math.abs(feet);
        double distanceTravelled = Distance.distanceTravelled();
        double driveError = feet - distanceTravelled;
        double speed = PID.drivePID.kP * driveError;
        while(distanceTravelled<feet&&(!ran)){
            distanceTravelled = Distance.distanceTravelled();
            driveError = feet - distanceTravelled;
            speed = PID.drivePID.kP * driveError;
            Distance.drive(speed * speedModifier);
        }
        stop();
    }
    public static void turnToAngle(double angle){
        angle = 0-angle;
        double[] ypr = new double[3];
        Map.driveTrain.gyro.getYawPitchRoll(ypr);
        double currentAngle = ypr[0];
        int errorCount = 100;
        int currentErrorCount = 0;
        while((currentErrorCount<errorCount)&&(!ran)){
            Map.driveTrain.gyro.getYawPitchRoll(ypr);
            currentAngle = ypr[0];
            if(Math.abs(currentAngle-angle)>Gyro.slopePoint){
                if(currentAngle>angle){
                   turn(-Gyro.maxSpeed);
                }else{
                    turn(Gyro.maxSpeed);
                }
            }else{
                if(Gyro.isAtAngle(currentAngle,angle)){
                    currentErrorCount++;
                }
                turn(-(((currentAngle-angle)/(Gyro.slopePoint+Math.abs(angle)))*Gyro.maxSpeed));
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
                        while(!Targeting.readyToFire()&&(!ran)){
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
            while(TurretMotion.Launcher.getVelocity()>shutoffVelocity&&(!ran)){
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
                    while(piston&&(!ran)){
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
                    load = true;
                    while(load&&(!ran)){
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

            Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);
            double turnError = PID.turnPID.ypr_deg[0]/90;

            Map.driveTrain.lf.set(-pwr + turnError);
            Map.driveTrain.rf.set(-pwr + turnError);
            Map.driveTrain.lr.set(-pwr - turnError);
            Map.driveTrain.rr.set(-pwr - turnError);
        }
        private static double ticksToFeet(double ticks){
            double ticksToFeet = (6 * Math.PI) / 84;
            return ticksToFeet*ticks;
        }
        private static double distanceTravelled() {
            double average = ticksToFeet((Map.driveTrain.lfEncoder.getPosition()+Map.driveTrain.rfEncoder.getPosition()+Map.driveTrain.lrEncoder.getPosition()+Map.driveTrain.rrEncoder.getPosition())/4);
            return Math.abs(average);
        }
        private static void setEncoders(double set){
            
            Map.driveTrain.lfEncoder.setPosition(0);
            Map.driveTrain.lrEncoder.setPosition(0);
            Map.driveTrain.rfEncoder.setPosition(0);
            Map.driveTrain.rrEncoder.setPosition(0);
            
        }
    }
    private static class Gyro{
        private static double slopePoint = 18; //degrees
        private static double maxSpeed = 0.5; // 0-1.00 percent
        private static double error = 0.5; //angle error
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