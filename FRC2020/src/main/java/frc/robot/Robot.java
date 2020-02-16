package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Autonomous.AutoMissions;
import frc.robot.Cartridge.pixyControl;
import frc.robot.Color_Wheel.ColorSensor;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Vision.LimeLight;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    TurretMotion.Rotation.setPosition(0); // Resets encoder ticks
    Pixy.init(); // Starts up the Pixy2 Camera
  }

  public void robotPeriodic(){
    SmartDashboard.putString("Auto Mode", AutoMissions.CurrentAuto);
    SmartDashboard.getNumber("Auto Selection", AutoMissions.SelectedAuto);

    
    
    
  }

  @Override
  public void autonomousInit() {
    if(AutoMissions.SelectedAuto == 0){
      AutoMissions.NoAutoSelected();
    }
    if(AutoMissions.SelectedAuto == 1){
      AutoMissions.TrenchRun();
    }

    if(AutoMissions.SelectedAuto == 2){
      AutoMissions.GeneratorAuto();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    TurretControl.run(); // Runs turret
    TurretDisplay.display(); // Displays LimeLight stats
    pixyControl.run(); // Runs pixy homing automation

    //DriveTrain.drive(); // Driver Controll ** BIG DEAL ** (needs to have correct CAN IDs)
    /*
    if(Map.Controllers.xbox.getRawButton(1)){
      LimeLight.LED.on();
    }else{
      LimeLight.LED.off();
    }
    */
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    SmartDashboard.putNumber("Red: ", ColorSensor.GetColor());
  }

}
