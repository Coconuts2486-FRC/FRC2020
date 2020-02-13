package frc.robot.Cartridge;
import frc.robot.Map;


public class FullSensor {
    public static boolean full = false;
    public static boolean getSensorValue() {
        return Map.Cartridge.Sensors.fullSensor.get();
    }   
    public static void BallSense() {
        
        if(getSensorValue()){
             full = true;

        }else{
            full = false ;
        }
    }
}
