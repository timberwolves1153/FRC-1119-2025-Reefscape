package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.ClimbSetpoints;

public class Climber extends SubsystemBase {
    public enum ClimbSetpoint {
        Reset,
        Stow,
        Ready,
        Climb
    }

    private SparkFlex climberMotor;

    private SparkClosedLoopController climbController;
    private RelativeEncoder climbEncoder;
    
    private double climbCurrentTarget;

    public Climber() {
        climberMotor = new SparkFlex(61, MotorType.kBrushless);

        climbController = climberMotor.getClosedLoopController();
        climbEncoder = climberMotor.getEncoder();

        climbCurrentTarget = ClimbSetpoints.Stow;

        climberMotor.configure(
            Configs.Climber.climberConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    public void climberUp() {
        climberMotor.setVoltage(12);
    }

    public void climberDown() {
        climberMotor.setVoltage(-12);
    }

    public void climberStop() {
        climberMotor.setVoltage(0);
    }

    public void climberToSetpoint() {
            climbController.setReference(climbCurrentTarget,
            ControlType.kMAXMotionPositionControl
        );
    }

    public Command setClimberSetpoint(ClimbSetpoint setpoint) {
        return this.runOnce(
            () -> {
                switch (setpoint) {
                    case Reset:
                        climbCurrentTarget = ClimbSetpoints.Reset;
                        break;
                    case Stow:
                        climbCurrentTarget = ClimbSetpoints.Stow;
                        break;
                    case Ready:
                        climbCurrentTarget = ClimbSetpoints.Ready;
                        break;
                    case Climb:
                        climbCurrentTarget = ClimbSetpoints.Climb;
                        break;
                    default:
                        climbCurrentTarget = ClimbSetpoints.Stow;
                        break;
                }
            }
        );
    }
    @Override
    public void periodic() {
        // climberToSetpoint();

        SmartDashboard.putNumber("Climber Target Position", climbCurrentTarget);
        SmartDashboard.putNumber("Climber True Position", climbEncoder.getPosition());
    }
}