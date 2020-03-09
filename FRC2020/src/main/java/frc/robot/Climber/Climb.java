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
                lift(1);
            } else if (Map.Controllers.driverRight.getRawButton(Map.climber.lower)){
                lift(-1);
            } else{
                Map.climber.leftClimb.set(ControlMode.PercentOutput, 0);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, 0);
            }
    }
    private static double maxError = 300;
    private static double maxHight = 3800;
    private static void lift(double pwr){
        double leftP = Map.climber.leftClimb.getSelectedSensorPosition();
        double rightP = Map.climber.rightClimb.getSelectedSensorPosition();
        double error = leftP-rightP;
        double absError = Math.abs(error);
        if(absError>maxError){
            absError = maxError;
        }
        double speedRatio = 1-(absError/maxError);
        if(pwr>0){
            if(leftP<maxHight){
                Map.climber.leftClimb.set(ControlMode.PercentOutput, ((speedRatio-((error/maxError))))*pwr);
            }
            if(rightP<maxHight){
                Map.climber.rightClimb.set(ControlMode.PercentOutput,((speedRatio+((error/maxError))))*pwr);
            }
        }else{
            if(leftP>0){
                Map.climber.leftClimb.set(ControlMode.PercentOutput, ((speedRatio-((error/maxError))))*pwr);
            }
            if(rightP>0){
                Map.climber.rightClimb.set(ControlMode.PercentOutput,((speedRatio+((error/maxError))))*pwr);
            }
        }
    }

    


}