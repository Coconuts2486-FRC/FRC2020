package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Map;


public class PID {
    public final static double ticksToFeet = (6 * Math.PI) / 84;
    public static double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
    public static double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
    public static double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
    public static double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;
    public static class drivePID{
        
        public final static double kP = 0.1;
        public final static double kI = .1;
        public final static double kD = 0;
        public final static double iLimit = 5;
        public static double errorSum = 0;
        public static double lastError = 0;
             
    }

    public static class turnPID{
        
        final static double kP = 0.05;
        final static double kI = 0;
        final static double kD = 0;
        final static double iLimit = 10;
        static double errorSum = 0;
        static double lastTimestamp = 0;
        static double lastError = 0;
        static double dt = Timer.getFPGATimestamp() - lastTimestamp;
        public static double [] ypr_deg = new double [3];
        


    }

    public static class strafePID{
        final static double kP = 0;
        final static double kI = 0;
        final static double kD = 0;
        final static double iLimit = 1;
        static double errorSum = 0;
        static double lastTimestamp = 0;
        static double lastError = 0;
        static double dt = Timer.getFPGATimestamp() - lastTimestamp; 
        
    } 
 
}