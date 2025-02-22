package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class Intake extends SubsystemBase {
    private SparkMax intakeMotor;

    public Intake() {
        intakeMotor = new SparkMax(56, MotorType.kBrushless);

        intakeMotor.configure(
            Configs.Intake.intakeConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    public void intakeIn() {
        intakeMotor.setVoltage(-10);
    }

    public void intakeOut() {
        intakeMotor.setVoltage(10);
    }

    public void intakeStop() {
        intakeMotor.setVoltage(0);
    }
}
