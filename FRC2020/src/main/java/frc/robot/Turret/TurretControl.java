package frc.robot.Turret;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.Map;

/**
 * TurretControl
 */
public class TurretControl {
    private static XboxController xbox = Map.Controllers.xbox;
    private static boolean turretInitiated = false;
    private static boolean warningIsRunning = false;
    public static boolean manuelMode = false;

    public static void run() {
        if(!manuelMode){
            if (Targeting.readyToFire()) {
                xbox.setRumble(RumbleType.kLeftRumble, 1);
                xbox.setRumble(RumbleType.kRightRumble, 1);
            }
            if (xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if (!turretInitiated) {
                    turretInitiated = true;
                    Targeting.initilize();
                }else {
                    Targeting.stop();
                    turretInitiated = false;
                }
            }
            if(xbox.getRawButton(Map.Turret.controllers.launch)) {
                if(Targeting.readyToFire()) {
                    Targeting.launch();
                }else {
                    warningVibration();
                }
            }
        }else{
            //Targeting.
        }
    }
    private static Thread vibe = new Thread() {
        public void run() {
                warningIsRunning = true;
                for (int i = 0; i < 5; i++) {
                    xbox.setRumble(RumbleType.kLeftRumble, 0.5);
                    xbox.setRumble(RumbleType.kRightRumble, 0.5);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                warningIsRunning = false;
            }
        }
    };
    private static void warningVibration() {
        if(!warningIsRunning){
            vibe.start();
        }
    }
}