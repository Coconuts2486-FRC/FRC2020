package frc.robot.Turret;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Utilities.Sleep;
import frc.robot.Vision.LimeLight;

/**
 * Targeting
 */
public class Targeting {
    // Method Settings
    private static boolean track = false; // tracks target as long as true
    private static boolean targetZeroedIn = false; // true if target is within trackingerror
    private static boolean maintainLaunchingSpeed = false; // keeps launcher loop up to speed as long as true
    private static boolean launcherUpToSpeed = false; // nofifies if launcher is at wanted velocity
    private static boolean maintainBaseLaunchSpeed = false; // maintains a basic launching velocity while true
    //public static boolean isFiring = false;

    public static void initilize() {
        // Starts tracking process
        track = true;
        TurretSettings.launching.automatic.automaticLauncherInitiated = true;
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
        TurretMotion.Launcher.setPercentSpeed(0);
        track = false;
        targetZeroedIn = false;
        maintainLaunchingSpeed = false;
        launcherUpToSpeed = false;
        maintainBaseLaunchSpeed = false;
        TurretSettings.launching.manual.manualLaunch = false;
        TurretSettings.launching.automatic.automaticLauncherInitiated = false;
        LimeLight.LED.off();
        TurretMotion.Rotation.turn(0);
        TurretMotion.Launcher.setPercentSpeed(0);
        stopLaunch();
    }

    private static void setBaseLaunchingSpeed() {
        maintainBaseLaunchSpeed = true;
        while (maintainBaseLaunchSpeed && track) {
            TurretMotion.Launcher.setVelocity(TurretSettings.launching.automatic.baseLaunchSpeed);
        }
    }

    private static void findTarget() {
        // Finds target
        targetZeroedIn = false;
        if (!LimeLight.isTarget()) {
            while (!LimeLight.isTarget() && track) {
                while (!LimeLight.isTarget() && TurretMotion.Rotation.getDegrees() < TurretSettings.rotation.general.maxDeg && track) {
                    TurretMotion.Rotation.turn(TurretSettings.rotation.manual.topSpeed);
                }
                while (!LimeLight.isTarget() && TurretMotion.Rotation.getDegrees() > TurretSettings.rotation.general.minDeg && track) {
                    TurretMotion.Rotation.turn(-TurretSettings.rotation.manual.topSpeed);
                }
            }
        }
    }

    public static void launch() {
        TurretSettings.turretUsingConveyors = true;
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0.5);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 1);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 1);
    }

    public static void autoLaunch() {
        Thread thread = new Thread(){
            public void run(){
                double initVelocity = TurretMotion.Launcher.getVelocity();
                double shutoffVelocity = initVelocity - TurretSettings.launching.automatic.launchDrop;
                while ((TurretMotion.Launcher.getVelocity() > shutoffVelocity)
                     && Map.Controllers.xbox.getRawButton(Map.Turret.controllers.launch) && track) {
                    launch();
                }
                stopLaunch();
            }
        };
        thread.start();
    }

    public static void learningLauncher() {
        Thread thread = new Thread(){
            public void run(){
                TurretSettings.launching.manual.manualLaunch = true;
                LimeLight.Snapshot.start();
                double initVelocity = TurretMotion.Launcher.getVelocity();
                double shutoffVelocity = initVelocity - TurretSettings.launching.automatic.launchDrop;
                while ((TurretMotion.Launcher.getVelocity() > shutoffVelocity) && TurretSettings.launching.manual.manualLaunch) {
                    launch();
                    if (Map.Controllers.xbox.getRawButtonPressed(Map.Turret.controllers.launch)
                            || Map.Controllers.xbox.getRawButton(Map.Turret.controllers.outtake)) {
                            TurretSettings.launching.manual.manualLaunch = false;
                    }
                }
                LimeLight.Snapshot.stop();
                stopLaunch();
                TurretDisplay.learningDisplay(TurretSettings.launching.manual.manuelVelocity, initVelocity, LimeLight.getY());
                Sleep.delay(100);
            }
        };
        thread.start();
    }
    public static void stopLaunch() {
        /*
        Map.Cartridge.Conveyor1.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor2.set(ControlMode.PercentOutput, 0);
        Map.Cartridge.Conveyor3.set(ControlMode.PercentOutput, 0);
        */
        TurretSettings.turretUsingConveyors = false;
        TurretSettings.launching.manual.manualLaunch = false;
    }

    private static void initLauncher() {
        // maintains a launch speed (will need to be in a thread)
        maintainBaseLaunchSpeed = false; // overrides base launch code
        maintainLaunchingSpeed = true;
        double motorSpeed = TurretMotion.Launcher.getVelocity();
        double targetSpeed = calculateLaunchSpeed();
        //double targetSpeed = setVelocityToRealVelocity(calculateLaunchSpeed());
        double abserror = Math.abs(targetSpeed - motorSpeed);
        while (maintainLaunchingSpeed && track) {
            motorSpeed = TurretMotion.Launcher.getVelocity();
            targetSpeed = calculateLaunchSpeed();
            //targetSpeed = setVelocityToRealVelocity(calculateLaunchSpeed());
            abserror = Math.abs(targetSpeed - motorSpeed);
            if (abserror > TurretSettings.launching.automatic.maxLaunchingSpeedError) {
                launcherUpToSpeed = false;
                TurretMotion.Launcher.setVelocity(targetSpeed);
            } else {
                launcherUpToSpeed = true;
            }
        }
    }

    private static double calculateLaunchSpeed() {
        // Finds the speed of the flywheen needed to hit the target
        double y = LimeLight.getY();
        double a = -748474;
        double b = 1.1795e6;
        double c = -495639;
        double d = 62999.9;
        double output = ((a*Math.pow(y, 4))+(b*Math.pow(y, 3))+(c*Math.pow(y, 2))+(d)) + TurretSettings.launching.automatic.launchingSpeedAddition; // replace 'y' with custom function
        SmartDashboard.putNumber("Calculated Velocity", output);
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
                while (error > TurretSettings.rotation.automatic.trackingerror && track) {
                    targetZeroedIn = false;
                    position = LimeLight.getX();
                    error = Math.abs(position);
                    if (error > TurretSettings.rotation.automatic.slopePoint) {
                        if (position > 0) {
                            TurretMotion.Rotation.turn(-TurretSettings.rotation.automatic.topSpeed);
                        } else {
                            TurretMotion.Rotation.turn(TurretSettings.rotation.automatic.topSpeed);
                        }
                    } else {
                        //TurretMotion.Rotation.turn(0 - (position / slopePoint));
                        TurretMotion.Rotation.turn((position / TurretSettings.rotation.automatic.slopePoint)*TurretSettings.rotation.automatic.topSpeed);
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