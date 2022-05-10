// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private CANSparkMax rightDrive, leftDrive, rightFollow, leftFollow;
  private double speedFactor;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightDrive = new CANSparkMax(0, MotorType.kBrushless);
    rightDrive.restoreFactoryDefaults();

    leftDrive = new CANSparkMax(1, MotorType.kBrushless);
    leftDrive.restoreFactoryDefaults();

    rightFollow = new CANSparkMax(2, MotorType.kBrushless);
    rightFollow.restoreFactoryDefaults();
    rightFollow.follow(rightDrive);

    leftFollow = new CANSparkMax(3, MotorType.kBrushless);
    leftFollow.restoreFactoryDefaults();
    leftFollow.follow(leftDrive);

    speedFactor = 0.6;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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
}
