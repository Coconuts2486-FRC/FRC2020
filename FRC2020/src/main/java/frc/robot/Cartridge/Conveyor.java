package frc.robot.Cartridge;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Map;

public class Conveyor {
    public static void conveyor(){
        
    }
    public static void LauncherLoader(Double x){
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, x);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, x);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, x);
    }
}