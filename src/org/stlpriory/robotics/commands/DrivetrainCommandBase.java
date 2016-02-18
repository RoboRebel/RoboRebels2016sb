package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.strongback.command.Command;

/**
 * The command that ...
 */
public abstract class DrivetrainCommandBase extends Command {

    private final DrivetrainSubsystem drivetrain;
    
    /**
     * Base for drive train type commands.
     * @param ballholder the ball holder subsystem
     */
    public DrivetrainCommandBase(DrivetrainSubsystem drivetrain) {
        super(drivetrain.getDrive());
        this.drivetrain = drivetrain;
    }
    
    /**
     * Base for drive train type commands.
     * @param ballholder the ball holder subsystem
     * @param duration the duration of this command; should be positive
     */
    public DrivetrainCommandBase(DrivetrainSubsystem drivetrain, double duration) {
        super(duration, drivetrain.getDrive());
        this.drivetrain = drivetrain;
    }
    
    public void driveStraight(double speed) {
        this.drivetrain.getDrive().tank(speed, speed);
    }
    
    public void turnLeft(double speed) {
        this.drivetrain.getDrive().tank(-speed, speed);
    }
    
    public void turnRight(double speed) {
        this.drivetrain.getDrive().tank(speed, -speed);
    }
    
    public void stop() {
        this.drivetrain.getDrive().stop();
    }
    
}