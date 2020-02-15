package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;


/**
 * Map
 */
public class Map {
    // Where all controllers and gamepads are stored
    public static class driveTrain{
        // Drive Train Motors
        public static CANSparkMax lf = new CANSparkMax(0, MotorType.kBrushless);
        public static CANSparkMax lr = new CANSparkMax(1, MotorType.kBrushless);
        public static CANSparkMax rf = new CANSparkMax(2, MotorType.kBrushless);
        public static CANSparkMax rr = new CANSparkMax(3, MotorType.kBrushless);

        //Drive Train Encoders
        public static CANEncoder lfEncoder = lf.getEncoder();
        public static CANEncoder lrEncoder = lr.getEncoder();
        public static CANEncoder rfEncoder = rf.getEncoder();
        public static CANEncoder rrEncoder = rr.getEncoder();        

    }
    public static class Controllers{
        // Controllers
        public static Joystick driverLeft = new Joystick(0);
        public static Joystick driverRight = new Joystick(1);
        public static XboxController xbox = new XboxController(2); // this is experimental
    }
    public static class Turret{
        // Turret Motors
        public static class motors{
            // Rotation motors
            public static TalonSRX rotation = new TalonSRX(4);
            // Launching Motors
            public static TalonSRX launcher = new TalonSRX(5);
            public static TalonSRX follower = new TalonSRX(6);
        }
        public static class controllers{
            public static int initiation = 5; // Raw button number for launching initiation
            public static int launch = 6; // Raw button number for launching ball
        }
    }
        public static class Cartridge{
        // Cartridge / Loading-Arm motors (and pneumatics)
        public static Solenoid RightPiston = new Solenoid (1,1);
        public static Solenoid LeftPiston = new Solenoid (2,2);
        public static TalonSRX ArmRoller = new TalonSRX(7);
        public static TalonSRX Conveyor1 = new TalonSRX(8);
        public static TalonSRX Conveyor2 = new TalonSRX(9);
        public static TalonSRX Conveyor3 = new TalonSRX(10);
        public static class Sensors{
            public static DigitalInput fullSensor = new DigitalInput(0);
            
        }
        public static class controllers{
            public static int zeroInOnTarget = 1; // Raw button number for lining up with closest ball
            public static int grabTarget = 2; // Raw button number for picking up ball
        }

        public static class PIDcontrol{
            //PID variables
            double kP = 0;
            double kI = 0;
            double kD = 0;
        }


    }
}