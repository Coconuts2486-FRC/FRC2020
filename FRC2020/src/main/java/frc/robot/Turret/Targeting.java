package frc.robot.Turret;

import frc.robot.Vision.LimeLight;

/**
 * Targeting
 */
public class Targeting {
    public static boolean track = false; // tracks target as long as true
    private static double slopePoint = 0.1; // Point at which turret uses slope formula to turn
    private static double trackingerror = 0.001; // X axis distange from 0 error range
    private static boolean targetZeroedIn = false; // true if target is within trackingerror 

    public static double launchingSpeed = 0;
    private static boolean maintainLaunchingSpeed = false;
    private static double maxLaunchingSpeedError = 20;
    private static boolean launcherUpToSpeed = false;
    private static double baseLaunchSpeed = 0.5; // init speed (so that its close to target launch speed)
    private static boolean maintainBaseLaunchSpeed = false;
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
                findTarget();
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
            setLauncherSpeed(baseLaunchSpeed);
        }
    }
    private static void findTarget() {
        // Finds target
        if(!LimeLight.isTarget()){
            while(!LimeLight.isTarget()){
                if(Turret.getDegrees()>=90){
                    while(Turret.getDegrees()<180||(!LimeLight.isTarget())){
                        Turret.turn(1);
                    }
                }else{
                    while(Turret.getDegrees()>0||(!LimeLight.isTarget())){
                        Turret.turn(-1);
                    }
                }
            }
        }
    }
    private static void launch(){
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
                setLauncherSpeed(
                    velocityToMotorSpeed(targetSpeed+error));// might be minus error (too tired to think rn)
            }else{
                launcherUpToSpeed = true;
                //launch
            }
        }
    }
    private static double velocityToMotorSpeed(double velocity){
        //translates velocity to a value between -1 and 1
        return 0;
    }
    private static void setLauncherSpeed(double speed){
        // Give speed to launching motors
    }
    private static double calculateLaunchSpeed(){
        // Finds the speed of the flywheen needed to hit the target
        double y = LimeLight.getY();
        double output = (y)+launchingSpeed; // replace 'y' with custom function
        return output;
    }
    private static void trackTarget(){
        // Lines turret to center of target
        double position = LimeLight.getX();
        double error = Math.abs(position);
        track = true;
        while(track){
            position = LimeLight.getX();
            error = Math.abs(position);
            while(error>trackingerror&&track){
                position = LimeLight.getX();
                error = Math.abs(position);
                if(error>slopePoint){
                     if(position>0){
                        Turret.turn(-1);
                     }else{
                        Turret.turn(1);
                    }
                }else{
                    Turret.turn(0-(position/slopePoint));
                 }
            }
            targetZeroedIn = true;
            Turret.turn(0);
        }
    }
    public static class Turret{
        private static double getPosition(){
            // Gets current position in motor ticks
            return 0;
        }
        public static double getDegrees(){
            // Gets current position in Degrees
            return 0;
        }
        public static void turn(double pwr){
            // Turns turret using pwr as input (-1 to 1)
        }
    }
}