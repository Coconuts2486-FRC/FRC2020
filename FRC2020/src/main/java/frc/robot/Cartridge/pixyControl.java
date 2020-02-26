package frc.robot.Cartridge;

import frc.robot.Map;

/**
 * pixyControl
 */
public class pixyControl {
    public static void run() {
        if (Map.Controllers.driverRight.getRawButton(Map.Cartridge.controllers.zeroInOnTarget)) {
            pixyMotion.zeroInOnTarget();
        }
    }
}