package frc.robot.Climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

public class Climb{

    public static void Init(){

        Map.climber.leftClimb.setNeutralMode(NeutralMode.Brake);
        Map.climber.rightClimb.setNeutralMode(NeutralMode.Brake);

        Map.climber.leftClimb.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.climber.rightClimb.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        
        Map.climber.rightClimb.setInverted(true);
        Map.climber.leftClimb.setInverted(false);
        Map.climber.leftClimb.setSensorPhase(true);

        Map.climber.leftClimb.getSensorCollection().setQuadraturePosition(0, 10);
        Map.climber.rightClimb.getSensorCollection().setQuadraturePosition(0, 10);
    }
    public static void display(){
        SmartDashboard.putNumber("RightClimber Position: ", Map.climber.rightClimb.getSelectedSensorPosition());
        SmartDashboard.putNumber("LeftClimber Position: ", Map.climber.leftClimb.getSelectedSensorPosition());
        SmartDashboard.putNumber("LEFT Velocity", Map.climber.leftClimb.getSelectedSensorVelocity());
        SmartDashboard.putNumber("RIGHT Velocity", Map.climber.rightClimb.getSelectedSensorVelocity());
    }
    public static void run(){
            if (Map.Controllers.driverRight.getRawButton(Map.climber.lift)){
                lift();
            } else if (Map.Controllers.driverRight.getRawButton(Map.climber.lower)){
                drop();
            } else{
                Map.climber.leftClimb.set(ControlMode.PercentOutput, 0);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, 0);
            }
    }
    private static double maxError = 300;
    private static void drop(){
        double error = Map.climber.leftClimb.getSelectedSensorPosition()-Map.climber.rightClimb.getSelectedSensorPosition();
        double absError = Math.abs(error);
        if(absError>maxError){
            absError = maxError;
        }
        double speedRatio = 1-(absError/maxError);
        Map.climber.leftClimb.set(ControlMode.PercentOutput, -(speedRatio-((error/maxError))));
        Map.climber.rightClimb.set(ControlMode.PercentOutput,-(speedRatio+((error/maxError))));
    }
    private static void lift(){
        double error = Map.climber.leftClimb.getSelectedSensorPosition()-Map.climber.rightClimb.getSelectedSensorPosition();
        double absError = Math.abs(error);
        if(absError>maxError){
            absError = maxError;
        }
        double speedRatio = 1-(absError/maxError);
        Map.climber.leftClimb.set(ControlMode.PercentOutput, (speedRatio-((error/maxError))));
        Map.climber.rightClimb.set(ControlMode.PercentOutput,(speedRatio+((error/maxError))));
    }

    


}