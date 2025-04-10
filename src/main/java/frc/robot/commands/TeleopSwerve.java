package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;

public class TeleopSwerve extends Command {
    private Swerve s_Swerve;
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier fieldCentricSup;
    private BooleanSupplier overrideSup;

    public TeleopSwerve(
        Swerve s_Swerve,
        DoubleSupplier translationSup,
        DoubleSupplier strafeSup,
        DoubleSupplier rotationSup,
        BooleanSupplier fieldCentricSup,
        BooleanSupplier overrideSup
    ) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.fieldCentricSup = fieldCentricSup;
        this.overrideSup = overrideSup;
    }

    @Override
    public void execute() {
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);

        if (overrideSup.getAsBoolean() == false) {
        s_Swerve.drive(
            new Translation2d(
                translationVal,
                strafeVal
            ).times(Constants.Swerve.maxSpeed * 0.15),
            rotationVal * Constants.Swerve.maxAngularVelocity * 0.25,
            fieldCentricSup.getAsBoolean(),
            true
        );

        } else {
            s_Swerve.drive(
                new Translation2d(
                    0,
                    0
                ),
                0,
                fieldCentricSup.getAsBoolean(),
                true
            );

        }
    }
}