// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {
  private CANSparkMax rightDrive, leftDrive, rightFollow, leftFollow;
  private double speedFactor = 0.6;

  private double leftPosition, rightPosition, heading; // heading in degrees
  private AHRS navx;
  private DifferentialDriveOdometry odometry;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightDrive = new CANSparkMax(DrivetrainConfig.frontRightMotorPort, MotorType.kBrushless);
    rightDrive.restoreFactoryDefaults();

    leftDrive = new CANSparkMax(DrivetrainConfig.frontLeftMotorPort, MotorType.kBrushless);
    leftDrive.restoreFactoryDefaults();

    rightFollow = new CANSparkMax(DrivetrainConfig.backRightMotorPort, MotorType.kBrushless);
    rightFollow.restoreFactoryDefaults();
    rightFollow.follow(rightDrive);

    leftFollow = new CANSparkMax(DrivetrainConfig.backLeftMotorPort, MotorType.kBrushless);
    leftFollow.restoreFactoryDefaults();
    leftFollow.follow(leftDrive);

    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
    navx = new AHRS(DrivetrainConfig.navxPort);
  }

  @Override
  public void periodic() {
    this.getPose();
    // from updateInputs
    leftPosition = rotationsToDistance(leftDrive.getEncoder().getPosition());
    rightPosition = rotationsToDistance(rightDrive.getEncoder().getPosition());

    updateHeading();

    odometry.update(navx.getRotation2d(), leftPosition, rightPosition);
  }

  public void boostSpeed() {
    this.speedFactor = 1.0;
  }

  public void slowSpeed() {
    this.speedFactor = 0.2;
  }

  public void normalSpeed() {
    this.speedFactor = 0.6;
  }

  public void customSpeed(double speed) {
    this.speedFactor = speed;
  }

  public void stop() {
    this.speedFactor = 0.0;
  }

  public double getSpeed() {
    return this.speedFactor;
  }

  /**
   * Drives the robot using arcade drive.
   * 
   * @param forwardSpeed speed of forward movement, or negative for backwards
   * @param turnSpeed    speed of rotation (positive is clockwise)
   */
  public void arcadeDrive(double forwardSpeed, double turnSpeed) {
    double maximum = Math.max(Math.abs(forwardSpeed), Math.abs(turnSpeed));
    double leftSpeed = forwardSpeed >= 0
        ? (turnSpeed >= 0 ? maximum : forwardSpeed + turnSpeed)
        : (turnSpeed >= 0 ? forwardSpeed + turnSpeed : -maximum);
    double rightSpeed = forwardSpeed >= 0
        ? (turnSpeed >= 0 ? forwardSpeed - turnSpeed : maximum)
        : (turnSpeed >= 0 ? -maximum : forwardSpeed - turnSpeed);

    maximum = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
    this.leftDrive.set(leftSpeed * speedFactor / maximum);
    this.rightDrive.set(rightSpeed * speedFactor / maximum);
  }

  /**
   * Drives the robot using tank drive.
   * 
   * @param leftSpeed  speed of left motors
   * @param rightSpeed speed of right motors
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    double maximum = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
    this.leftDrive.set(leftSpeed * speedFactor / maximum);
    this.rightDrive.set(rightSpeed * speedFactor / maximum);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    this.periodic();
  }

  public void resetOdometry(Pose2d pose) {
      resetEncoders();
      Rotation2d rotation = navx.getRotation2d();
      odometry.resetPosition(pose, rotation);
  }

  public void resetEncoders() {
    leftDrive.getEncoder().setPosition(0);
    rightDrive.getEncoder().setPosition(0);
  }

  public void zeroHeading() {
      this.navx.reset();
      updateHeading();
  }

  public void updateHeading() {
    double headingDegrees = navx.getAngle();

    heading = headingDegrees;
  }

  public double getHeading() {
    return heading;
  }

  public Pose2d getPose() {
    System.out.println(odometry.getPoseMeters());
    return odometry.getPoseMeters();
  }

  public double getDistanceFromStart() {
    Transform2d displacement = this.getPose().minus(new Pose2d(0, 0, new Rotation2d(0)));
    double distance = Math.sqrt(displacement.getX() * displacement.getX() + displacement.getY() * displacement.getY());
    
    //System.out.println("Distance from start: " + distance);
    return distance;
}

  public double rotationsToDistance(double rotations) {
    return rotations * Math.PI * DrivetrainConfig.wheelDiameterMeters / DrivetrainConfig.gearRatio;
  }
}
