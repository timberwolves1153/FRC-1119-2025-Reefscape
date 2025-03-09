package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

import java.util.Map;

import com.revrobotics.ColorSensorV3.LEDPulseFrequency;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private Distance ledDensity;

    private LEDPattern defaultPattern;
    private LEDPattern intakePattern;
    private LEDPattern elevatorLowPattern;
    private LEDPattern elevatorMidPattern;
    private LEDPattern elevatorHighPattern;

    private LEDPattern limelightLeftPattern;
    private LEDPattern limelightRightPattern;
    private LEDPattern linedUpPattern;

    public LED() {
        led = new AddressableLED(5);
        ledDensity = Meters.of(0.02);
        ledBuffer = new AddressableLEDBuffer(60);
        led.setLength(60);

        /* Patterns */
    
        defaultPattern = LEDPattern.gradient(
            LEDPattern.GradientType.kDiscontinuous, Color.kWhite, Color.kWhiteSmoke)
            .scrollAtRelativeSpeed(Percent.per(Second).of(25));

        intakePattern = LEDPattern.steps(Map.of(0.0, Color.kBlue, 0.5, Color.kOrange));

        elevatorLowPattern = LEDPattern.gradient(
            LEDPattern.GradientType.kDiscontinuous, Color.kBlue, Color.kOrange)
            .scrollAtRelativeSpeed(Percent.per(Second).of(50));

        elevatorMidPattern = LEDPattern.gradient(
            LEDPattern.GradientType.kDiscontinuous, Color.kBlue, Color.kOrange)
            .scrollAtRelativeSpeed(Percent.per(Second).of(125));

        elevatorHighPattern = LEDPattern.gradient(
            LEDPattern.GradientType.kDiscontinuous, Color.kBlue, Color.kOrange)
            .scrollAtRelativeSpeed(Percent.per(Second).of(200));

        limelightLeftPattern = LEDPattern.steps(Map.of(0.0, Color.kBlue, 0.5, Color.kWhite));

        limelightRightPattern = LEDPattern.steps(Map.of(0.0, Color.kWhite, 0.5, Color.kOrange));

        linedUpPattern = LEDPattern.rainbow(255, 128);
    

        led.start();
    }

    public Command runDefaultPattern() {
        return run (() -> defaultPattern.applyTo(ledBuffer));
    }

    public Command runIntakePattern() {
        return run (() -> intakePattern.applyTo(ledBuffer));
    }

    public Command runElevatorLowPattern() {
        return run (() -> elevatorLowPattern.applyTo(ledBuffer));
    }

    public Command runElevatorMidPattern() {
        return run (() -> elevatorMidPattern.applyTo(ledBuffer));
    }

    public Command runElevatorHighPattern() {
        return run (() -> elevatorHighPattern.applyTo(ledBuffer));
    }

    public Command runLimelightLeftPattern() {
        return run (() -> limelightLeftPattern.applyTo(ledBuffer));
    }

    public Command runLimelightRightPattern() {
        return run (() -> limelightRightPattern.applyTo(ledBuffer));
    }

    public Command runLinedUpPattern() {
        return run (() -> linedUpPattern.applyTo(ledBuffer));
    }

    @Override
    public void periodic() {
        led.setData(ledBuffer);
    }
}