// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RPMPController;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
  private final TalonSRX motor;
  private final RPMPController rpmController;
  boolean enabled;

  private final double sensorUnitsPerRPM = 75 / 512;

  public Shooter() {
    motor = new TalonSRX(ShooterConfig.motorPort);
    rpmController = new RPMPController(ShooterConfig.motorKP, 0);
  }

  @Override
  public void periodic() {
    if (enabled) {
      updateSpeed(rpmController.update(
          motor.getSelectedSensorVelocity() / sensorUnitsPerRPM));
    }
  }

  public void enable() {
    this.setEnabled(true);
  }

  public void disable() {
    this.setEnabled(false);
  }

  public void toggleEnabled() {
    this.setEnabled(!this.enabled);
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void updateSpeed(double rpm) {
    double percentRpm = MathUtil.clamp(rpm / ShooterConfig.maxRpm, -1.0, 1.0);
    motor.set(ControlMode.PercentOutput, percentRpm);
  }

  public void setMotorSpeed(double rpm) {
    rpmController.setTargetRPM(rpm);
  }
}
