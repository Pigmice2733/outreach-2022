// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
  private final TalonSRX motor;
  boolean enabled;

  public void enable() { this.setEnabled(true); }
  public void disable() { this.setEnabled(false); }
  public void toggleEnabled() { this.setEnabled(!this.enabled); }
  public void setEnabled(boolean enabled) { this.enabled = enabled; }

  public Shooter() {
    motor = new TalonSRX(ShooterConfig.motorPort);
  }

  @Override
  public void periodic() {
    if (enabled) {
      setMotorSpeed(ShooterConfig.motorOutputPercent);
    }
  }

  public void setMotorSpeed(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }
}
