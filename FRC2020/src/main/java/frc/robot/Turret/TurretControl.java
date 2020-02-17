package frc.robot.Turret;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

/**
 * TurretControl
 */
public class TurretControl {
    private static XboxController xbox = Map.Controllers.xbox;
    private static boolean turretInitiated = false;
    public static boolean manuelMode = false;
    private static int manuelVelocity = 1000; // set to whatever base velocity should be
    private static int manuelVelocityChange = 100; // The amount that velocity is adjusted when POV is pressed
    private static double manuelSmallAdjusterDivision = 2; // how much the small adjuster is divided by

    public static void run() {
        if(Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelMode)){
            if(manuelMode){
                manuelMode = false;
            }else{
                manuelMode = true;
                Targeting.stop();
                turretInitiated = false;
            }
        }
        if(!manuelMode){
            SmartDashboard.putBoolean("Ready to Fire!", Targeting.readyToFire());
            if (xbox.getRawButtonPressed(Map.Turret.controllers.initiation)) {
                if (!turretInitiated) {
                    turretInitiated = true;
                    Targeting.initilize();
                }else {
                    Targeting.stop();
                    turretInitiated = false;
                }
            }
            if(xbox.getRawButton(Map.Turret.controllers.launch)&&Targeting.readyToFire()){
                Targeting.launch();
            }
        }else{
            TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateLarge));
            TurretMotion.Rotation.turn(Map.Controllers.xbox.getRawAxis(Map.Turret.controllers.manuelRotateSmall)/manuelSmallAdjusterDivision);
            if(Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.initiation)){
                if(!turretInitiated){
                    turretInitiated = true;
                    TurretMotion.Launcher.setVelocity(manuelVelocity);
                }else{
                    turretInitiated = false;
                    TurretMotion.Launcher.setPercentSpeed(0);
                }
                if(Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherAddPower)){
                    manuelVelocity+=manuelVelocityChange;
                }else if(Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.manuelLauncherSubtractPower)){
                    manuelVelocity-=manuelVelocityChange;
                }
                if(xbox.getRawButton(Map.Turret.controllers.launch)){
                    Targeting.launch();
                }
            }
        }
    }
}