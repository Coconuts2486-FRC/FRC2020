package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

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
        private final CANEncoder lfEncoder = lf.getEncoder();
        private final CANEncoder lrEncoder = lr.getEncoder();
        private final CANEncoder rfEncoder = rf.getEncoder();
        private final CANEncoder rrEncoder = rr.getEncoder();        

    }
    public static class Controllers{
        // Controllers
        public static Joystick driverLeft = new Joystick(0);
        public static Joystick driverRight = new Joystick(1);
        public static Joystick xbox = new Joystick(2);
    }
    public static class Turret{
        // Turret Motors
        // Rotation motors
        public static TalonSRX rotation = new TalonSRX(1);

        // Launching Motors
        public static TalonSRX launcher = new TalonSRX(1);
        public static TalonSRX follower = new TalonSRX(1);

        public static class Controllers{
            //Buttons and axis used for the Turret (for simplicity later in code)
        }
    }
    public static class Cartridge{
        // Cartridge / Loading-Arm motors (and pneumatics)

    }
}