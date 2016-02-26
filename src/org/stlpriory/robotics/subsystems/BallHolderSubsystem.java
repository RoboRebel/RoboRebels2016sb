package org.stlpriory.robotics.subsystems;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHolderSubsystem {
    public static final int RIGHT_WINDOW_MOTOR_PWM_CHANNEL = 1;
    public static final int LEFT_WINDOW_MOTOR_PWM_CHANNEL = 2;

    public static final int POT_CHANNEL = 0;
    public static final int SWITCH_DIO_CHANNEL = 1;
    
    /*
     * The scaling factor multiplied by the analog voltage value to obtain the angle in degrees
     * For example, let's say you have an ideal 10-turn linear potentiometer attached to a motor
     * attached by chains and a 25x gear reduction to an arm. If the potentiometer (attached to
     * the motor shaft) turned its full 3600 degrees, the arm would rotate 144 degrees. Therefore,
     * the POT_FULL_RANGE scale factor would be 144 degrees / 5V,  or 28.8 degrees/volt.
     * Since the RoboRebels ball holder uses a 1x gear reduction between the potentiometer and the
     * rotating arm the POT_FULL_RANGE scale factor would be 3600 degrees / 5V or 720 degrees/volt.
     */
    public static final int POT_FULL_RANGE = 720;
    
    /*
     * The offset in degrees that the angle sensor will subtract from the underlying value before
     * returning the angle. Specifying the offset is necessary because often to prevent the potentiometer
     * from breaking due to minor shifting in alignment of the mechanism, the potentiometer may
     * be installed with the "zero-point" of the mechanism (e.g., arm in this case) a little ways into
     * the potentiometer's range (for example 30 degrees). In this case, the offset value of 30 is
     * determined from the mechanical design.
     */
    public static final int POT_OFFSET_DEG = 0;
    
    public static final double BALL_PICKUP_ANGLE    = 180.0d;
    public static final double LOW_GOAL_SHOOT_ANGLE = 120.0d;
    public static final double HI_GOAL_SHOOT_ANGLE  = 60.0d;
    public static final double ANGLE_TOLERANCE = 1.0d;
    
    public static final double ARM_EXTEND_SPEED  = 0.2d;
    public static final double ARM_RETRACT_SPEED = -0.2d;

    private final Motor ballHolderMotor;
    private final AngleSensor angleSensor;
    
    private final Switch stowSwitch;


    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public BallHolderSubsystem() {
        this.ballHolderMotor = Motor.compose(Hardware.Motors.talon(RIGHT_WINDOW_MOTOR_PWM_CHANNEL), 
                                             Hardware.Motors.talon(LEFT_WINDOW_MOTOR_PWM_CHANNEL).invert());
        
        this.stowSwitch = Hardware.Switches.normallyOpen(SWITCH_DIO_CHANNEL);
        
        this.angleSensor = Hardware.AngleSensors.potentiometer(POT_CHANNEL, POT_FULL_RANGE, POT_OFFSET_DEG);
        
        // Reset the angle sensor so that the current sensor angle reading at robot startup
        // is considered zero so that every subsequent angle reading will be relative to
        // that starting position
        this.angleSensor.zero();
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================
    
    public Motor getMotor() {
        return this.ballHolderMotor;
    }
    
    public AngleSensor getAngleSensor() {
        return this.angleSensor;
    }
    
    public boolean isStowed() {
        return this.stowSwitch.isTriggered();
    }
    
    public void setSpeed(final double speed) {
        this.ballHolderMotor.setSpeed(speed);
    }

    public void stop() {
        setSpeed(0);
    }
    
    /**
     * Get the current ball holder arm angle relative to its initial position upon startup
     * @return the potentiometer reading in degrees
     */
    public double getAngle() {
        return this.angleSensor.getAngle();
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Ballholder motor speed", this.ballHolderMotor.getSpeed());
        SmartDashboard.putNumber("Ballholder sensor angle", this.angleSensor.getAngle());
    }

}
