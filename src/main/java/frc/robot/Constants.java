// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static class DrivetrainConfig {
		public static final int frontRightMotorPort = 0;
		public static final int frontLeftMotorPort = 1;
		public static final int backRightMotorPort = 2;
		public static final int backLeftMotorPort = 3;

		public static final double driveSpeed = 0.2;
		public static final double turnSpeed = 0.2;

		public static final double axisThreshold = 0.1;

		public static final SPI.Port navxPort = SPI.Port.kMXP;

		public static final double wheelDiameterMeters = Units.inchesToMeters(4);
		public static final double gearRatio = 7.5833; // 3 motor rotations to 1 wheel rotation
		public static final double rotationToDistanceConversion = (Math.PI * wheelDiameterMeters) / gearRatio;
	}

	public static class IntakeConfig {
		public static final int[] solenoidPorts = { 0, 1 };
		public static final Value defultSolenoidState = Value.kReverse;
		public static final int motorPort = 2;
		public static final double motorSpeed = 0.5;
	}

	public static class ShooterConfig {
		public static final int motorPort = 0;
		public static final double motorOutputPercent = 0.5;
	}

	public static class VariableHoodConfig {
		public static final int motorPort = 0;
		public static final double kP = 0;
		public static final double kI = 0;
		public static final double kD = 0;
		public static final double motorGearRatio = 3;
	}
}
