package frc.robot.Vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * LimeLight
 */
public class LimeLight {
    private static NetworkTable table = NetworkTableInstance
    .getDefault().getTable("limelight"); // might have to be updated in methods

    public static double getX(){
        // Returns the X axis of the target
        return table.getEntry("tx").getDouble(0.0)/27; //translated degrees -27 to 27 to a value between -1 and 1
    }
    public static double getY(){
        // Returns the Y axis of the target
        return table.getEntry("ty").getDouble(0.0)/27; //translated degrees -27 to 27 to a value between -1 and 1
    }
    public static boolean isTarget(){
        // Returns true if there is a target in frame
        String tv = table.getEntry("tv").toString();
        if(tv.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    public static class LED{
        public static void on(){
            // Turns LEDs on
            table.getEntry("ledMode").setNumber(3);
        }
        public static void off(){
            // Turns LEDs off
            table.getEntry("ledMode").setNumber(1);
        }
        public static void flash(){
            // Quickly flashes LEDs
            table.getEntry("ledMode").setNumber(2);
        }
    }
}