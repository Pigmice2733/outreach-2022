// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleToLongFunction;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConfig;
import frc.robot.Constants.ShooterConfig;

public class Intake extends SubsystemBase {
  private final DoubleSolenoid solenoid;

  public Intake() {
    solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConfig.solenoidPorts[0], IntakeConfig.solenoidPorts[1]);
  }

  public void Extend() {
    solenoid.set(Value.kForward);
  } 

  public void Retract() {
    solenoid.set(Value.kReverse);
  }

  public void Toggle() {
    solenoid.set(solenoid.get() == Value.kForward ? Value.kReverse : Value.kForward);
  }

  @Override
  public void periodic() {

  }
}
