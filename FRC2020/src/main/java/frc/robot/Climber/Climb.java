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
    private static boolean climberActivated = false;
    public static void run(){
            if (Map.Controllers.driverRight.getRawButton(Map.climber.lift)&&climberActivated){
                lift(0.5);
            }
            if(Map.Controllers.driverRight.getRawButtonPressed(Map.climber.unlock)){
                if(!climberActivated){
                    climberActivated=true;
                }else{
                    climberActivated=false;
                }
            }
            if(climberActivated){
                Map.climber.lock.set(false);
            }else{
                Map.climber.lock.set(true);
            }
    }
    private static double maxError = 500;
    private static double maxHight = 80000;
    private static void lift(double pwr){
        double leftP = Map.climber.leftClimb.getSelectedSensorPosition();
        double rightP = Map.climber.rightClimb.getSelectedSensorPosition();
        double error = leftP-rightP;
        double absError = Math.abs(error);
        if(absError>maxError){
            absError = maxError;
        }
        double speedSetting = 1-(absError/maxError);
        double averageHight = (leftP+rightP)/2;
            if(averageHight<maxHight){
                Map.climber.leftClimb.set(ControlMode.PercentOutput, ((speedSetting-(error/maxError))));
                Map.climber.rightClimb.set(ControlMode.PercentOutput,((speedSetting+(error/maxError))));
            }else{
                Map.climber.leftClimb.set(ControlMode.PercentOutput, 0);
                Map.climber.rightClimb.set(ControlMode.PercentOutput,0);
            }
    }

    


}