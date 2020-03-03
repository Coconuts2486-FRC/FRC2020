package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.DriverStation;


public class Color_Wheel{

public static void spinToColor(){
String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      //Blue case code
      Arm.colorMatchBlue();
      break;
    case 'G' :
      //Green case code
      Arm.colorMatchGreen();
      break;
    case 'R' :
      //Red case code
      Arm.colorMatchRed();
      break;
    case 'Y' :
      //Yellow case code
      Arm.colorMatchYellow();
      break;
    default :
      //This is corrupt data
      break;
  }
} else {
  //Code for no data received yet
}
}
}