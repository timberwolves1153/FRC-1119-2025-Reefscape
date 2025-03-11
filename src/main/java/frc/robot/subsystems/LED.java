package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

import java.util.Map;

import com.revrobotics.ColorSensorV3.LEDPulseFrequency;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private AddressableLEDBufferView leftView;
    private AddressableLEDBufferView rightView;
    private Distance ledDensity;

    private LEDPattern defaultPattern;
    private LEDPattern intakePatternBase;
    private LEDPattern intakePattern;
    private LEDPattern outtakePatternBase;
    private LEDPattern outtakePattern;
    private Map<Number, Color> intakeMaskSteps;
    private LEDPattern elevatorLowPattern;
    private LEDPattern elevatorLowPatternBase;
    private LEDPattern elevatorMidPattern;
    private LEDPattern elevatorMidPatternBase;
    private LEDPattern elevatorHighPattern;
    private LEDPattern elevatorHighPatternBase;
    private Map<Number, Color> elevatorMaskSteps;

    private LEDPattern limelightLeftPattern;
    private LEDPattern limelightRightPattern;
    private LEDPattern linedUpPattern;

    public LED() {
        led = new AddressableLED(0);
        ledDensity = Meters.of(0.02);
        led.setLength(60);
        ledBuffer = new AddressableLEDBuffer(60);
        leftView = ledBuffer.createView(0, 29);
        rightView = ledBuffer.createView(30, 59).reversed();

        /* Patterns V1 */

        // defaultPattern = LEDPattern.gradient(
        //     LEDPattern.GradientType.kDiscontinuous, Color.kWhite, Color.kWhiteSmoke)
        //     .scrollAtRelativeSpeed(Percent.per(Second).of(25));

        // intakePattern = LEDPattern.solid(Color.kGreen);

        // outtakePattern = LEDPattern.solid(Color.kRed);

        // elevatorLowPattern = LEDPattern.gradient(
        //     LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed)
        //     .scrollAtRelativeSpeed(Percent.per(Second).of(50));

        // elevatorMidPattern = LEDPattern.gradient(
        //     LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed)
        //     .scrollAtRelativeSpeed(Percent.per(Second).of(125));

        // elevatorHighPattern = LEDPattern.gradient(
        //     LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed)
        //     .scrollAtRelativeSpeed(Percent.per(Second).of(200));

        // limelightLeftPattern = LEDPattern.steps(Map.of(0.0, Color.kBlue, 0.5, Color.kWhite));

        // limelightRightPattern = LEDPattern.steps(Map.of(0.0, Color.kWhite, 0.5, Color.kOrangeRed));

        // linedUpPattern = LEDPattern.rainbow(255, 128);

        /* Patterns V2 */

        defaultPattern = LEDPattern.gradient(
            LEDPattern.GradientType.kDiscontinuous, Color.kWhite, Color.kWhiteSmoke)
            .scrollAtRelativeSpeed(Percent.per(Second).of(25));

        intakeMaskSteps = Map.of(0, Color.kWhite, 0.66, Color.kBlack);

        intakePatternBase = LEDPattern.solid(Color.kGreen);
        intakePattern = intakePatternBase.mask(LEDPattern.steps(intakeMaskSteps)
            .scrollAtRelativeSpeed(Percent.per(Second).of(75)));

        outtakePatternBase = LEDPattern.solid(Color.kRed);
        outtakePattern = outtakePatternBase.mask(LEDPattern.steps(intakeMaskSteps)
            .scrollAtRelativeSpeed(Percent.per(Second).of(-75)));

        elevatorMaskSteps = Map.of(0, Color.kWhite, 0.33, Color.kBlack);

        elevatorLowPatternBase = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed);
        elevatorLowPattern = elevatorLowPatternBase.mask(LEDPattern.steps(elevatorMaskSteps))
            .scrollAtRelativeSpeed(Percent.per(Second).of(100));

        elevatorMidPatternBase = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed);
        elevatorMidPattern = elevatorMidPatternBase.mask(LEDPattern.steps(elevatorMaskSteps))
            .scrollAtRelativeSpeed(Percent.per(Second).of(150));

        elevatorHighPatternBase = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kOrangeRed);
        elevatorHighPattern = elevatorHighPatternBase.mask(LEDPattern.steps(elevatorMaskSteps))
            .scrollAtRelativeSpeed(Percent.per(Second).of(200));

        led.start();
    }

    public void runDefaultPattern() {
        defaultPattern.applyTo(leftView);
        defaultPattern.applyTo(rightView);
    }

    public void runIntakePattern() {
        intakePattern.applyTo(leftView);
        intakePattern.applyTo(rightView);
    }

    public void runOuttakePattern() {
        outtakePattern.applyTo(leftView);
        outtakePattern.applyTo(rightView);
    }

    public void runElevatorLowPattern() {
        elevatorLowPattern.applyTo(leftView);
        elevatorLowPattern.applyTo(rightView);
    }

    public void runElevatorMidPattern() {
        elevatorMidPattern.applyTo(leftView);
        elevatorMidPattern.applyTo(rightView);
    }

    public void runElevatorHighPattern() {
        elevatorHighPattern.applyTo(leftView);
        elevatorHighPattern.applyTo(rightView);
    }

    public void runLimelightLeftPattern() {
        limelightLeftPattern.applyTo(leftView);
        limelightLeftPattern.applyTo(rightView);
    }

    public void runLimelightRightPattern() {
        limelightRightPattern.applyTo(leftView);
        limelightRightPattern.applyTo(rightView);
    }

    public void runLinedUpPattern() {
        linedUpPattern.applyTo(leftView);
        linedUpPattern.applyTo(rightView);
    }

    @Override
    public void periodic() {
        led.setData(ledBuffer);
    }
}