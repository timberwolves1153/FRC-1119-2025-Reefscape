package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LED;

public class RunLEDs extends Command {
    private LED LED;

    private BooleanSupplier POVUpSup;
    private BooleanSupplier POVDownSup;
    private DoubleSupplier ElevatorPositionSup;

    public RunLEDs(
        LED LED,

        BooleanSupplier POVUpSup,
        BooleanSupplier POVDownSup,
        DoubleSupplier ElevatorPositionSup
    ) {
        this.LED = LED;
        addRequirements(LED);

        this.POVUpSup = POVUpSup;
        this.POVDownSup = POVDownSup;
        this.ElevatorPositionSup = ElevatorPositionSup;
    }
    
    @Override
    public void execute() {
        boolean POVUp = POVUpSup.getAsBoolean();
        boolean POVDown = POVDownSup.getAsBoolean();

        double elevatorPosition = ElevatorPositionSup.getAsDouble();

        if (POVUp == true) {
            LED.runOuttakePattern();
        } else if (POVDown == true) {
            LED.runIntakePattern();
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