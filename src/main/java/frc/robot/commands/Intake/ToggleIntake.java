package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ToggleIntake extends CommandBase {
    private final Intake intake;

    public ToggleIntake(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        intake.enable();
        intake.toggleExtension();
    }

    @Override
    public void end(boolean interrupted) {
        if (intake.isEnabled()) {
            intake.disable();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
