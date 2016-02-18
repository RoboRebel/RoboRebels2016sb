package org.stlpriory.robotics.commands;

import java.util.concurrent.TimeUnit;

import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.strongback.command.Command;

/**
 * The command that ...
 */
public abstract class ShooterCommandBase extends Command {

    private final ShooterSubsystem shooter;
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     */
    public ShooterCommandBase(ShooterSubsystem shooter) {
        super(shooter.getMotor());
        this.shooter = shooter;
    }
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     * @param duration the duration of this command; should be positive
     */
    public ShooterCommandBase(ShooterSubsystem shooter, double duration) {
        super(duration, shooter.getMotor());
        this.shooter = shooter;
    }
    
    public boolean isBallLoaded() {
        return true;
    }
    
    public void startShooter() {
        this.shooter.startShooter();
    }
    
    public void startSucker() {
        this.shooter.startSucker();
    }
    
    public void stop() {
        this.shooter.stop();
    }

    public boolean isLoaderArmRetracted() {
        return this.shooter.isLoaderArmRetracted();
    }

    public void extendLoaderArm() {
        this.shooter.extendLoaderArm();
    }

    public void retractLoaderArm() {
        this.shooter.retractLoaderArm();
    }
    
    public void pauseTime(final double seconds) {
        long t0 = System.nanoTime();
        long elapsedTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - t0);
        
        while (elapsedTime < seconds) {
            elapsedTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - t0);
        }
    }
    
}