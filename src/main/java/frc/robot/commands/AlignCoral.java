package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;

public class AlignCoral extends SequentialCommandGroup {

  private Intake intake;

  public AlignCoral(Intake intake) {

    this.intake = intake;
    addRequirements(intake);

    addCommands(
        Commands.runOnce(() -> intake.intakeOutSlow()),
        new WaitCommand(0.1),
        Commands.runOnce(() -> intake.intakeInSlow()),
        new WaitCommand(0.1),
        Commands.runOnce(() -> intake.intakeOutSlow()),
        new WaitCommand(0.1),
        Commands.runOnce(() -> intake.intakeInSlow()),
        new WaitCommand(0.1),
        Commands.runOnce(() -> intake.intakeIdle())
    );
  }
}