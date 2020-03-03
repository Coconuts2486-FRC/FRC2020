package frc.robot.Color_Wheel;

import frc.robot.Map;

/**
 * ColorWheelControl
 */
public class ColorWheelControl {
    private static boolean climberActive = false;
    public static void run(){
        if(Map.Controllers.xbox.getRawButtonPressed(8)){
            if(!climberActive){
                climberActive = true;
                Map.ColorWheel.SensorLift1.set(true);
            }else{
                climberActive = false;
                Map.ColorWheel.SensorLift1.set(false);
            }
        }
    }
}