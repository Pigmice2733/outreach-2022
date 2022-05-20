// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Intake.ToggleIntake;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic
 * should actually be handled in the {@link Robot} periodic methods
 * (other than the scheduler calls). Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be
 * declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final Drivetrain drivetrain;
  private final Intake intake;
  private final Shooter shooter;

  private final Controls controls;

  private XboxController driver;
  private XboxController operator;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // drivetrain = new Drivetrain();
    intake = new Intake();
    shooter = new Shooter();

    // Initalize controls and configure buttons
    driver = new XboxController(0);
    operator = new XboxController(1);
    controls = new Controls(driver, operator);
    configureButtonBindings(driver, operator);

    // drivetrain.setDefaultCommand(new ArcadeDriveCommand(drivetrain,
    // controls::getDriveSpeed, controls::getTurnSpeed));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link JoystickButton}.
   */
  private void configureButtonBindings(XboxController driver, XboxController operator) {
    // ALL CONTORLS ARE TEMPORARY UNTIL WE MEET WITH DRIVETEAM

    // Slow when driver A is held down
    /*
     * new JoystickButton(driver, Button.kA.value)
     * .whenPressed(drivetrain::slowSpeed)
     * .whenReleased(drivetrain::normalSpeed);
     * 
     * // Boost speed when Y is held down
     * new JoystickButton(driver, Button.kY.value)
     * .whenPressed(drivetrain::boostSpeed)
     * .whenReleased(drivetrain::normalSpeed);
     */

    // toggle intake with operator A
    new JoystickButton(operator, Button.kA.value)
        .whenPressed(new ToggleIntake(intake));

    // disable intake with operator B
    new JoystickButton(operator, Button.kB.value)
        .whenPressed(intake::disable);

    // toggle shooter with operator X
    new JoystickButton(operator, Button.kX.value)
        .whenPressed(shooter::enable);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
