package frc.robot.Turret;

import frc.robot.Vision.LimeLight;

/**
 * Targeting
 */
public class Targeting {
    // Customizable Settings
        // Turret Rotation Settings
            private static double slopePoint = 0.1; // Point at which turret uses slope formula to turn
            private static double trackingerror = 0.001; // X axis distange from 0 error range
        // Launching Settings
            public static double launchingSpeedAddition = 0; // additional power added to launching function
            private static double maxLaunchingSpeedError = 20; // maximum velocity error for launcher
            private static double baseLaunchSpeed = 0.5; // init speed (so that its close to target launch speed)

    // Method Settings
        private static boolean track = false; // tracks target as long as true
        private static boolean targetZeroedIn = false; // true if target is within trackingerror 
        private static boolean maintainLaunchingSpeed = false; // keeps launcher loop up to speed as long as true
        private static boolean launcherUpToSpeed = false; // nofifies if launcher is at wanted velocity
        private static boolean maintainBaseLaunchSpeed = false; // maintains a basic launching velocity while true
    /* 
    Example data:
    180 Degrees of rotation
    y and x (on LimeLight) range from -1 to 1
    */
    public static void initilize(){
        // Sets launching motors at base speed
        Thread setBaseLaunchingSpeed = new Thread(){
            public void run(){
                setBaseLaunchingSpeed();
            }
        };
        // Gets turret into position to launch
        Thread followingThread = new Thread(){
            public void run() {
                trackTarget();
            }
        };
        // Gets turret up to speed and ready to launch
        Thread firingThread = new Thread(){
            public void run(){
                initLauncher();
            }
        };
        setBaseLaunchingSpeed.start();
        followingThread.start();
        firingThread.start();
    }
    public static void setBaseLaunchingSpeed(){
        while(maintainBaseLaunchSpeed){
            TurretMotion.Launcher.setVelocity(baseLaunchSpeed);
        }
    }
    private static void findTarget() {
        // Finds target
        if(!LimeLight.isTarget()){
            while(!LimeLight.isTarget()){
                if(TurretMotion.Rotation.getDegrees()>=90){
                    while(TurretMotion.Rotation.getDegrees()<180||(!LimeLight.isTarget())){
                        TurretMotion.Rotation.turn(1);
                    }
                }else{
                    while(TurretMotion.Rotation.getDegrees()>0||(!LimeLight.isTarget())){
                        TurretMotion.Rotation.turn(-1);
                    }
                }
            }
        }
    }
    public static void launch(){
        if(launcherUpToSpeed&&targetZeroedIn){
            // load ball into chamber (Owens code)
        }
    }
    private static void initLauncher(){
        //maintains a launch speed (will need to be in a thread)
        maintainBaseLaunchSpeed = false; //overrides base launch code
        double motorSpeed = 0; // changed to get data from 'Map.Turret' class
        double targetSpeed = calculateLaunchSpeed();
        double abserror = Math.abs(targetSpeed-motorSpeed);
        double error = targetSpeed-motorSpeed;
        while(maintainLaunchingSpeed){
            motorSpeed = 0; // changed to get data from 'Map.Turret' class
            targetSpeed = calculateLaunchSpeed();
            abserror = Math.abs(targetSpeed-motorSpeed);
            if(abserror>maxLaunchingSpeedError){
                TurretMotion.Launcher.setVelocity(targetSpeed+error);// might be minus error (too tired to think rn)
            }else{
                launcherUpToSpeed = true;
                //launch
            }
        }
    }
    private static double calculateLaunchSpeed(){
        // Finds the speed of the flywheen needed to hit the target
        double y = LimeLight.getY();
        double output = (y)+launchingSpeedAddition; // replace 'y' with custom function
        return output;
    }
    private static void trackTarget(){
        // Lines turret to center of target
        double position = LimeLight.getX();
        double error = Math.abs(position);
        track = true;
        LimeLight.LED.on();
        while(track){
            if(LimeLight.isTarget()&&track){
                position = LimeLight.getX();
                error = Math.abs(position);
                while(error>trackingerror&&track){
                    position = LimeLight.getX();
                    error = Math.abs(position);
                    if(error>slopePoint){
                        if(position>0){
                            TurretMotion.Rotation.turn(-1);
                        }else{
                            TurretMotion.Rotation.turn(1);
                        }
                    }else{
                        TurretMotion.Rotation.turn(0-(position/slopePoint));
                    }
                }
                targetZeroedIn = true;
                TurretMotion.Rotation.turn(0);
            }else{
                findTarget();
            }
        }
        LimeLight.LED.off();
    }
}