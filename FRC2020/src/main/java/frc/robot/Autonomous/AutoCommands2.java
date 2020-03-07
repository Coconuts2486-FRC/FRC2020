package frc.robot.Autonomous;

import frc.robot.Map;
import frc.robot.Turret.Targeting;

public class AutoCommands2 {

    public static void driveForward(double distanceSetpoint){
        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = Math.abs((LFEposition + LREposition + RFEposition + RREposition) / 4);//theoretically might work

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = PID.turnPID.ypr_deg[0]/90;

        if (averagePosition < distanceSetpoint){

            Map.driveTrain.lf.set(-.3 - error);
            Map.driveTrain.lr.set(-.3 - error);
            Map.driveTrain.rf.set(-.3 + error);
            Map.driveTrain.rr.set(-.3 + error);
        }else{
            Map.driveTrain.lf.set(0);
            Map.driveTrain.lr.set(0);
            Map.driveTrain.rf.set(0);
            Map.driveTrain.rr.set(0);

        }

    }

    public static void driveBackward(double distanceSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = PID.turnPID.ypr_deg[0]/90;

        if (averagePosition < distanceSetpoint){

            Map.driveTrain.lf.set(.3 - error);
            Map.driveTrain.lr.set(.3 - error);
            Map.driveTrain.rf.set(.3 + error);
            Map.driveTrain.rr.set(.3 + error);
        }else{
            Map.driveTrain.lf.set(0);
            Map.driveTrain.lr.set(0);
            Map.driveTrain.rf.set(0);
            Map.driveTrain.rr.set(0);

        }


    }

    public static void turnLeft(double degreeSetpoint){

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = degreeSetpoint - PID.turnPID.ypr_deg[0];

        double outputSpeed = PID.turnPID.kP * error;

        Map.driveTrain.lf.set(-outputSpeed);
        Map.driveTrain.lr.set(-outputSpeed);
        Map.driveTrain.rf.set(outputSpeed);
        Map.driveTrain.rr.set(outputSpeed);

        


    }

    public static void turnRight(double degreeSetpoint){

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = degreeSetpoint - PID.turnPID.ypr_deg[0];

        double outputSpeed = PID.turnPID.kP * error;

        Map.driveTrain.lf.set(outputSpeed);
        Map.driveTrain.lr.set(outputSpeed);
        Map.driveTrain.rf.set(-outputSpeed);
        Map.driveTrain.rr.set(-outputSpeed);
    }

    public static void strafeLeft(double distanceSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = Math.abs((LFEposition + LREposition + RFEposition + RREposition) / 4);//theoretically might work

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = PID.turnPID.ypr_deg[0]/90;

        if (averagePosition < distanceSetpoint){

            Map.driveTrain.lf.set(.3 - error);
            Map.driveTrain.lr.set(-.3 - error);
            Map.driveTrain.rf.set(-.3 + error);
            Map.driveTrain.rr.set(.3 + error);
        }else{
            stop();
        }

    }

    public static void strafeRight(double distanceSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = Math.abs((LFEposition + LREposition + RFEposition + RREposition) / 4);//theoretically might work

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = PID.turnPID.ypr_deg[0]/90;

        if (averagePosition < distanceSetpoint){

            Map.driveTrain.lf.set(-.3 - error);
            Map.driveTrain.lr.set(.3 - error);
            Map.driveTrain.rf.set(.3 + error);
            Map.driveTrain.rr.set(-.3 + error);
        }else{
            stop();

        }


    }

    public static void stop(){

        Map.driveTrain.lf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.rr.set(0);


    }
    public static boolean launched = false;
    public static void launch(double NumberOfBalls){
        Thread thread = new Thread(){
            public void run(){
                launched = false;
                for(int i=0;i<NumberOfBalls;i++){
                    while(!Targeting.readyToFire()){
                        // do nothing
                    }
                    //launch one ball (logie will make code here) 
                }
                launched = true;
            }
        };
        thread.start();
    }
}