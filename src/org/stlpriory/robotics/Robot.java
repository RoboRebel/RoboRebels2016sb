package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.TimedDriveCommand;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.CANDrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.strongback.Strongback;
import org.strongback.components.ui.ContinuousRange;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * A tank-drive robot controlled with a Logitech F310 XBox controller plugged into port 1 
 * on the Driver Station. The robot has two motors on each side, and each motor is controlled 
 * by one Talon motor controller.

 */
public class Robot extends IterativeRobot {

    // Initialize robot subsystems
    public static final DrivetrainSubsystem drivetrain = new CANDrivetrainSubsystem();
    public static final BallHolderSubsystem ballHolder = new BallHolderSubsystem();
    public static final ShooterSubsystem shooter = new ShooterSubsystem();
    
    // Human operator interface
    private OI oi = new OI();

    private ContinuousRange leftSpeed;
    private ContinuousRange rightSpeed;
    
    // ==================================================================================
    //                            ROBOT INIT SECTION
    // ==================================================================================

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional, but we won't use 
        // events so it's slightly better if we turn them off. All other defaults are fine.
        Strongback.configure().recordNoEvents().initialize();

        // Initialize robot subsystems ...
        initSubsystems();

        // Set up the human input controls for teleoperated mode. 
        initController();
    }
    
    public OI getOI() {
        return this.oi;
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }
    
    private void initSubsystems() {
        // sub-systems statically initialized upon Robot instantiation
    }
    
    private void initController() {
        this.oi = new OI();
    }
    
    // ==================================================================================
    //                          AUTONOMOUS MODE SECTION
    // ==================================================================================

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.start();
        Strongback.submit(new TimedDriveCommand(drivetrain.getDrive(), 0.5, 0.5, false, 5.0));
    }
    
    @Override
    public void autonomousPeriodic() {
    }
    
    // ==================================================================================
    //                            TELEOP MODE SECTION
    // ==================================================================================

    @Override
    public void teleopInit() {
        // Kill anything running if it is ...
        Strongback.disable();
        // Start Strongback functions if not already running...
        Strongback.start();
        // Record initial status values
        updateStatus();
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.getDrive().tank(this.leftSpeed.read(), this.rightSpeed.read(), true);
        // Record updated status values
        updateStatus();
    }
    
    @Override
    public void disabledPeriodic() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
        // Record final status values
        updateStatus();
    }
    
    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
//        drivetrain.updateStatus();
//        ballHolder.updateStatus();
//        shooter.updateStatus();
    }

    @Override
    public void testInit() {
        LiveWindow.run();
    }
}
