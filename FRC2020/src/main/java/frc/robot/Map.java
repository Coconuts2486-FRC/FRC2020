package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Map
 */
public class Map {
    // Where all controllers and gamepads are stored
    public static class driveTrain {
        // Drive Train Motors
        public static CANSparkMax lf = new CANSparkMax(1, MotorType.kBrushless);
        public static CANSparkMax lr = new CANSparkMax(2, MotorType.kBrushless);
        public static CANSparkMax rf = new CANSparkMax(3, MotorType.kBrushless);
        public static CANSparkMax rr = new CANSparkMax(4, MotorType.kBrushless);

        // Drive Train Encoders
        public static CANEncoder lfEncoder = lf.getEncoder();
        public static CANEncoder lrEncoder = lr.getEncoder();
        public static CANEncoder rfEncoder = rf.getEncoder();
        public static CANEncoder rrEncoder = rr.getEncoder();

    }

    public static class Controllers {
        // Controllers
        public static Joystick driverLeft = new Joystick(0);
        public static Joystick driverRight = new Joystick(1);
        public static XboxController xbox = new XboxController(2); // this is experimental
    }

    public static class Turret {
        // Turret Motors
        public static class motors {
            // Rotation motors
            public static TalonSRX rotation = new TalonSRX(5);
            // Launching Motors
            public static TalonSRX launcher = new TalonSRX(9);
            public static TalonSRX follower = new TalonSRX(7);
        }

        public static class controllers {
            public static int initiation = 5; // Raw button number for launching initiation
            public static int launch = 6; // Raw button number for launching ball
            public static int manuelRotateLarge = 0;
            public static int manuelRotateSmall = 2;
            public static int manuelLauncherAddPower = 4;
            public static int manuelLauncherSubtractPower = 2;
            public static int manuelMode = 10; // button that changes remote into manuel
            public static int manuelEncoderZeroer = 7;
            public static int manuelSetAngle = 11; // Sets an angle for manuel mode
            public static int manuelGoToAngle = 12; // Turns turret to preset angle
            public static int manuelEncoderDisable = 9;
            public static int LimeLightLED = 1;
            public static int outtake = 8;
        }
    }

    public static class Cartridge {
        // Cartridge / Loading-Arm motors (and pneumatics)
        public static Solenoid RightPiston = new Solenoid(1, 4);
        // private static Solenoid test = new Solenoid(4);
        // public static Solenoid LeftPiston = new Solenoid (2,2);
        public static TalonSRX ArmRoller = new TalonSRX(2);
        public static TalonSRX Conveyor1 = new TalonSRX(4);
        public static TalonSRX Conveyor2 = new TalonSRX(3);
        public static TalonSRX Conveyor3 = new TalonSRX(8);


        public static class Sensors {

            public static DigitalInput fullSensor = new DigitalInput(0);

        }

        public static class controllers {
            public static int zeroInOnTarget = 1; // Raw button number for lining up with closest ball
            public static int grabTarget = 2; // Raw button number for picking up ball
            public static int outtake = 5;
            public static int intake = 1;
        }

        public static class PIDcontrol {
            // PID variables
            double kP = 0;
            double kI = 0;
            double kD = 0;
        }
    }
		public static class ColorWheel{


            public static Solenoid SensorLift1 = new Solenoid(1,5);
            public static TalonSRX ColorSpinner = new TalonSRX(11);
            public final static I2C.Port i2cPort = I2C.Port.kOnboard;
            public static ColorSensorV3 sensor = new ColorSensorV3(i2cPort);



        }
		

        public static class ColorWheelDefs {
            // piston,motor, and color sensor used in color wheel manipulator

            public static Solenoid SensorLift = new Solenoid(1, 5);
            public static TalonSRX Color = new TalonSRX(11);
            public final static I2C.Port i2cPort = I2C.Port.kOnboard;
            public static ColorSensorV3 sensor = new ColorSensorV3(i2cPort);

        }

    }

    

