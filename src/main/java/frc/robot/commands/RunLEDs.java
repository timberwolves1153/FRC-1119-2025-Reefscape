package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Configs.Climber;
import frc.robot.subsystems.LED;

public class RunLEDs extends Command {
    private LED LED;

    private BooleanSupplier POVUpSup;
    private BooleanSupplier POVDownSup;

    private DoubleSupplier ClimberPositionSup;
    private DoubleSupplier ElevatorPositionSup;

    public RunLEDs(
        LED LED,

        BooleanSupplier POVUpSup,
        BooleanSupplier POVDownSup,

        DoubleSupplier ClimberPositionSup,
        DoubleSupplier ElevatorPositionSup
    ) {
        this.LED = LED;
        addRequirements(LED);

        this.POVUpSup = POVUpSup;
        this.POVDownSup = POVDownSup;

        this.ClimberPositionSup = ClimberPositionSup;
        this.ElevatorPositionSup = ElevatorPositionSup;
    }
    
    @Override
    public void execute() {
        boolean POVUp = POVUpSup.getAsBoolean();
        boolean POVDown = POVDownSup.getAsBoolean();
        
        double climberPosition = ClimberPositionSup.getAsDouble();
        double elevatorPosition = ElevatorPositionSup.getAsDouble();

        if (POVUp == true) {
            LED.runOuttakePattern();
        } else if (POVDown == true) {
            LED.runIntakePattern();
        } else if (climberPosition > 200) {
            LED.runClimbUpPattern();
        } else if (climberPosition > 100) {
            LED.runClimbDownPattern();
        } else if (elevatorPosition > -1) {
            LED.runDefaultPattern();
        } else if (elevatorPosition > -10) {
            LED.runElevatorLowPattern();
        } else if (elevatorPosition > -20) {
            LED.runElevatorMidPattern();
        } else {
            LED.runElevatorHighPattern();
        }
    }
}