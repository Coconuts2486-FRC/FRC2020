package frc.robot.Autonomous;

import frc.robot.Map;

public class AutoMissions {

    public static String CurrentAuto = "None Selected";
    public static double SelectedAuto = 0;
    public static boolean TrenchAuto = false;
    public static boolean GeneratorAuto = false; 
    // SmartDashboard.putString("Auto Mode", AutoMissions.CurrentAuto);

    public static void AutoInit(){
        PID.drivePID.lastError = 0;
        PID.drivePID.errorSum = 0;
        // double lastTimestamp = Timer.getFPGATimestamp();
        Map.driveTrain.lfEncoder.setPosition(0);
        Map.driveTrain.lrEncoder.setPosition(0);
        Map.driveTrain.rfEncoder.setPosition(0);
        Map.driveTrain.rrEncoder.setPosition(0);
        Map.driveTrain.gyro.setYaw(0);

    }

    public static void NoAutoSelected() {
        CurrentAuto = "None Selected";
        Map.driveTrain.lf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.rr.set(0);
    }

    public static void TrenchRun() {
        double LFEposition = Map.driveTrain.lfEncoder.getPosition() * PID.ticksToFeet;
        double LREposition = Map.driveTrain.lrEncoder.getPosition() * PID.ticksToFeet;
        double RFEposition = Map.driveTrain.rfEncoder.getPosition() * PID.ticksToFeet;
        double RREposition = Map.driveTrain.rrEncoder.getPosition() * PID.ticksToFeet;

        double averagePosition = Math.abs((LFEposition + LREposition + RFEposition + RREposition) / 4);//theoretically might work

        Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);
        //Map.Cartridge.RightPiston.set(true);
        //AutoCommands2.launch(3);

        
        while(averagePosition < 10){
            averagePosition = Math.abs((LFEposition + LREposition + RFEposition + RREposition) / 4);
            AutoCommands2.driveForward(10);
        }

        while(PID.turnPID.ypr_deg[0] > -90){
            Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);
            AutoCommands2.turnLeft(-90);
        }
        /*while(!AutoCommands2.launched){
            
        }*/
        

    }

    public static void GeneratorRun() {
        CurrentAuto = "GeneratorAuto";

    }
}