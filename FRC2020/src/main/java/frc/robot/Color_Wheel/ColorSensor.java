package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Map;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ColorSensor {
  
  double redHigh = .14;
  double redLow = .13;
  double blueHigh = .44;
  double blueLow = .45;
  double greenHigh = .42;
  double greenLow = .43;
  double redDifference = redHigh - redLow;
  double blueDifference = blueHigh - blueLow;
  double greenDifference = greenHigh - greenLow;
  public boolean isBlue = false;
  public boolean isRed = false;
  public boolean isGreen = false;
  public boolean isYellow = false;

  public void colorDetected(){

    Color detectedColor = Map.ColorWheel.sensor.getColor();
    double IR = Map.ColorWheel.sensor.getIR();
    int proximity = Map.ColorWheel.sensor.getProximity();

    
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putBoolean("Blue Detected", isBlue);
    SmartDashboard.putBoolean("Green Detected", isGreen);
    SmartDashboard.putBoolean("Red Detected", isRed);
    SmartDashboard.putBoolean("Yellow Detected", isYellow);

    if(detectedColor.red > .13 && detectedColor.red < .15 && detectedColor.blue > .43 && detectedColor.blue < .45 && detectedColor.green > .42 && detectedColor.green < .44){
      isBlue = true;
    } else{
      isBlue = false;
    }

    if(detectedColor.red > .49 && detectedColor.red < .51 && detectedColor.blue > .13 && detectedColor.blue < .14 && detectedColor.green > .345 && detectedColor.green < .355 ) {
      isRed = true;
    } else {
      isRed = false;
    }

    if(detectedColor.red > .17 && detectedColor.red < .18 && detectedColor.blue > .24 && detectedColor.blue < .255 && detectedColor.green > .58 && detectedColor.green < .59 ) {
      isGreen = true;
    } else {
      isGreen = false;
    }

    if(detectedColor.red > .31 && detectedColor.red < .32 && detectedColor.blue > .11 && detectedColor.blue < .12 && detectedColor.green > .555 && detectedColor.green < .565 ) {
      isYellow = true;
    } else {
      isYellow = false;
    }


  }

  
    
}