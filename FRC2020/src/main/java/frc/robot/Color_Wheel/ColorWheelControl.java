package frc.robot.Color_Wheel;

import frc.robot.Map;

/**
 * ColorWheelControl
 */
public class ColorWheelControl {
    private static boolean piston = false;
    public static void run(){
        if(Map.Controllers.driverLeft.getRawButtonPressed(Map.ColorWheel.controllers.piston)){
            if(!piston){
                piston = true;
                Map.ColorWheel.piston.set(true);
            }else{
                piston = false;
                Map.ColorWheel.piston.set(false);
            }
        }
        if(Map.Controllers.driverLeft.getRawButton(Map.ColorWheel.controllers.leftTurn)){
            Arm.spin(-0.5);
        }else if(Map.Controllers.driverLeft.getRawButton(Map.ColorWheel.controllers.rightTurn)){
            Arm.spin(0.5);
        }else{
            Arm.spin(0);
        }
    }
}