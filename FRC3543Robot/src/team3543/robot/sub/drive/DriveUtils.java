package team3543.robot.sub.drive;

import com.team254.frc2017.Constants;

public class DriveUtils {

    public static double rotationsToInches(double rotations) {
        return rotations * (DriveConstants.kDriveWheelDiameterInches * Math.PI);
    }

    public static double rpmToInchesPerSecond(double rpm) {
        return rotationsToInches(rpm) / 60;
    }

    public static double inchesToRotations(double inches) {
        return inches / (DriveConstants.kDriveWheelDiameterInches * Math.PI);
    }

    public static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60;
    }

}
