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
import frc.robot.Turret.TurretControl;
import frc.robot.Turret.TurretDisplay;
import frc.robot.Turret.TurretMotion;
import frc.robot.Utilities.Sleep;
import frc.robot.Vision.Pixy;

public class Robot extends TimedRobot {

  double startTime = 0;
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
    
    
    //AutoMissions.TrenchRun();
    if(!AutoCommands3.ran){
      AutoCommands3.Turret.goTo(80.73);
      Sleep.delay(100);
      AutoCommands3.Turret.init();
      AutoCommands3.Turret.launch(3);
      
      AutoCommands3.goDistance(7.3); // goes up to first ball
      Sleep.delay(100);
      while(!AutoCommands3.Turret.hasLaunched){

      }
      AutoCommands3.Piston.on();
      AutoCommands3.Loading.load();
      AutoCommands3.goDistance(9);

      AutoCommands3.Turret.launch(1);
      Sleep.delay(100);
      while(!AutoCommands3.Turret.hasLaunched){

      }
      //Sleep.delay(5000);
      /*
      while(!AutoCommands3.Turret.hasLaunched){

      }
      */
      /*
      AutoCommands3.Piston.on();
      AutoCommands3.Loading.load();
      AutoCommands3.goDistance(3);
      */
      AutoCommands3.endAuto();
    }
    
  


    /*if (AutoMissions.TrenchAuto == true){
      AutoMissions.TrenchRun();
    }

    if (AutoMissions.GeneratorAuto == false){
      AutoMissions.TrenchRun();
    }*/

    
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
    //ColorWheelControl.run();
    Map.driveTrain.gyro.getYawPitchRoll(PID.turnPID.ypr_deg);
    SmartDashboard.putNumber("Angle", PID.turnPID.ypr_deg[0]);


  }

  @Override
  public void testInit() {
    Map.Turret.motors.rotation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void testPeriodic(){ 
  }

}
