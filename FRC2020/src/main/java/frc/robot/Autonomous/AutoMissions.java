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
        CurrentAuto = "TrenchRun";
        Map.Cartridge.RightPiston.set(true);
        AutoCommands2.launch(3);
        AutoCommands2.driveForward(-10);
        while(!AutoCommands2.launched){
            
        }
        

    }

    public static void GeneratorRun() {
        CurrentAuto = "GeneratorAuto";

    }
}