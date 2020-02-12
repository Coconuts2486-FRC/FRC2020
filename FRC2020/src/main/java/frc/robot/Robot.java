package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretMotion;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    TurretMotion.Rotation.setPosition(0); // Resets encoder ticks
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
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
