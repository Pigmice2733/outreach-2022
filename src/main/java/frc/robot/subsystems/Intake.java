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
  private final DoubleSolenoid leftPiston, rightPiston;
  private final CANSparkMax intakeMotor, indexerMotor;
  private boolean enabled, extended, prevExtended;
  /*
   * Extended means the intake system is extended,
   * which actually means the pistons are retracted.
   */

  public Intake() {
    leftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConfig.solenoidPorts[0],
        IntakeConfig.solenoidPorts[1]);
    leftPiston.set(Value.kOff);
    rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConfig.solenoidPorts[2],
        IntakeConfig.solenoidPorts[3]);
    rightPiston.set(Value.kOff);

    indexerMotor = new CANSparkMax(IntakeConfig.indexerPort, MotorType.kBrushless);
    indexerMotor.restoreFactoryDefaults();
    intakeMotor = new CANSparkMax(IntakeConfig.intakePort, MotorType.kBrushless);
    intakeMotor.restoreFactoryDefaults();

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

  @Override
  public void periodic() {
    if (!enabled) {
      return;
    }

    if (prevExtended != extended) {
      if (extended) {
        leftPiston.set(Value.kReverse);
        rightPiston.set(Value.kReverse);
      } else {
        leftPiston.set(Value.kForward);
        rightPiston.set(Value.kForward);
      }
      prevExtended = extended;
    }

    if (extended) {
      intakeMotor.set(IntakeConfig.intakeMotorSpeed);
      indexerMotor.set(IntakeConfig.indexerMotorSpeed);
    }
  }

  public void enable() {
    this.enabled = true;
  }

  public void disable() {
    this.enabled = false;
    leftPiston.set(Value.kOff);
    rightPiston.set(Value.kOff);
    intakeMotor.stopMotor();
    indexerMotor.stopMotor();
  }

  public void toggleEnabled() {
    if (this.enabled) {
      this.disable();
    } else {
      this.enable();
    }
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void extend() {
    this.extended = true;
  }

  public void retract() {
    this.extended = false;
  }

  public void toggleExtension() {
    this.extended = extended ? false : true;
  }

  /**
   * Returns true if the boolean values {@code prevExtended} and
   * {@code extended} are unequal; i.e., if the subsystem has been instructed
   * to extend or retract but has not yet acted to effect that instruction.
   * Returns false otherwise, while the extension system is at rest.
   */
  public boolean whileExtending() {
    return this.prevExtended != this.extended;
  }
}
