package org.stlpriory.robotics.commands;

import org.fest.assertions.Delta;
import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;
import org.strongback.components.Motor;
import org.strongback.drive.TankDrive;
import org.strongback.mock.Mock;

import static org.fest.assertions.Assertions.assertThat;

public class TimedDriveCommandTest {
    
    private final Delta TOLERANCE = Delta.delta(0.001);
    private final long START_TIME_MS = 1000;

    private Motor leftMotor;
    private Motor rightMotor;
    private TankDrive drive;

    private CommandTester tester;

    @Before
    public void beforeEach() {
        leftMotor = Mock.stoppedMotor();
        rightMotor = Mock.stoppedMotor();
        drive = new TankDrive(leftMotor, rightMotor);
    }

    @Test
    public void shouldDriveForwardAndStopAfterDuration() {
        tester = new CommandTester(new TimedDriveCommand(drive, 0.5, 0.0, false, 2.0));
        assertThat(leftMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);

        // Start the command with the given artificial start time ...
        tester.step(START_TIME_MS);

        // Start the command and simulate time advancing almost 2 seconds ...
        tester.step(START_TIME_MS + 1999);
        assertThat(leftMotor.getSpeed()).isEqualTo(0.5, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.5, TOLERANCE);

        // Advance time past the 2 seconds ...
        tester.step(START_TIME_MS + 2100);
        assertThat(leftMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
    }

    @Test
    public void shouldStopWhenCancelled() {
        tester = new CommandTester(new TimedDriveCommand(drive, 0.5, 0.0, false, 2.0));
        assertThat(leftMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);

        // Start the command with the given artificial start time ...
        tester.step(START_TIME_MS);

        // Start the command and simulate time advancing almost 2 seconds ...
        tester.step(START_TIME_MS + 1999);
        assertThat(leftMotor.getSpeed()).isEqualTo(0.5, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.5, TOLERANCE);

        // Cancel the command, which should interrupt the command and advance the time ...
        tester.cancel();
        tester.step(START_TIME_MS + 1);
        assertThat(leftMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
        assertThat(rightMotor.getSpeed()).isEqualTo(0.0, TOLERANCE);
    }
}