package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.strongback.command.Command;
import org.strongback.components.AngleSensor;

/**
 * The command that ...
 */
public abstract class BallHolderCommandBase extends Command {

    private final BallHolderSubsystem ballholder;
    
    /**
     * Base for ball holder type commands.
     * @param ballholder the ball holder subsystem
     */
    public BallHolderCommandBase(BallHolderSubsystem ballholder) {
        super(ballholder.getMotor());
        this.ballholder = ballholder;
    }
    
    /**
     * Base for ball holder type commands.
     * @param ballholder the ball holder subsystem
     * @param duration the duration of this command; should be positive
     */
    public BallHolderCommandBase(BallHolderSubsystem ballholder, double duration) {
        super(duration, ballholder.getMotor());
        this.ballholder = ballholder;
    }
    
    public boolean isStowed() {
        return this.ballholder.isStowed();
    }

    public void setSpeed(double speed) {
        this.ballholder.setSpeed(speed);
    }
    
    public AngleSensor getAngleSensor() {
        return this.ballholder.getAngleSensor();
    }
    
    public void stop() {
        this.ballholder.stop();
    }
    
}