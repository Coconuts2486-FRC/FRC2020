package frc.robot.Climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.Map;

public class Climb{

    public static void Init(){

        Map.climber.leftClimb.setNeutralMode(NeutralMode.Brake);
        Map.climber.rightClimb.setNeutralMode(NeutralMode.Brake);
    }


    public static void run(){

        //velocity eqqualizer needs to be finalized
        /*
        double leftClimbVelocity = Map.climber.leftClimb.getSelectedSensorVelocity();
        double rightClimbVelocity = Map.climber.rightClimb.getSelectedSensorVelocity();
        double error = Math.abs(leftClimbVelocity - rightClimbVelocity);

  

            if (Map.Controllers.driverRight.getRawButton(Map.climber.lift)){
                Map.climber.leftClimb.set(ControlMode.Velocity, leftClimbVelocity + error);
                Map.climber.rightClimb.set(ControlMode.Velocity, leftClimbVelocity - error);
            } else if (Map.Controllers.driverRight.getRawButton(Map.climber.lower)){
                Map.climber.leftClimb.set(ControlMode.Velocity, -leftClimbVelocity + error);
                Map.climber.rightClimb.set(ControlMode.Velocity, -leftClimbVelocity - error);
            } else{
                Map.climber.leftClimb.set(ControlMode.Velocity, 0);
                Map.climber.rightClimb.set(ControlMode.Velocity, 0);
            }
            */


            //uses percentoutput currently instead of velocity
            if (Map.Controllers.driverRight.getRawButton(Map.climber.lift)){
                Map.climber.leftClimb.set(ControlMode.PercentOutput, .95);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, .9);
            } else if (Map.Controllers.driverRight.getRawButton(Map.climber.lower)){
                Map.climber.leftClimb.set(ControlMode.PercentOutput, -.95);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, -.9);
            } else{
                Map.climber.leftClimb.set(ControlMode.PercentOutput, 0);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, 0);
            }
    }


    


}