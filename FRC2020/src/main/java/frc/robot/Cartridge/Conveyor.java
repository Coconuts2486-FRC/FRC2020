package frc.robot.Cartridge;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Turret.TurretSettings;

public class Conveyor {
    public static void init() {
        Map.Cartridge.Conveyor1.setInverted(true);
        Map.Cartridge.Conveyor2.setInverted(true);
    }
    public static void display(){
        SmartDashboard.putBoolean("Intake Sensor: ", FullSensor.getSensorValue());
    }
    public static void run() {
        if (Map.Controllers.driverRight.getRawButton(Map.Cartridge.controllers.intake)) {
            if (FullSensor.getSensorValue()&&(!TurretSettings.turretUsingConveyors)) {
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.5); // adjust speeds
                Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0.3);
                Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0.3);
            } else if(!TurretSettings.turretUsingConveyors){
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
                Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
                Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
            }
        }else if((Map.Controllers.driverRight.getRawButton(Map.Cartridge.controllers.outtake))&&(!TurretSettings.turretUsingConveyors)){
            outtake();
        }else if(!TurretSettings.turretUsingConveyors){
            stop();
        }
    }
    public static void outtake(){
        Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, -1);
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, -1);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, -1);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, -1);
    }
    public static void stop(){
        Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
    }
}