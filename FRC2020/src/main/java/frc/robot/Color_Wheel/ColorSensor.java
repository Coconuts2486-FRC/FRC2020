package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Map;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ColorSensor {
  

  public boolean isBlue = false;
  public boolean isRed = false;
  public boolean isGreen = false;
  public boolean isYellow = false;
  double red = 0;
  double blue = 0;
  double green = 0;

  public void CalibrateBlue(){
    /*
    double redB = SmartDashboard.getNumber("Red", red);
    double blueB = SmartDashboard.getNumber("Blue", blue);
    double greenB = SmartDashboard.getNumber("green", green);

    double redR = SmartDashboard.getNumber("Red", red);
    double blueR = SmartDashboard.getNumber("Blue", blue);
    double greenR = SmartDashboard.getNumber("green", green);

    double redG = SmartDashboard.getNumber("Red", red);
    double blueG = SmartDashboard.getNumber("Blue", blue);
    double greenG = SmartDashboard.getNumber("green", green);

    double redY = SmartDashboard.getNumber("Red", red);
    double blueY = SmartDashboard.getNumber("Blue", blue);
    double greenY = SmartDashboard.getNumber("green", green);
    */
    

  }

  

  public void colorDetected(){

    final Color detectedColor = Map.ColorWheel.sensor.getColor();
    final double IR = Map.ColorWheel.sensor.getIR();
    final int proximity = Map.ColorWheel.sensor.getProximity();

    
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putBoolean("Blue Detected", isBlue);
    SmartDashboard.putBoolean("Green Detected", isGreen);
    SmartDashboard.putBoolean("Red Detected", isRed);
    SmartDashboard.putBoolean("Yellow Detected", isYellow);

    // detects blue
    if(isRed = false){
      isBlue = true;
    } else{
      isBlue = false;
    }

    // detects red
    if(detectedColor.red > .49 && detectedColor.red < .51 && detectedColor.blue > .13 && detectedColor.blue < .14 && detectedColor.green > .345 && detectedColor.green < .355 ) {
      isRed = true;
    } else {
      isRed = false;
    }

    // detects green
    if(detectedColor.red > .17 && detectedColor.red < .18 && detectedColor.blue > .24 && detectedColor.blue < .255 && detectedColor.green > .58 && detectedColor.green < .59 ) {
      isGreen = true;
    } else {
      isGreen = false;
    }

    //detects yellow
    if(detectedColor.red > .31 && detectedColor.red < .32 && detectedColor.blue > .11 && detectedColor.blue < .12 && detectedColor.green > .555 && detectedColor.green < .565 ) {
      isYellow = true;
    } else {
      isYellow = false;
    }


  }

  
    
}