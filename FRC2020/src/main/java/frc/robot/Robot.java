package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    
  }

  @Override
  public void autonomousInit() {
    Map.Turret.motors.rotation.setSelectedSensorPosition(0);
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
    //pixyControl.run(); // Runs pixy homing automation

    //DriveTrain.drive();
    //pistonlift.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    if(Map.Controllers.driverLeft.getRawButtonPressed(3)){
      Map.Turret.motors.rotation.setSelectedSensorPosition(0);
    }
    SmartDashboard.putNumber("Position!!! ", TurretMotion.Rotation.getDegrees());
  }

}
