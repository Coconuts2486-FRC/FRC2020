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
     ran = false;
  }

  
  public static boolean ran = false;
  @Override
  public void autonomousPeriodic() {
    
  
    //AutoMissions.TrenchRun();
    if(!ran){
      AutoCommands3.Turret.goTo(80.73);
      AutoCommands3.Turret.init();
      AutoCommands3.Turret.launch(3);
      
      AutoCommands3.goDistance(5); // goes up to first ball
      while(!AutoCommands3.Turret.hasLaunched){

      }
      /*
      AutoCommands3.Piston.on();
      AutoCommands3.Loading.load();
      AutoCommands3.goDistance(3);
      */
      AutoCommands3.endAuto();
      ran=true;
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
