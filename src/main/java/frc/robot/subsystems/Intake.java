// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
  private final DoubleSolenoid solenoid;
  private final CANSparkMax motor;
  private boolean enabled, extended, prevExtended;

  public Intake() {
    solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConfig.solenoidPorts[0],
        IntakeConfig.solenoidPorts[1]);
    solenoid.set(Value.kOff);

    motor = new CANSparkMax(IntakeConfig.motorPort, MotorType.kBrushless);
    motor.restoreFactoryDefaults();

    enabled = extended = false;
  }

  /*
   * public void extend() {
   * solenoid.set(Value.kForward);
   * }
   * 
   * public void retract() {
   * solenoid.set(Value.kReverse);
   * }
   * 
   * public void toggle() {
   * solenoid.set(solenoid.get() == Value.kForward ? Value.kReverse :
   * Value.kForward);
   * }
   * 
   * public Value getSolenoidValue() {
   * return solenoid.get();
   * }
   */

  public void enable() {
    this.enabled = true;
  }

  public void disable() {
    this.enabled = false;
    solenoid.set(Value.kOff);
    motor.stopMotor();
  }

  public void toggleEnabled() {
    this.enabled = !enabled;
  }

  @Override
  public void periodic() {
    if (!enabled) {
      return;
    }

    if (prevExtended != extended) {
      if (extended) {
        solenoid.set(Value.kForward);
      } else {
        solenoid.set(Value.kReverse);
      }
    }

    if (extended) {
      motor.set(IntakeConfig.motorSpeed);
    }
  }

  public void extend() {
    this.prevExtended = this.extended;
    extended = true;
  }

  public void retract() {
    this.prevExtended = this.extended;
    extended = false;
  }

  public void toggle() {
    this.prevExtended = this.extended;
    extended = extended ? false : true;
  }
}
