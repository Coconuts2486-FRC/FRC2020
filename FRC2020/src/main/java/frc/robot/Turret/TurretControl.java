package frc.robot.Turret;

import frc.robot.Map;
import frc.robot.Cartridge.Conveyor;
import frc.robot.Vision.LimeLight;

/**
 * TurretControl
 */
public class TurretControl {
    private static boolean led = false;
    public static boolean encoderDisabled = false;

    public static void run() {
        if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelMode)) {
            switchModes();
        }
        if (TurretSettings.automaticModeActive) {
            // Auto Mode
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if (!TurretSettings.launching.automatic.automaticLauncherInitiated) {
                    TurretSettings.launching.automatic.automaticLauncherInitiated = true;
                    Targeting.initilize();
                } else {
                    TurretSettings.launching.automatic.automaticLauncherInitiated = false;
                    Targeting.stop();
                }
            }
            if (Map.Controllers.xbox.getRawButton(Map.Turret.controllers.launch) && Targeting.readyToFire()) {
                TurretSettings.turretUsingConveyors = true;
                Targeting.autoLaunch();
            } else {
                TurretSettings.turretUsingConveyors = false;
                Targeting.stopLaunch();
            }

            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.LimeLightLED)) {
                if (!led) {
                    LimeLight.LED.on();
                    led = true;
                } else {
                    LimeLight.LED.off();
                    led = false;
                }
            }
        } else {
            // Manuel Mode
            // Turning
            if(Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelEncoderDisable)){
                if(encoderDisabled){
                    encoderDisabled = false;
                }else{
                    encoderDisabled = true;
                }
            }
            double large = Math.abs(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge));
            double small = Math.abs(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall));
            if(encoderDisabled){
                if (large > small) {
                    TurretMotion.Rotation.overrideTurn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge)
                            / TurretSettings.rotation.manual.manuelLargeAdjusterDivision);
                } else {
                    TurretMotion.Rotation.overrideTurn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall)
                            / TurretSettings.rotation.manual.manuelSmallAdjusterDivision);
                }
                if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelEncoderZeroer)) {
                    Map.Turret.motors.rotation.setSelectedSensorPosition(0);
                  }
            }else{
                if (large > small) {
                    TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge)
                            / TurretSettings.rotation.manual.manuelLargeAdjusterDivision);
                } else {
                    TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall)
                            / TurretSettings.rotation.manual.manuelSmallAdjusterDivision);
                }
            }
            // LED config
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.LimeLightLED)) {
                if (!led) {
                    LimeLight.LED.on();
                    led = true;
                } else {
                    LimeLight.LED.off();
                    led = false;
                }
            }
            // Launch
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.launch)) {
                    Targeting.learningLauncher();
            }
            
            //outputs balls
            if(Map.Controllers.xbox.getRawButton(Map.Turret.controllers.outtake)){
                TurretSettings.turretUsingConveyors=true;
                Conveyor.outtake();
            }else{
                TurretSettings.turretUsingConveyors=false;
            }
            
            // Initiate Launcher
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if(TurretSettings.launching.manual.manualLauncherInitiated){
                    TurretMotion.Launcher.setVelocity(0);
                    TurretSettings.launching.manual.manualLauncherInitiated = false;
                }else{
                    TurretMotion.Launcher.setVelocity(TurretSettings.launching.manual.manuelVelocity);
                    TurretSettings.launching.manual.manualLauncherInitiated = true;
                }
            }
            // Change launch velocity
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherAddPower)) {
                TurretSettings.launching.manual.manuelVelocity += TurretSettings.launching.manual.manuelVelocityChange;
            } else if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherSubtractPower)) {
                TurretSettings.launching.manual.manuelVelocity -= TurretSettings.launching.manual.manuelVelocityChange;
            }

            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelSetAngle)) {
                TurretSettings.rotation.manual.manuelAngle = TurretMotion.Rotation.getDegrees();
            }
            if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelGoToAngle)) {
                if(!TurretSettings.rotation.manual.manualGoTo){
                    TurretMotion.Rotation.goToPosition(TurretSettings.rotation.manual.manuelAngle);
                }else{
                    TurretSettings.rotation.manual.manualGoTo = false;
                }
            }
        }
    }
    public static void periodicRun() {
        if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelEncoderZeroer)) {
            Map.Turret.motors.rotation.setSelectedSensorPosition(0);
          }
    }
    public static void switchModes(){
        if (!TurretSettings.automaticModeActive) {
            // Switch to Automatic Targeting
            TurretSettings.automaticModeActive = true;
            led = false;
            Targeting.stop();
        }else {
            // Switch to Manual Targeting
            TurretSettings.automaticModeActive = false;
            Targeting.stop();
        }
    }
}