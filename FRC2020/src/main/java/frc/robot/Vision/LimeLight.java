package frc.robot.Vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * LimeLight
 */
public class LimeLight {
    private static NetworkTable table = NetworkTableInstance
    .getDefault().getTable("limelight"); // might have to be updated in methods

    public static double getX(){
        // Returns the X axis of the target
        String tx = table.getEntry("tx0").toString();
        return Double.parseDouble(tx);
    }
    public static double getY(){
        // Returns the Y axis of the target
        String ty = table.getEntry("ty0").toString();
        return Double.parseDouble(ty);
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