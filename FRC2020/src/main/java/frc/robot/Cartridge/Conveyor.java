package frc.robot.Cartridge;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Map;
import frc.robot.Turret.Targeting;
import frc.robot.Turret.TurretControl;

public class Conveyor {
    public static void init() {
        Map.Cartridge.Conveyor1.setInverted(true);
        Map.Cartridge.Conveyor2.setInverted(true);
    }

    public static void run() {
        if (Map.Controllers.driverRight.getRawButton(1)) {
            if (FullSensor.getSensorValue()&&(!Targeting.isFiring)) {
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.4); // adjust speeds
            } else if(!Targeting.isFiring){
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
            }
        }else if(Map.Controllers.driverLeft.getRawButton(5)&&(!Targeting.isFiring)){
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, -1);
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, -1);
            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, -1);
            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, -1);
        }else if(!Targeting.isFiring){
            Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
            Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
        }
    }
}