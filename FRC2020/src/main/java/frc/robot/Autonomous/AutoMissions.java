package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
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
        PID.drivePID.lastTimestamp = Timer.getFPGATimestamp();
        

    }

    public static void NoAutoSelected() {
        CurrentAuto = "None Selected";
        Map.driveTrain.lf.set(0);
        Map.driveTrain.lr.set(0);
        Map.driveTrain.rf.set(0);
        Map.driveTrain.rr.set(0);
    }

    public static void TrenchRun() {
        CurrentAuto = "TrenchRun";

    }

    public static void GeneratorRun() {
        CurrentAuto = "GeneratorAuto";

    }
}