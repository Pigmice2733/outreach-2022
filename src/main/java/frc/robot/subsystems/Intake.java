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
    this.prevExtended = this.extended;
    extended = true;
  }

  public void retract() {
    this.prevExtended = this.extended;
    extended = false;
  }

  public void toggleExtension() {
    this.prevExtended = this.extended;
    extended = extended ? false : true;
  }
}
