package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Map;
import frc.robot.Vision.LimeLight;

/**
 * Targeting
 */
public class Targeting {
    // Customizable Settings
    // Turret Rotation Settings
    private static double slopePoint = 1; // Point at which turret uses slope formula to turn
    private static double trackingerror = 0.05; // X axis distange from 0 error range
    // Launching Settings
    public static double launchingSpeedAddition = 90000; // additional power added to launching function
    private static double maxLaunchingSpeedError = 1000; // maximum velocity error for launcher
    private static double baseLaunchSpeed = 50000; // init speed (so that its close to target launch speed)

    // Method Settings
    private static boolean track = false; // tracks target as long as true
    private static boolean targetZeroedIn = false; // true if target is within trackingerror
    private static boolean maintainLaunchingSpeed = false; // keeps launcher loop up to speed as long as true
    private static boolean launcherUpToSpeed = false; // nofifies if launcher is at wanted velocity
    private static boolean maintainBaseLaunchSpeed = false; // maintains a basic launching velocity while true
    private static double launchDrop = 1000;
    /*
     * Example data: 180 Degrees of rotation y and x (on LimeLight) range from -1 to
     * 1
     */

    public static void initilize() {
        // Starts tracking process
        track = true;
        maintainBaseLaunchSpeed = true;
        // Sets launching motors at base speed
        Thread setBaseLaunchingSpeed = new Thread() {
            public void run() {
                setBaseLaunchingSpeed();
            }
        };
        // Gets turret into position to launch
        Thread followingThread = new Thread() {
            public void run() {
                trackTarget();
            }
        };
        // Gets turret up to speed and ready to launch
        Thread firingThread = new Thread() {
            public void run() {
                initLauncher();
            }
        };
        setBaseLaunchingSpeed.start();
        followingThread.start();
        firingThread.start();
    }

    public static boolean readyToFire() {
        if (launcherUpToSpeed && targetZeroedIn) {
            return true;
        } else {
            return false;
        }
    }

    public static void stop() {
        // stops targeting
        track = false;
        targetZeroedIn = false;
        maintainLaunchingSpeed = false;
        launcherUpToSpeed = false;
        maintainBaseLaunchSpeed = false;
        LimeLight.LED.off();
        TurretMotion.Rotation.turn(0);
        TurretMotion.Launcher.setPercentSpeed(0);
    }

    private static void setBaseLaunchingSpeed() {
        maintainBaseLaunchSpeed = true;
        while (maintainBaseLaunchSpeed && track) {
            TurretMotion.Launcher.setVelocity(baseLaunchSpeed);
        }
    }

    private static void findTarget() {
        // Finds target
        targetZeroedIn = false;
        if (!LimeLight.isTarget()) {
            while (!LimeLight.isTarget() && track) {
                while (!LimeLight.isTarget() && TurretMotion.Rotation.getDegrees() < 180 && track) {
                    TurretMotion.Rotation.turn(0.5);
                }
                while (!LimeLight.isTarget() && TurretMotion.Rotation.getDegrees() > 0 && track) {
                    TurretMotion.Rotation.turn(-0.5);
                }
            }
        }
    }

    public static void launch() {
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.5);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 1);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 1);
    }
    public static void autoLaunch(){
        double initVelocity = TurretMotion.Launcher.getVelocity();
        double shutoffVelocity = initVelocity-launchDrop;
        while((TurretMotion.Launcher.getVelocity()>shutoffVelocity)&&Map.Controllers.xbox.getRawButton(Map.Turret.controllers.launch)){
            launch();
        }
    }

    public static void stopLaunch() {
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
    }

    private static void initLauncher() {
        // maintains a launch speed (will need to be in a thread)
        maintainBaseLaunchSpeed = false; // overrides base launch code
        maintainLaunchingSpeed = true;
        double motorSpeed = TurretMotion.Launcher.getVelocity();
        double targetSpeed = calculateLaunchSpeed();
        double abserror = Math.abs(targetSpeed - motorSpeed);
        while (maintainLaunchingSpeed && track) {
            motorSpeed = TurretMotion.Launcher.getVelocity();
            targetSpeed = calculateLaunchSpeed();
            abserror = Math.abs(targetSpeed - motorSpeed);
            if (abserror > maxLaunchingSpeedError) {
                launcherUpToSpeed = false;
                TurretMotion.Launcher.setVelocity(targetSpeed);// might be minus error (too tired to think rn)
            } else {
                launcherUpToSpeed = true;
            }
        }
    }

    private static double calculateLaunchSpeed() {
        // Finds the speed of the flywheen needed to hit the target
        double y = LimeLight.getY();
        double output = (y) + launchingSpeedAddition; // replace 'y' with custom function
        return output;
    }

    private static void trackTarget() {
        // Lines turret to center of target
        double position = LimeLight.getX();
        double error = Math.abs(position);
        LimeLight.LED.on();
        while (track) {
            if (LimeLight.isTarget() && track) {
                position = LimeLight.getX();
                error = Math.abs(position);
                while (error > trackingerror && track) {
                    targetZeroedIn = false;
                    position = LimeLight.getX();
                    error = Math.abs(position);
                    if (error > slopePoint) {
                        if (position > 0) {
                            TurretMotion.Rotation.turn(-1);
                        } else {
                            TurretMotion.Rotation.turn(1);
                        }
                    } else {
                        //TurretMotion.Rotation.turn(0 - (position / slopePoint));
                        TurretMotion.Rotation.turn((position / slopePoint));
                    }
                }
                targetZeroedIn = true;
                TurretMotion.Rotation.turn(0);
            } else {
                findTarget();
            }
        }
    }
}