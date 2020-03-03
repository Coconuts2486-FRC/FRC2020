package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Autonomous.AutoMissions;
import frc.robot.Cartridge.Conveyor;
import frc.robot.Cartridge.pistonlift;
import frc.robot.Cartridge.pixyDisplay;
import frc.robot.TeleOp.DriveTrain;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    TurretMotion.init(); // Configs the turret 
    Pixy.init(); // Starts up the Pixy2 Camera
    Conveyor.init(); // Configs the conveyor belts
    Map.driveTrain.gyro.setYaw(0);
  }
  public void robotPeriodic() {
    TurretDisplay.display();
    pixyDisplay.display();
    Conveyor.display();
  }
  @Override
  public void disabledInit(){
  }
  public void disabledPeriodic() {
    TurretControl.periodicRun();

    if (Map.Controllers.driverLeft.getRawButtonPressed(20)){
      AutoMissions.TrenchAuto = true;
    } else{
      AutoMissions.TrenchAuto = false;
    }

    if (Map.Controllers.driverRight.getRawButtonPressed(20)){
      AutoMissions.GeneratorAuto = true;
    } else{
      AutoMissions.GeneratorAuto = false;
    }

    SmartDashboard.putBoolean("TrenchRun", AutoMissions.TrenchAuto);
    SmartDashboard.putBoolean("GeneratorRun", AutoMissions.GeneratorAuto);

  }
  @Override
  public void autonomousInit() {
    AutoMissions.AutoInit();
  }

  @Override
  public void autonomousPeriodic() {

    if (AutoMissions.TrenchAuto == true){
      AutoMissions.TrenchRun();
    }

    if (AutoMissions.GeneratorAuto == false){
      AutoMissions.TrenchRun();
    }
  }

  @Override
  public void teleopInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void teleopPeriodic() {
    TurretControl.run(); // Runs turret
    // pixyControl.run(); // Runs pixy homing automation
    //DriveTrain.drive();
    pistonlift.run();
    Conveyor.run();
    //ColorWheelControl.run();
  }

  @Override
  public void testInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void testPeriodic(){ 
  }

}
