package org.stlpriory.robotics.commands;

import org.strongback.command.Command;
import org.strongback.drive.TankDrive;

/**
 * The command that drives the robot at a constant forward and turn speed for a specific duration.
 */
public class TimedDriveCommand extends Command {

    private final TankDrive drive;
    private final double driveSpeed;
    private final double turnSpeed;
    private final boolean squareInputs;
    
    /**
     * Create a new autonomous command.
     * @param drive the chassis
     * @param driveSpeed the speed at which to drive forward; should be [-1.0, 1.0]
     * @param turnSpeed the speed at which to turn; should be [-1.0, 1.0]
     * @param squareInputs whether to increase sensitivity at low speeds
     * @param duration the duration of this command; should be positive
     */
    public TimedDriveCommand( TankDrive drive, double driveSpeed, double turnSpeed, boolean squareInputs, double duration ) {
        super(duration, drive);
        this.drive = drive;
        this.driveSpeed = driveSpeed;
        this.turnSpeed = turnSpeed;
        this.squareInputs = squareInputs;
    }
    
    @Override
    public boolean execute() {
        drive.arcade(driveSpeed, turnSpeed, squareInputs);
        return false;   // not complete; it will time out automatically
    }
    
    @Override
    public void interrupted() {
        drive.stop();
    }
    
    @Override
    public void end() {
        drive.stop();
    }

}