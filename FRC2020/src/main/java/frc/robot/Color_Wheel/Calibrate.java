package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Map;

public class Calibrate{
    
    
    public static class Colors{
        public static Color1 Red = new Color1();
        public static Color1 Blue = new Color1();
        public static Color1 Green = new Color1();
        public static Color1 Yellow = new Color1();
    }

    public static class Color1{
        public double r;
        public double g;
        public double b;
    }

    public static void setColor(String color) {
        final Color detectedColor = Map.ColorWheel.sensor.getColor();

        switch(color){
            case("red"):
                Colors.Red.r = detectedColor.red;
                Colors.Red.g = detectedColor.green;
                Colors.Red.b = detectedColor.blue;
                break;
            case("blue"):
                Colors.Blue.r = detectedColor.red;
                Colors.Blue.g = detectedColor.green;
                Colors.Blue.b = detectedColor.blue;
                break;
            case("green"):
                Colors.Green.r = detectedColor.red;
                Colors.Green.g = detectedColor.green;
                Colors.Green.b = detectedColor.blue;
                break;
            case("yellow"):
                Colors.Yellow.r = detectedColor.red;
                Colors.Yellow.g = detectedColor.green;
                Colors.Yellow.b = detectedColor.blue;
                break;
        }



    }
}