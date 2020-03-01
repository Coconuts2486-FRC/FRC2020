package frc.robot.Cartridge;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Turret.Targeting;

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
            if (FullSensor.getSensorValue()&&(!Targeting.isFiring)) {
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.3); // adjust speeds
                Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0.3);
                Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0.3);
            } else if(!Targeting.isFiring){
                Map.Cartridge.ArmRoller.set(ControlMode.PercentOutput, 1); // adjust speeds
                Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
                Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
                Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
            }
        }else if((Map.Controllers.driverLeft.getRawButton(Map.Cartridge.controllers.outtake)||Map.Controllers.driverRight.getRawButton(Map.Cartridge.controllers.outtake))&&(!Targeting.isFiring)){
            outtake();
        }else if(!Targeting.isFiring){
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