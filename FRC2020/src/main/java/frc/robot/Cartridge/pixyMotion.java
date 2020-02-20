package frc.robot.Cartridge;

import frc.robot.Map;
import frc.robot.Vision.Pixy;

/**
 * pixyMotion
 */
public class pixyMotion {
    private static double errorRange = 0.01; // distance from target on pixy screen (-1 to 1) until considered zeroed in
    private static double slopeRange = 0.05; // distance from target until drive starts to slow down
    private static double targetPosition = -0.57; // target position
    public static boolean zeroedInOnTarget = false;

    public static void zeroInOnTarget() {
        if (Pixy.isTarget()) {
            int targetIndex = Pixy.getBiggestBlock();
            double x = Pixy.getX(targetIndex);
            double error = x - targetPosition;
            double abserror = Math.abs(error);
            x = Pixy.getX(targetIndex);
            error = x - targetPosition;
            abserror = Math.abs(error);
            if (abserror > errorRange) {
                zeroedInOnTarget = false;
                if (abserror > slopeRange) {
                    if (error > 0) {
                        drive.strafe(-1);
                    } else {
                        drive.strafe(1);
                    }
                } else {
                    drive.strafe(error);
                }
            } else {
                drive.strafe(0);
                zeroedInOnTarget = true;
            }
        }
    }

    private static class drive {
        public static void strafe(double power) {
            Map.driveTrain.lf.set(-power);
            Map.driveTrain.lr.set(power);
            Map.driveTrain.rf.set(-power);
            Map.driveTrain.rr.set(power);
        }
    }
}