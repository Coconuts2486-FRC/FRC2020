package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

public class AutoCommands{
    public static double lastTimestamp = 0;

    public static void driveForward(double distanceSetpoint){
        
        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;
        double dt = Timer.getFPGATimestamp() - lastTimestamp;
    

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = distanceSetpoint - averagePosition;

        if (Math.abs(error) < PID.drivePID.iLimit){
            PID.drivePID.errorSum += error * dt;
        }
        
        double errorRate = (error - PID.drivePID.lastError) / dt;
    
        double outputSpeed = PID.drivePID.kP * error + PID.drivePID.kI * PID.drivePID.errorSum + PID.drivePID.kD * errorRate;

        Map.driveTrain.lf.set(outputSpeed);
        Map.driveTrain.lr.set(outputSpeed);
        Map.driveTrain.rf.set(outputSpeed);
        Map.driveTrain.rr.set(outputSpeed);

        SmartDashboard.putNumber("error", error);
        SmartDashboard.putNumber("dt", dt);

       //update last variables
       lastTimestamp = Timer.getFPGATimestamp();
       PID.drivePID.lastError = error;

    }

    public static void driveBackward(double distanceSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;
        double dt = Timer.getFPGATimestamp() - lastTimestamp;

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = distanceSetpoint - averagePosition;

        if (Math.abs(error) < PID.drivePID.iLimit){
            PID.drivePID.errorSum += error * dt;
        }
        
        double errorRate = (error - PID.drivePID.lastError) / dt;
    
        double outputSpeed = PID.drivePID.kP * error + PID.drivePID.kI * PID.drivePID.errorSum + PID.drivePID.kD * errorRate;

        Map.driveTrain.lf.set(-outputSpeed);
        Map.driveTrain.lr.set(-outputSpeed);
        Map.driveTrain.rf.set(-outputSpeed);
        Map.driveTrain.rr.set(-outputSpeed);

        //update last variables
        lastTimestamp = Timer.getFPGATimestamp();
        PID.drivePID.lastError = error;

    }

    public static void turnLeft(double degreeSetpoint){

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = degreeSetpoint - PID.turnPID.ypr_deg[0];

        if (Math.abs(error) < PID.turnPID.iLimit){
            PID.turnPID.errorSum += error * PID.turnPID.dt;
        }
        
        double errorRate = (error - PID.turnPID.lastError) / PID.turnPID.dt;

        double outputSpeed = PID.turnPID.kP * error + PID.turnPID.kI * PID.turnPID.errorSum + PID.turnPID.kD * errorRate;

        Map.driveTrain.lf.set(-outputSpeed);
        Map.driveTrain.lr.set(-outputSpeed);
        Map.driveTrain.rf.set(outputSpeed);
        Map.driveTrain.rr.set(outputSpeed);

        //update last variables
        PID.turnPID.lastTimestamp = Timer.getFPGATimestamp();
        PID.turnPID.lastError = error;

    }

    public static void turnRight(double degreeSetpoint){
        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = degreeSetpoint - PID.turnPID.ypr_deg[0];

        if (Math.abs(error) < PID.turnPID.iLimit){
            PID.turnPID.errorSum += error * PID.turnPID.dt;
        }
        
        double errorRate = (error - PID.turnPID.lastError) / PID.turnPID.dt;

        double outputSpeed = PID.turnPID.kP * error + PID.turnPID.kI * PID.turnPID.errorSum + PID.turnPID.kD * errorRate;

        Map.driveTrain.lf.set(outputSpeed);
        Map.driveTrain.lr.set(outputSpeed);
        Map.driveTrain.rf.set(-outputSpeed);
        Map.driveTrain.rr.set(-outputSpeed);

        //update last variables
        PID.turnPID.lastTimestamp = Timer.getFPGATimestamp();
        PID.turnPID.lastError = error;
    }

    public static void strafeLeft(double strafeSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = strafeSetpoint - averagePosition;//might not work

        if (Math.abs(error) < PID.strafePID.iLimit){
            PID.strafePID.errorSum += error * PID.strafePID.dt;
        }
        
        double errorRate = (error - PID.strafePID.lastError) / PID.strafePID.dt;
    
        double outputSpeed = PID.strafePID.kP * error + PID.strafePID.kI * PID.strafePID.errorSum + PID.strafePID.kD * errorRate;

        Map.driveTrain.lf.set(-outputSpeed);
        Map.driveTrain.lr.set(outputSpeed);
        Map.driveTrain.rf.set(outputSpeed);
        Map.driveTrain.rr.set(-outputSpeed);

        //update last variables
        PID.strafePID.lastTimestamp = Timer.getFPGATimestamp();
        PID.strafePID.lastError = error;

    }

    public static void strafeRight(double strafeSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = strafeSetpoint - averagePosition;//might not work

        if (Math.abs(error) < PID.strafePID.iLimit){
            PID.strafePID.errorSum += error * PID.strafePID.dt;
        }
        
        double errorRate = (error - PID.strafePID.lastError) / PID.strafePID.dt;
    
        double outputSpeed = PID.strafePID.kP * error + PID.strafePID.kI * PID.strafePID.errorSum + PID.strafePID.kD * errorRate;

        Map.driveTrain.lf.set(outputSpeed);
        Map.driveTrain.lr.set(-outputSpeed);
        Map.driveTrain.rf.set(-outputSpeed);
        Map.driveTrain.rr.set(outputSpeed);

        //update last variables
        PID.strafePID.lastTimestamp = Timer.getFPGATimestamp();
        PID.strafePID.lastError = error;

    }

    public static void strafeDiagnol(double strafeSetpoint, String direction){

        if (direction == "LF"){

        }

        if (direction == "RF"){

        }

        if (direction == "LB"){

        }

        if (direction == "RB"){

        }

    }

    public static void driveStraight(double degreeSetpoint){
        double pl = 0;
        double pr = 0;

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);

        double error = PID.turnPID.ypr_deg[0]/90;

        Map.driveTrain.lf.set(-.3 - error);
        Map.driveTrain.lr.set(-.3 - error);
        Map.driveTrain.rf.set(-.3 + error);
        Map.driveTrain.rr.set(-.3 + error);
        
        /*
        while (PID.turnPID.ypr_deg[0] < degreeSetpoint ){
            pl -=.1;
        }

        while (PID.turnPID.ypr_deg[0] > degreeSetpoint){
            pr -=.1;
        }
        */
        
        


    }

    public static void stop(){
        Map.driveTrain.lf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.rr.set(0);
    }
}    
