package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< HEAD
=======
import frc.robot.Autonomous.AutoMissions;
import frc.robot.Cartridge.pixyControl;
import frc.robot.Color_Wheel.ColorSensor;
>>>>>>> 41bc6a56fde2d046687205cc30fa675a6331f4c6
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    TurretMotion.init();
    Pixy.init(); // Starts up the Pixy2 Camera
  }

  public void robotPeriodic(){
    SmartDashboard.putString("Auto Mode", AutoMissions.CurrentAuto);
    SmartDashboard.getNumber("Auto Selection", AutoMissions.SelectedAuto);

    
    
    
  }

  @Override
  public void autonomousInit() {
<<<<<<< HEAD
    Map.Turret.motors.rotation.setSelectedSensorPosition(0);
=======
    if(AutoMissions.SelectedAuto == 0){
      AutoMissions.NoAutoSelected();
    }
    if(AutoMissions.SelectedAuto == 1){
      AutoMissions.TrenchRun();
    }

    if(AutoMissions.SelectedAuto == 2){
      AutoMissions.GeneratorAuto();
    }
>>>>>>> 41bc6a56fde2d046687205cc30fa675a6331f4c6
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void teleopPeriodic() {
    TurretControl.run(); // Runs turret
    TurretDisplay.display(); // Displays LimeLight stats
    pixyControl.run(); // Runs pixy homing automation

<<<<<<< HEAD
    //DriveTrain.drive();
    //pistonlift.run();
=======
    //DriveTrain.drive(); // Driver Controll ** BIG DEAL ** (needs to have correct CAN IDs)
    /*
    if(Map.Controllers.xbox.getRawButton(1)){
      LimeLight.LED.on();
    }else{
      LimeLight.LED.off();
    }
    */
>>>>>>> 41bc6a56fde2d046687205cc30fa675a6331f4c6
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
<<<<<<< HEAD
    if(Map.Controllers.driverLeft.getRawButtonPressed(3)){
      Map.Turret.motors.rotation.setSelectedSensorPosition(0);
    }
    SmartDashboard.putNumber("Position!!! ", TurretMotion.Rotation.getDegrees());
=======
    SmartDashboard.putNumber("Red: ", ColorSensor.GetColor());
>>>>>>> 41bc6a56fde2d046687205cc30fa675a6331f4c6
  }

}
