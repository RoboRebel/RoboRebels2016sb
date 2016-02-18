package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.strongback.command.Command;

/**
 * The command that ...
 */
public class Shoot extends ShooterCommandBase {
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     */
    public Shoot(ShooterSubsystem shooter) {
        super(shooter);
    }
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     * @param duration the duration of this command; should be positive
     */
    public Shoot(ShooterSubsystem shooter, double duration) {
        super(shooter, duration);
    }

    /**
     * Perform a one-time setup of this {@link Command} prior to any calls to {@link #execute()}. 
     * No physical hardware should be manipulated.
     * <p>
     * By default this method does nothing.
     */
    @Override
    public void initialize() {
        super.initialize();
    }
    
    /**
     * Perform the primary logic of this command. This method will be called repeatedly after 
     * this {@link Command} is initialized until it returns {@code true}.
     *
     * @return {@code true} if this {@link Command} is complete; {@code false} otherwise
     */
    @Override
    public boolean execute() {
        if (isBallLoaded()) {
            // Ensure that the loader arm is retracted
            if (! isLoaderArmRetracted()) {
                retractLoaderArm();
            }
            
            // start the shooter motors spinning outward
            startShooter();
            
            // pause for 1 second to allow the motors to get to full speed
            pauseTime(1.0);
            
            // extend the loader arm and to push the ball into the spinning wheels
            extendLoaderArm();
            
            // pause for 1 second before retracting arm
            pauseTime(1.0);
            
            // retract the loader arm
            retractLoaderArm();
            
            // stop the shooter motors
            stop();
        }
        return true;
    }
    
    /**
     * Signal that this command has been interrupted before {@link #initialize() initialization} 
     * or {@link #execute() execution} could successfully complete. A command is interrupted when 
     * the command is canceled, when the robot is shutdown while the command is still running, or 
     * when {@link #initialize()} or {@link #execute()} throw exceptions. Note that if this method
     * is called, then {@link #end()} will not be called on the command.
     * <p>
     * By default this method does nothing.
     */
    @Override
    public void interrupted() {
        retractLoaderArm();
        stop();
    }
    
    /**
     * Perform one-time clean up of the resources used by this command and typically putting the robot
     * in a safe state. This method is always called after {@link #execute()} returns {@code true} 
     * unless {@link #interrupted()} is called.
     * <p>
     * By default this method does nothing.
     */
    @Override
    public void end() {
        retractLoaderArm();
        stop();
    }

}