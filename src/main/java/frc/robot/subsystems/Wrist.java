package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorSetpoints;
// import frc.robot.subsystems.Elevator.Setpoint;

public class Wrist extends SubsystemBase {
    // private double wristCurrentTarget = ElevatorSetpoints.kFeederStation;
    
    // private SparkFlex wristMotor;

    // private static final SparkFlexConfig wristConfig = new SparkFlexConfig();

    // private SparkClosedLoopController wristController = wristMotor.getClosedLoopController();
    // private RelativeEncoder wristEncoder = wristMotor.getEncoder();

    // public Wrist() {
    //     wristMotor = new SparkFlex(0, MotorType.kBrushless); //TODO: set ID

    //     wristConfig.closedLoop
    //       .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    //       .p(0.1)
    //       .outputRange(-1, 1);

    //     wristMotor.configure(
    //         wristConfig,
    //         ResetMode.kResetSafeParameters,
    //         PersistMode.kPersistParameters
    //     );

    //     wristEncoder.setPosition(0);
    // }

    // private void wristToSetpoint() {
    //     wristController.setReference(wristCurrentTarget, ControlType.kMAXMotionPositionControl);
    // }

    // public Command setWristSetpoint(Setpoint setpoint) {
    //     return this.runOnce(
    //         () -> {
    //             switch (setpoint) {
    //                 case kFeederStation:
    //                     wristCurrentTarget = ElevatorSetpoints.kFeederStation;
    //                     break;
    //                 case L1:
    //                     wristCurrentTarget = ElevatorSetpoints.kL1;
    //                     break;
    //                 case L2:
    //                     wristCurrentTarget = ElevatorSetpoints.kL2;
    //                     break;
    //                 case L3:
    //                     wristCurrentTarget = ElevatorSetpoints.kL3;
    //                     break;
    //             }
    //         }
    //     );
    // }

    // @Override
    // public void periodic() {
    //     wristToSetpoint();

    //     SmartDashboard.putNumber("Wrist Target Position", wristCurrentTarget);
    //     SmartDashboard.putNumber("Wrist True Position", wristEncoder.getPosition());
    // } 
}