package frc.robot.Turret;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Map;
import frc.robot.Vision.LimeLight;

/**
 * TurretControl
 */
public class TurretControl {
    private static XboxController xbox = Map.Controllers.xbox;
    private static boolean turretInitiated = false;
    public static boolean manuelMode = false;
    private static boolean led = false;
    private static double manuelAngle = 90;
    public static int manuelVelocity = 10000; // set to whatever base velocity should be
    private static int manuelVelocityChange = 1000; // The amount that velocity is adjusted when POV is pressed
    private static double manuelSmallAdjusterDivision = 6; // how much the small adjuster is divided by
    private static double manuelLargeAdjusterDivision = 2; // how much the large adjuster is divided by
    public static boolean firing = false;
    public static boolean autoIsTracking = true;

    public static void run() {
        if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelMode)) {
            if (manuelMode) {
                manuelMode = false;
                led = false;
                LimeLight.LED.off();
                autoIsTracking = true;
            } else {
                manuelMode = true;
                Targeting.stop();
                autoIsTracking = false;
                turretInitiated = false;
            }
        }
        if (!manuelMode) {
            // Auto Mode
            if (xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if (!turretInitiated) {
                    turretInitiated = true;
                    Targeting.initilize();
                } else {
                    Targeting.stop();
                    turretInitiated = false;
                }
            }
            if (xbox.getRawButton(Map.Turret.controllers.launch) && Targeting.readyToFire()) {
                firing = true;
                Targeting.launch();
            } else {
                firing = false;
                Targeting.stopLaunch();
            }
        } else {
            // Manuel Mode
            // Turning
            double large = Math.abs(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge));
            double small = Math.abs(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall));
            if (large > small) {
                TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge)
                        / manuelLargeAdjusterDivision);
            } else {
                TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall)
                        / manuelSmallAdjusterDivision);
            }
            // LED config
            if (Map.Controllers.xbox.getRawButtonPressed(1)) {
                if (!led) {
                    LimeLight.LED.on();
                    led = true;
                } else {
                    LimeLight.LED.off();
                    led = false;
                }
            }
            // Launch
            if (xbox.getRawButton(Map.Turret.controllers.launch)) {
                Targeting.launch();
            } else {
                Targeting.stopLaunch();
            }
            // Initiate Launcher
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if(turretInitiated){
                    turretInitiated = false;
                }else{
                    turretInitiated = true;
                }
            }
            if(!autoIsTracking){
                if (turretInitiated) {
                    //TurretMotion.Launcher.setVelocity(manuelVelocity);
                    TurretMotion.Launcher.setVelocity(manuelVelocity);
                    //TurretMotion.Launcher.setPercentSpeed(1);
                } else {
                    TurretMotion.Launcher.setVelocity(0);
                }
            }
            // Change launch velocity
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherAddPower)) {
                manuelVelocity += manuelVelocityChange;
            } else if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherSubtractPower)) {
                manuelVelocity -= manuelVelocityChange;
            }

            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelSetAngle)) {
                manuelAngle = TurretMotion.Rotation.getDegrees();
            }
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelGoToAngle)) {
                if(!TurretMotion.Rotation.goTo){
                    TurretMotion.Rotation.goToPosition(manuelAngle);
                }else{
                    TurretMotion.Rotation.goTo = false;
                }
            }
        }
    }
}