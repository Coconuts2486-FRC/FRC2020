package frc.robot.TeleOp;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

/**
 * DriveTrain
 */
public class DriveTrain {

    public static double ticksToFeet = 6 * Math.PI / 84;

    public static void drive(){
        SmartDashboard.putNumber("Encoder Position fr", -Map.driveTrain.rfEncoder.getPosition() * ticksToFeet);
        SmartDashboard.putNumber("Encoder Position fl", -Map.driveTrain.lfEncoder.getPosition() * ticksToFeet);
        SmartDashboard.putNumber("Encoder Position br", -Map.driveTrain.rrEncoder.getPosition() * ticksToFeet);
        SmartDashboard.putNumber("Encoder Position bl", -Map.driveTrain.lrEncoder.getPosition() * ticksToFeet);

        // stores current joystick location in a variable
        final double leftX = Map.Controllers.driverLeft.getX();
        final double rightX = Map.Controllers.driverRight.getX();
        final double leftY = Map.Controllers.driverLeft.getY();
        final double rightY = Map.Controllers.driverRight.getY();
        // creates final movement variable for meccanum drive
        final double lf = leftY - leftX;
        final double rf = rightY + rightX;
        final double lr = leftY + leftX;
        final double rr = rightY - rightX;
        // returns the final movement variables
    
        Map.driveTrain.lf.set(lf);
        Map.driveTrain.lr.set(lr);
        Map.driveTrain.rf.set(rf);
        Map.driveTrain.rr.set(rr);
    }
}