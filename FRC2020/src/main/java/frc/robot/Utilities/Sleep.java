package frc.robot.Utilities;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Sleep
 */
public class Sleep {

    public static void delay(long time){
        Stopwatch sw = Stopwatch.createStarted();
        while(sw.elapsed(TimeUnit.MILLISECONDS) <= time);
    }
}