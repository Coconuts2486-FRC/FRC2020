package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Map;

public class AutoCommands{

    public static void driveForward(double distanceSetpoint){
        
        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;
    

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = distanceSetpoint - averagePosition;

        if (Math.abs(error) < PID.drivePID.iLimit){
            PID.drivePID.errorSum += error * PID.drivePID.dt;
        }
        
        double errorRate = (error - PID.drivePID.lastError) / PID.drivePID.dt;
    
        double outputSpeed = PID.drivePID.kP * error + PID.drivePID.kI * PID.drivePID.errorSum + PID.drivePID.kD * errorRate;

        Map.driveTrain.lf.set(outputSpeed);
        Map.driveTrain.lr.set(outputSpeed);
        Map.driveTrain.rf.set(outputSpeed);
        Map.driveTrain.rr.set(outputSpeed);

       //update last variables
       PID.drivePID.lastTimestamp = Timer.getFPGATimestamp();
       PID.drivePID.lastError = error;

    }

    public static void driveBackward(double distanceSetpoint){

        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = (LFEposition + LREposition + RFEposition + RREposition) / 4;//theoretically might work


        double error = distanceSetpoint - averagePosition;

        if (Math.abs(error) < PID.drivePID.iLimit){
            PID.drivePID.errorSum += error * PID.drivePID.dt;
        }
        
        double errorRate = (error - PID.drivePID.lastError) / PID.drivePID.dt;
    
        double outputSpeed = PID.drivePID.kP * error + PID.drivePID.kI * PID.drivePID.errorSum + PID.drivePID.kD * errorRate;

        Map.driveTrain.lf.set(-outputSpeed);
        Map.driveTrain.lr.set(-outputSpeed);
        Map.driveTrain.rf.set(-outputSpeed);
        Map.driveTrain.rr.set(-outputSpeed);

        //update last variables
        PID.drivePID.lastTimestamp = Timer.getFPGATimestamp();
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
}