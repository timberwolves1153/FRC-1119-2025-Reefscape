package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class Climber extends SubsystemBase {
    private SparkFlex climberMotor;

    public Climber() {
        climberMotor = new SparkFlex(61, MotorType.kBrushless);

        climberMotor.configure(
            Configs.Climber.climberConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    public void climberUp() {
        climberMotor.setVoltage(6);
    }

    public void climberDown() {
        climberMotor.setVoltage(-10);
    }

    public void climberStop() {
        climberMotor.setVoltage(0);
    }
}
