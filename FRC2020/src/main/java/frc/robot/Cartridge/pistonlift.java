package frc.robot.Cartridge;
import com.ctre.phoenix.motorcontrol.*;
import frc.robot.Map;

public class pistonlift {
    private static boolean pistonactive = false;
    public static void run() {
        if (Map.Controllers.driverLeft.getRawButtonPressed(1)) {
            if(!pistonactive){
                Map.Cartridge.RightPiston.set(true);
                pistonactive = true;
            }else{
                Map.Cartridge.RightPiston.set(false);
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 0);
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
                pistonactive = false;
            }
        } 
        /*
        if(Map.Controllers.driverRight.getRawButton(1)){
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1);
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, -0.25);
        }else if(Map.Controllers.driverRight.getRawButton(5)){
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, -1);
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 1);
        }else{
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
        }
        */
    }
}
