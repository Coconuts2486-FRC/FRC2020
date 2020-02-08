package frc.robot;

public static class DriveTrain{

    SmartDashboard.putNumber("Encoder Position fr", -rfEncoder.getPosition() * ticksToFeet);
    SmartDashboard.putNumber("Encoder Position fl", -lfEncoder.getPosition() * ticksToFeet);
    SmartDashboard.putNumber("Encoder Position br", -rrEncoder.getPosition() * ticksToFeet);
    SmartDashboard.putNumber("Encoder Position bl", -lrEncoder.getPosition() * ticksToFeet);
    


    // stores current joystick location in a variable
    final double leftX = driverLeft.getX();
    final double rightX = driverRight.getX();
    final double leftY = driverLeft.getY();
    final double rightY = driverRight.getY();
    // creates final movement variable for meccanum drive
    final double lf = leftY - leftX;
    final double rf = rightY + rightX;
    final double lr = leftY + leftX;
    final double rr = rightY - rightX;
    // returns the final movement variables

    lf.set(lf);
    lr.set(lr);
    rf.set(rf);
    rr.set(rr);

}

    

   