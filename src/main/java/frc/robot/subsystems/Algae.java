package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Algae extends SubsystemBase {
    // private SparkMax pivotMotor;
    // private SparkFlex intakeMotor;

    // SparkMaxConfig pivotConfig = new SparkMaxConfig();
    // SparkFlexConfig intakeConfig = new SparkFlexConfig();

    // public Algae() {
    //     pivotMotor = new SparkMax(0, MotorType.kBrushless);
    //     intakeMotor = new SparkFlex(0, MotorType.kBrushless); // TODO: set IDs

    //     pivotConfig
    //         .smartCurrentLimit(40)
    //         .idleMode(IdleMode.kBrake);
    //     pivotMotor.configure(
    //         pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters
    //     );

    //     intakeConfig
    //         .smartCurrentLimit(40)
    //         .idleMode(IdleMode.kBrake);
    //     pivotMotor.configure(
    //         pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters
    //     );
    // }

    // public void pivotUp() {
    //     pivotMotor.setVoltage(0);
    // }

    // public void pivotDown() {
    //     pivotMotor.setVoltage(0);
    // }

    // public void pivotStop() {
    //     pivotMotor.setVoltage(0);
    // }

    // public void algaeIntake() {
    //     intakeMotor.setVoltage(0);
    // }

    // public void algaeOuttake() {
    //     intakeMotor.setVoltage(0);
    // }

    // public void algaeStop() {
    //     intakeMotor.setVoltage(0);
    // }
}