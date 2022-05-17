package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.DrivetrainConfig;

public class Controls {
    XboxController driver;
    XboxController operator;

    private double threshold = DrivetrainConfig.axisThreshold;
    // if a value from a joystick is less than this, it will return 0

    public Controls(XboxController driver, XboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }

    public double getDriveSpeed() {
        double joystickValue = driver.getLeftY();
        // joystickValue = Math.abs(joystickValue) > threshold ? 0 : joystickValue;
        // vv this will work better and is more compact vv
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold);

        return joystickValue * DrivetrainConfig.driveSpeed;
    }

    public double getTurnSpeed() {
        double joystickValue = driver.getRightX();
        // joystickValue = Math.abs(joystickValue) > threshold ? 0 : joystickValue;
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold);

        return joystickValue * DrivetrainConfig.driveSpeed;
    }
}
