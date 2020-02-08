package frc.robot.Cartridge;
import com.ctre.phoenix.motorcontrol.*;
import frc.robot.Map;

public class pistonlift {
    public static void pistons() {
        if (Map.Controllers.driverLeft.getRawButton(1) || Map.Controllers.driverRight.getRawButton(1)) {
            Map.Cartridge.RightPiston.set(true);
            Map.Cartridge.LeftPiston.set(true);
        } else {
            Map.Cartridge.RightPiston.set(false);
            Map.Cartridge.LeftPiston.set(false);
        }
       
    }
    public static void PistonRoller() {
       if (Map.Controllers.driverLeft.getRawButton(1) || Map.Controllers.driverRight.getRawButton(1) ) {
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1);
       }
    }
}
