package org.stlpriory.robotics.commands;

import org.strongback.command.Command;
import org.strongback.drive.TankDrive;

/**
 * The command that ...
 */
public class PickUpBall extends Command {

    private final TankDrive drive;
    
    /**
     * Create a new command.
     * @param drive the chassis
     * @param duration the duration of this command; should be positive
     */
    public PickUpBall( TankDrive drive, double duration ) {
        super(duration, drive);
        this.drive = drive;
    }
    
    @Override
    public boolean execute() {
        drive.tank(0, 0, true);
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