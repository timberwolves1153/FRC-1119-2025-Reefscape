package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class Algae extends SubsystemBase {
    private SparkMax pivotMotor;
    private SparkFlex intakeMotor;

    public Algae() {
        pivotMotor = new SparkMax(41, MotorType.kBrushless);
        intakeMotor = new SparkFlex(42, MotorType.kBrushless);

        pivotMotor.configure(
            Configs.Algae.pivotConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        pivotMotor.configure(
            Configs.Algae.pivotConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    public void pivotUp() {
        pivotMotor.setVoltage(3);
    }

    public void pivotDown() {
        pivotMotor.setVoltage(-3);
    }

    public void pivotStop() {
        pivotMotor.setVoltage(0);
    }

    public void algaeIntake() {
        intakeMotor.setVoltage(-3);
    }

    public void algaeOuttake() {
        intakeMotor.setVoltage(3);
    }

    public void algaeStop() {
        intakeMotor.setVoltage(0);
    }
}