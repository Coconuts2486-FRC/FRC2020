package frc.robot.Color_Wheel;

import frc.robot.Map;

public class Arm{

    public static boolean colorPistonActive = false;

    public static void lift() {
        if (Map.Controllers.xbox.getRawButtonPressed(20)) {
            if (!colorPistonActive) {
                Map.ColorWheel.SensorLift.set(true);
                colorPistonActive = true;
            } else{
                Map.ColorWheel.SensorLift.set(false);
                colorPistonActive = false;
                

            }


        }


    }

    public static void spinner(){

    }

    public static void spinnerRed(){

    }

    public static void spinnerGreen(){

    }

    public static void spinnerYellow(){

    }
    
    public static void spinnerBlue(){

    }
}