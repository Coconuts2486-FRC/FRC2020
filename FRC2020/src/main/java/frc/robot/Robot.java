package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Autonomous.AutoMissions;
import frc.robot.Cartridge.Conveyor;
import frc.robot.Cartridge.pistonlift;
import frc.robot.Cartridge.pixyDisplay;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {
  public static boolean colorwheel = false;
  @Override
  public void robotInit() {
    TurretMotion.init(); // Configs the turret 
    Pixy.init(); // Starts up the Pixy2 Camera
    Conveyor.init(); // Configs the conveyor belts
  }

  public void robotPeriodic() {
    TurretDisplay.display();
    TurretControl.periodicRun();
  }

  @Override
  public void autonomousInit() {
    if (AutoMissions.SelectedAuto == 0) {
      AutoMissions.NoAutoSelected();
    }
    if (AutoMissions.SelectedAuto == 1) {
      AutoMissions.TrenchRun();
    }

    if (AutoMissions.SelectedAuto == 2) {
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
    pixyDisplay.display();
    // pixyControl.run(); // Runs pixy homing automation
    //DriveTrain.drive();
    pistonlift.run();
    Conveyor.run();
  }

  @Override
  public void testInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void testPeriodic(){ 
  }

}
