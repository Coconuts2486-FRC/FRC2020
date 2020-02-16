package frc.robot.Autonomous;

import frc.robot.Map;

public class AutoMissions {

    public static String CurrentAuto = "None Selected";
    public static double SelectedAuto = 0;
    //SmartDashboard.putString("Auto Mode", AutoMissions.CurrentAuto);

    public static void NoAutoSelected(){
        CurrentAuto = "None Selected";
        Map.driveTrain.lf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.rr.set(0);
    }

    public static void TrenchRun(){
        CurrentAuto = "TrenchRun";


    }

    public static void GeneratorAuto(){
        CurrentAuto = "GeneratorAuto";


    }
}