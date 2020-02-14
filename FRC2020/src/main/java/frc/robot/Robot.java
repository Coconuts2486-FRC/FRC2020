package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Cartridge.pixyControl;
import frc.robot.Map.driveTrain;
import frc.robot.TeleOp.DriveTrain;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    TurretMotion.Rotation.setPosition(0); // Resets encoder ticks
  }

  public void robotPeriodic(){
    
  }

  @Override
  public void autonomousInit() {
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
    
    DriveTrain.drive(); // Driver Controll ** BIG DEAL **
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
