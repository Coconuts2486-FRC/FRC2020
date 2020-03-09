package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Autonomous.AutoMissions;
import frc.robot.Autonomous.PID;
import frc.robot.Autonomous.Commands.AutoCommands3;
import frc.robot.Cartridge.Conveyor;
import frc.robot.Cartridge.pistonlift;
import frc.robot.Cartridge.pixyDisplay;
import frc.robot.Climber.Climb;
import frc.robot.Color_Wheel.ColorWheelControl;
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Vision.LimeLight;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {

  double startTime = 0;
  @Override
  public void robotInit() {
    TurretMotion.init(); // Configs the turret 
    LimeLight.LED.off();
    Pixy.init(); // Starts up the Pixy2 Camera
    Conveyor.init(); // Configs the conveyor belts
    Climb.Init();
    Map.driveTrain.gyro.setYaw(0);
  }
  public void robotPeriodic() {
    TurretDisplay.display();
    pixyDisplay.display();
    Conveyor.display();
  }
  @Override
  public void disabledInit(){
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Coast);
  }
  public void disabledPeriodic() {
    TurretControl.periodicRun();

    if (Map.Controllers.driverLeft.getRawButtonPressed(10)){
      AutoMissions.TrenchAuto = true;
    } else{
      AutoMissions.TrenchAuto = false;
    }

    if (Map.Controllers.driverRight.getRawButtonPressed(10)){
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
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
     startTime = Timer.getFPGATimestamp();
     AutoCommands3.ran = false;
  }

  @Override

  public void autonomousPeriodic() {
    //AutoMissions.trenchRun2();
    AutoMissions.test();
    SmartDashboard.putNumber("gyro angle2", PID.turnPID.ypr_deg[0]);
  }

  @Override
  public void teleopInit() {
    AutoCommands3.endAuto();
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void teleopPeriodic() {
    TurretControl.run(); // Runs turret
    // pixyControl.run(); // Runs pixy homing automation
    DriveTrain.drive();
    pistonlift.run();
    Conveyor.run();
    Climb.run();
    ColorWheelControl.run();
    Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);
    SmartDashboard.putNumber("gyro angle2", PID.turnPID.ypr_deg[0]);
  }

  @Override
  public void testInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void testPeriodic(){ 
  }

}
