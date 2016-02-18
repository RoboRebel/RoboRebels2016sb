package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.strongback.command.Command;

/**
 * The command that ...
 */
public class PickUpBall extends ShooterCommandBase {
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     */
    public PickUpBall(ShooterSubsystem shooter) {
        super(shooter);
    }
    
    /**
     * Base for shooter type commands.
     * @param shooter the ball shooter subsystem
     * @param duration the duration of this command; should be positive
     */
    public PickUpBall(ShooterSubsystem shooter, double duration) {
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
        // The sequence of actions to pick up a ball are: 
        // (1) precondition - assume there is not ball loaded into the ball holder
        //    (do we need a way to detect whether a ball is loaded or not????)
        // (2) precondition - assume the loader arm is retracted
        // (3) start the shooter motors spinning inward
        // (4) stop the shooter motors once we detect a ball is loaded???

        // Ensure that the loader arm is retracted
        if (! isLoaderArmRetracted()) {
            retractLoaderArm();
        }
        
        // start the shooter motors spinning inward
        startSucker();
        
        // keep going until a ball is loaded or the command times out
        if (! isBallLoaded()) {
            return false;
        }
        
        // stop the shooter motors
        stop();
        
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
        stop();
    }

}