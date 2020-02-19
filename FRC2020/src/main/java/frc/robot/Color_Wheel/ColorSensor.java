package frc.robot.Color_Wheel;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;



/**
 * ColorSensor
 */

public class ColorSensor {
    private final static I2C.Port i2cPort = I2C.Port.kOnboard;
    private static ColorSensorV3 sensor = new ColorSensorV3(i2cPort);

    public ColorSensor(Port i2cport) {
    }

    public static double GetColor() {
        double red = sensor.getColor().red;
        return red;

    }
}