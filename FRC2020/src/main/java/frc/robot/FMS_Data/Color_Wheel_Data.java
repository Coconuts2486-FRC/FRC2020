package frc.robot.FMS_Data;

import edu.wpi.first.wpilibj.DriverStation;


public class Color_Wheel_Data{

public static void getColor(){
String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      //Blue case code
      break;
    case 'G' :
      //Green case code
      break;
    case 'R' :
      //Red case code
      break;
    case 'Y' :
      //Yellow case code
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