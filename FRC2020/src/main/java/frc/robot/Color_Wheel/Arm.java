package frc.robot.Color_Wheel;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Map;

public class Arm{

    public static boolean colorPistonActive = false;
    public static boolean isCompleted;

    public static void lift() {
        /*
        if (Map.Controllers.xbox.getRawButtonPressed(20)) {
            if (!colorPistonActive) {
                Map.ColorWheel.SensorLift.set(true);
                colorPistonActive = true;
            } else{
                Map.ColorWheel.SensorLift.set(false);
                colorPistonActive = false;
                

            }

            
        }
        */

    }

    public static void colorMatchBlue(){
        if(isCompleted == true){
            Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, 0);
        }else{
            if(ColorSensor.isBlue == true){
                while(ColorSensor.isBlue == true){
                    Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .2);
            }
        }
    }
        
        if(ColorSensor.isBlue == false){
            while(ColorSensor.isBlue == false){
                Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .5);
                if(ColorSensor.isBlue == true){
                    isCompleted = true;
                }
            }
    }   
    }

    public static void colorMatchGreen(){
        if(isCompleted == true){
            Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, 0);
        }else{
            if(ColorSensor.isGreen == true){
                while(ColorSensor.isGreen == true){
                    Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .2);
            }
        }
    }
    
        
        if(ColorSensor.isGreen == false){
            while(ColorSensor.isGreen == false){
                Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .5);
                if(ColorSensor.isGreen == true){
                    isCompleted = true;
                }
        }
    }
    }

    public static void colorMatchYellow(){
        if(isCompleted == true){
            Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, 0);
        }else{
            if(ColorSensor.isYellow == true){
                while(ColorSensor.isYellow == true){
                    Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .2);
            }
        }
    }
        
        if(ColorSensor.isYellow == false){
            while(ColorSensor.isYellow == false){
                Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .5);
                if(ColorSensor.isYellow == true){
                    isCompleted = true;
                }
        }
    }
     
    }

    public static void colorMatchRed(){
        if(isCompleted == true){
            Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, 0);
        }else{
            if(ColorSensor.isRed == true){
                while(ColorSensor.isRed == true){
                    Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .2);
            }
        }
    }
        
        if(ColorSensor.isRed == false){
            while(ColorSensor.isRed == false){
                Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .5);
                if(ColorSensor.isRed == true){
                    isCompleted = true;
                }
        }
    }

    }

    public static void spin(){
        if(Map.Controllers.xbox.getRawButton(50)){
            Map.ColorWheel.ColorSpinner.set(ControlMode.PercentOutput, .5);
        }

    }

}