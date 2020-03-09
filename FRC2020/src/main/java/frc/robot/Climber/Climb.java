package frc.robot.Climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Utilities.Sleep;

public class Climb{

    public static void Init(){

        Map.climber.leftClimb.setNeutralMode(NeutralMode.Brake);
        Map.climber.rightClimb.setNeutralMode(NeutralMode.Brake);

        Map.climber.leftClimb.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.climber.rightClimb.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        /*
        Map.climber.leftClimb.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        Map.climber.rightClimb.configRemoteFeedbackFilter(Map.climber.leftClimb.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor,0,10);

        Map.climber.leftClimb.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0,10);
        Map.climber.leftClimb.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative,10);

        Map.climber.rightClimb.configSelectedFeedbackSensor(FeedbackDevice.SensorSum,0,10);
        Map.climber.rightClimb.configSelectedFeedbackCoefficient(0.5,0,10);

        Map.climber.rightClimb.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20,10);
        Map.climber.rightClimb.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20,10);
        Map.climber.leftClimb.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5,10);

        Map.climber.rightClimb.configMotionAcceleration(2000,10);
        Map.climber.rightClimb.configMotionCruiseVelocity(2000,10);

        Map.climber.leftClimb.configMotionAcceleration(2000,10);
        Map.climber.leftClimb.configMotionCruiseVelocity(2000,10);

        //Map.climber.leftClimb.follow(Map.climber.rightClimb);

        Map.climber.rightClimb.config_kP(0, 0.1,10);
        Map.climber.leftClimb.config_kP(0, 0.1, 10);
        */
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
    private static boolean top = false;
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
            /*
            if(Map.Controllers.driverRight.getRawButtonPressed(Map.climber.lift)){
                    top = true;
                    Map.climber.rightClimb.set(ControlMode.MotionMagic, 40000);
                    Map.climber.leftClimb.set(ControlMode.MotionMagic, 40000);
            }
            */
            
            if (Map.Controllers.driverRight.getRawButton(Map.climber.lift)){
                lift(0.5);
            } else if (Map.Controllers.driverRight.getRawButton(Map.climber.lower)){
                lift(-0.5);
            } else{
                Map.climber.leftClimb.set(ControlMode.PercentOutput, 0);
                Map.climber.rightClimb.set(ControlMode.PercentOutput, 0);
            }
            

    }
    private static double maxError = 300;
    private static void lift(double pwr){
        //Map.climber.leftClimb.set(ControlMode.MotionMagic, demand);
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