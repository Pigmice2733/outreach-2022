// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VariableHoodConfig;

public class VariableHood extends SubsystemBase {
  private final CANSparkMax motor;
  private final PIDController controller;
  private double targetAngle = 0;

  // Shuffleboard prints
  private final ShuffleboardTab variableHoodTab;
  public final NetworkTableEntry enabledEntry;
  public final NetworkTableEntry targetRotationEntry;
  public final NetworkTableEntry currentRotationEntry;

  // Enabled functions
  private boolean enabled;
  public void enable() { this.setEnabled(true); }
  public void disable() { this.setEnabled(false); }
  public void toggleEnabled() { this.setEnabled(!this.enabled); }
  public void setEnabled(boolean enabled) { this.enabled = enabled; enabledEntry.setBoolean(enabled); }

  public VariableHood() {
    motor = new CANSparkMax(VariableHoodConfig.motorPort, MotorType.kBrushless);
    motor.restoreFactoryDefaults();

    controller = new PIDController(VariableHoodConfig.kP, VariableHoodConfig.kI, VariableHoodConfig.kD);
    controller.setSetpoint(targetAngle);

    // Initialize shuffleboard entries
    variableHoodTab = Shuffleboard.getTab("Variable Hood");
    enabledEntry = variableHoodTab.add("Enabled", enabled).getEntry();
    targetRotationEntry = variableHoodTab.add("CurrentRotation", 0).getEntry();
    currentRotationEntry = variableHoodTab.add("Target Rotation", targetAngle).getEntry();
  }

  @Override
  public void periodic() {
    if (!enabled)
      return;

    double currentMotorPosition = getCurrentRotation();
    double targetMotorSpeed = controller.calculate(currentMotorPosition);
    setMotorOutput(targetMotorSpeed);
  }

  // Set the hood angle in degrees
  public void setTargetAngle(double targetAngle) {
    this.targetAngle = targetAngle;
    controller.setSetpoint(targetAngle);
    targetRotationEntry.setDouble(targetAngle);
  }

  // Directly set the output of the motor (% output)
  void setMotorOutput(double speed) {
    motor.set(speed);
  }

  // Reset the encoder on the motor to 0
  void resetEncoder() {
    motor.getEncoder().setPosition(0);
  }

  // Return the current rotation in degrees
  double getCurrentRotation() {
    double rotation = motor.getEncoder().getPosition() / VariableHoodConfig.motorGearRatio;
    currentRotationEntry.setDouble(rotation);
    return rotation;
  }
}
