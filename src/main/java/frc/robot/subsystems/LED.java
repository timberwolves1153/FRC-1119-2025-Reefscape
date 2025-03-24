package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

import java.util.Map;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private AddressableLEDBufferView leftView;
    private AddressableLEDBufferView rightView;
    private Distance ledDensity;

    private LEDPattern defaultPattern;

    private Map<Number, Color> intakeMaskSteps;
    private LEDPattern intakePatternBase;
    private LEDPattern intakePattern;
    private LEDPattern outtakePatternBase;
    private LEDPattern outtakePattern;

    private Map<Number, Color> elevatorMaskSteps;
    private LEDPattern elevatorLowPattern;
    private LEDPattern elevatorLowPatternBase;
    private LEDPattern elevatorMidPattern;
    private LEDPattern elevatorMidPatternBase;
    private LEDPattern elevatorHighPattern;
    private LEDPattern elevatorHighPatternBase;

    private Map<Number, Color> climbMaskSteps;
    private LEDPattern climbUpPatternBase;
    private LEDPattern climbUpPattern;
    private LEDPattern climbDownPatternBase;
    private LEDPattern climbDownPattern;

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

        /* Patterns */

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

        climbMaskSteps = Map.of(0, Color.kWhite, 0.125, Color.kBlack);

        climbUpPatternBase = LEDPattern.solid(Color.kYellow);
        climbUpPattern = climbUpPatternBase.mask(LEDPattern.steps(climbMaskSteps))
            .scrollAtRelativeSpeed(Percent.per(Second).of(50));

        climbDownPatternBase = LEDPattern.rainbow(255, 128);
        climbDownPattern = climbDownPatternBase.mask(LEDPattern.steps(climbMaskSteps))
            .scrollAtRelativeSpeed(Percent.per(Second).of(150));

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

    public void runClimbUpPattern() {
        climbUpPattern.applyTo(leftView);
        climbUpPattern.applyTo(rightView);
    }

    public void runClimbDownPattern() {
        climbDownPattern.applyTo(leftView);
        climbDownPattern.applyTo(rightView);
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