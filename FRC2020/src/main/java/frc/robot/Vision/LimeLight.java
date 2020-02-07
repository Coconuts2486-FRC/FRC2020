package frc.robot.Vision;

/**
 * LimeLight
 */
public class LimeLight {

    public static double getX(){
        // Returns the X axis of the target
        return 0;
    }
    public static double getY(){
        // Returns the Y axis of the target
        return 0;
    }
    public static boolean isTarget(){
        // Returns true if there is a target in frame
        return false;
    }

    static class LED{
        public static void on(){
            // Turns LEDs on
        }
        public static void off(){
            // Turns LEDs off
        }
        public static void flash(){
            // Quickly flashes LEDs
        }
    }
}