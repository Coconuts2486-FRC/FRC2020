package frc.robot.Turret;

import frc.robot.Vision.LimeLight;

/**
 * Targeting
 */
public class Targeting {
    public static boolean track = false; // tracks target as long as true
    private static double slopePoint = 0.1;
    private static double trackingerror = 0.001;

    public static double launchingSpeed = 0;
    /* 
    Example data:
    180 Degrees of rotation
    y and x (on LimeLight) range from -1 to 1
    */
    public static void initilize(){
        // Gets turret into position to launch
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
    private static double calculateLaunchSpeed(){
        // Finds the speed of the flywheen needed to hit the target
        double y = LimeLight.getY();
        double output = (y)+launchingSpeed; // replace 'y' with custom function
        return output;
    }
    private static void zeroInOnTarget(){
        // Lines turret to center of target
        double position = LimeLight.getX();
        double error = Math.abs(position);
        track = true;
        while(track){
            position = LimeLight.getX();
            error = Math.abs(position);
            while(error>trackingerror){
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