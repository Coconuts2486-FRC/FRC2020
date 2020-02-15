package frc.robot.Color_Wheel;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

/**
 * ColorSensor
 */

public class ColorSensor {
    private final static I2C.Port i2cPort = I2C.Port.kOnboard;
    private final static ColorSensor ColorSensor = new ColorSensor(i2cPort);

    public ColorSensor(Port i2cport) {
    }

    public static Color GetColor() {
        final Color detected_color = ColorSensor.GetColor();
        return detected_color;

    }
}