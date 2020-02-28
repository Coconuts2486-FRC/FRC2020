package frc.robot.Cartridge;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Vision.Pixy;

/**
 * pixyDisplay
 */
public class pixyDisplay {
    public static void display() {
        SmartDashboard.putBoolean("PowerCell Available: ", Pixy.isTarget());
    }
}