package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Turret.TurretControl;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
  
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
    TurretControl.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
