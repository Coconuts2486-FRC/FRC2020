package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Map
 */
public class Map {
    // Where all controllers and gamepads are stored
    public static class driveTrain{
        // Drive Train Motors

    }
    public static class Controllers{
        // Controllers
        public static Joystick driverLeft = new Joystick(0);
        public static Joystick driverRight = new Joystick(1);
    }
    public static class Turret{
        // Turret Motors
        // Rotation motors
        //public static TalonSRX rotation = new TalonSRX(1);

        // Launching Motors
        //public static TalonSRX launcher = new TalonSRX(1);
        //public static TalonSRX follower = new TalonSRX(1);
    }
    public static class Cartridge{
        // Cartridge / Loading-Arm motors (and pneumatics)

    }
}