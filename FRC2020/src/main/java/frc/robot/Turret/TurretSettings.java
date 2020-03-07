package frc.robot.Turret;

/**
 * TurretSettings
 */
public class TurretSettings {
    public static boolean automaticModeActive = true;
    public static boolean turretUsingConveyors = false;
    public static class rotation{
        public static class general{
            public static int ticksInrevolution = 4120; // needs to be changed to actual number
            public static int maxDeg = 135;
            public static int minDeg = 0;
        }
        public static class manual{
            public static boolean manualGoTo = false;

            public static double manuelSmallAdjusterDivision = 6; // how much the small adjuster is divided by
            public static double manuelLargeAdjusterDivision = 2; // how much the large adjuster is divided by

            public static double errorRange = 1; // in degrees
            public static double slopePoint = 5;
            public static double topSpeed = 0.5;

            public static double manuelAngle = 90;
        }
        public static class automatic{
            public static double slopePoint = 1; // Point at which turret uses slope formula to turn
            public static double trackingerror = 0.05; // X axis distange from 0 error range
        }
    }

    public static class launching{
        public static class general{
            public static double maxVelocity = 110000;
        }
        public static class manual{
            public static boolean manualLauncherInitiated = false;
            public static boolean manualLaunch = false;
            public static boolean testLaunch = false;
            public static int manuelVelocity = 10000; // set to whatever base velocity should be
            public static int manuelVelocityChange = 5000; // The amount that velocity is adjusted when POV is pressed
        }
        public static class automatic{
            public static boolean automaticLauncherInitiated = false;
            public static double launchingSpeedAddition = 50000; // additional power added to launching function
            public static double maxLaunchingSpeedError = 1000; // maximum velocity error for launcher
            public static double baseLaunchSpeed = 10000; // init speed (so that its close to target launch speed)
            public static double launchDrop = 3000;

            public static double velocity = 0;
            public static boolean velocityIsRunning = false;
        }
    }
}