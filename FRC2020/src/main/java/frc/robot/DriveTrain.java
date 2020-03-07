package frc.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
    private static double deadBand = 0.05;
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
        powerManagement(Map.driveTrain.lf, lf);
        powerManagement(Map.driveTrain.lr, lr);
        powerManagement(Map.driveTrain.rf, rf);
        powerManagement(Map.driveTrain.rr, rr);
         SmartDashboard.putNumber("Driver Left", Map.Controllers.driverLeft.getY());
         SmartDashboard.putNumber("Driver Right", Map.Controllers.driverRight.getY());
    }
    private static void powerManagement(CANSparkMax motor, double pwr){
        double abspwr = Math.abs(pwr);
        if(abspwr>deadBand){
            motor.set(pwr);
        }else{
            motor.set(0);
        }
    }
}
