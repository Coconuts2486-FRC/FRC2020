package frc.robot.Cartridge;

import frc.robot.Map;

public class FullSensor {
    public static boolean getSensorValue() {
        return Map.Cartridge.Sensors.fullSensor.get();
    }
}
