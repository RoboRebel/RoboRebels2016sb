package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.TimedDriveCommand;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.strongback.Strongback;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * A tank-drive robot controlled with a Logitech F310 XBox controller plugged into port 1 
 * on the Driver Station. The robot has two motors on each side, and each motor is controlled 
 * by one Talon motor controller.

 */
public class Robot extends IterativeRobot {

    static final int GAMEPAD_PORT  = 1; // in driver station

    private DrivetrainSubsystem drivetrain;
    private ContinuousRange leftSpeed;
    private ContinuousRange rightSpeed;
    private Gamepad gamepad;
    
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
        initGamepad();
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }
    
    private void initSubsystems() {
        this.drivetrain = new DrivetrainSubsystem();
    }
    
    private void initGamepad() {
        // Left/right stick Y-values will return within the range of [-1,1]
        this.gamepad = Hardware.HumanInterfaceDevices.logitechF310(GAMEPAD_PORT);
        this.leftSpeed  = gamepad.getLeftY();
        this.rightSpeed = gamepad.getRightY();
    }
    
    // ==================================================================================
    //                          AUTONOMOUS MODE SECTION
    // ==================================================================================

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.start();
        Strongback.submit(new TimedDriveCommand(this.drivetrain.getDrive(), 0.5, 0.5, false, 5.0));
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
        this.drivetrain.getDrive().tank(this.leftSpeed.read(), this.rightSpeed.read(), true);
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
