package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Map;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ColorSensor {
  
  public static boolean isBlue = false;
  public static boolean isRed = false;
  public static boolean isGreen = false;
  public static boolean isYellow = false;
  final Color detectedColor = Map.ColorWheel.sensor.getColor();
  private static double maxColorError = 0.001;

  private static boolean isColor(Calibrate.Color1 color, Calibrate.Color1 compare){

    double Rabserror = Math.abs(compare.r-color.r);
    double Gabserror = Math.abs(compare.g-color.g);
    double Babserror = Math.abs(compare.b-color.b);

    if(Rabserror<maxColorError && Gabserror<maxColorError && Babserror<maxColorError){
      return true;
    }else{
      return false;
    }
  }
  
  public static void getColor(){
    Color precolor = Map.ColorWheel.sensor.getColor();
    Calibrate.Color1 color = new Calibrate.Color1();
    color.r=precolor.red;
    color.g=precolor.green;
    color.b=precolor.blue;

    if(isColor(color, Calibrate.Colors.Red)){
      isRed = true;

    }else if(isColor(color, Calibrate.Colors.Green)){
      isGreen = true;

    }else if(isColor(color, Calibrate.Colors.Blue)){
      isBlue = true;

    }else if(isColor(color, Calibrate.Colors.Yellow)){
      isYellow = true;

    }else{
      isRed = false;
      isBlue = false;
      isGreen = false;
      isYellow = false;

    }
  }
}