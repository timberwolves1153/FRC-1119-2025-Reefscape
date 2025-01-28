package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    // private SparkMax intakeMotor;

    // private SparkMaxConfig intakeConfig;

    // public Intake() {
    //     intakeMotor = new SparkMax(0, MotorType.kBrushless);

    //     intakeConfig = new SparkMaxConfig();

    //     intakeConfig.smartCurrentLimit(40).idleMode(IdleMode.kBrake);

    //     intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // }

    // public void intakeIn() {
    //     intakeMotor.setVoltage(0);
    // }

    // public void intakeOut() {
    //     intakeMotor.setVoltage(0);
    // }

    // public void intakeStop() {
    //     intakeMotor.setVoltage(0);
    // }
}
