package frc.robot.Vision;

import java.util.ArrayList;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

/**
 * Pixy
 */
public class Pixy {
    private static Pixy2 pixy = Pixy2.createInstance(new SPILink());
    private static int maxBlocks = 10;

    public static void init() {
        pixy = Pixy2.createInstance(new SPILink()); // Creates a new Pixy2 camera using SPILink
        pixy.init(); // Initializes the camera and prepares to send/receive data
        pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
        pixy.setLED(200, 30, 255); // Sets the RGB LED to purple
    }

    public static void setLED(int r, int g, int b) {
        pixy.setLED(r, g, b);
    }

    public static boolean isTarget() {
        int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, maxBlocks);
        if (blockCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int getBiggestBlock() {
        int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, maxBlocks);
        if (blockCount <= 0) {
            return 0;
        }
        ArrayList<Block> blocks = pixy.getCCC().getBlocks();
        Block largestBlock = null;
        for (Block block : blocks) {
            if (largestBlock == null) {
                largestBlock = block;
            } else if (block.getWidth() > largestBlock.getWidth()) {
                largestBlock = block;
            }
        }
        return largestBlock.getIndex();
    }

    public static double getX(int index) {
        // returns a value from -1 to 1
        // Default ranges from 0 to 315
        int pre = pixy.getCCC().getBlocks().get(index).getX();
        // int pre = getBiggestBlock().getX();
        return (pre - 157.5) / 157.5;
    }
}