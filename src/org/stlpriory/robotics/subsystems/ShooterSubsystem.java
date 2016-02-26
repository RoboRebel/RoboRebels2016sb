package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.hardware.HardwareSpecs;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem {
    public static final int SERVO_PWM_CHANNEL = 9;

    public static final int LEFT_SHOOTER_MOTOR_PWM_CHANNEL = 3;
    public static final int LEFT_SHOOTER_ENCODER_DIO_CHANNEL_A = 6;
    public static final int LEFT_SHOOTER_ENCODER_DIO_CHANNEL_B = 7;
    
    public static final int RIGHT_SHOOTER_MOTOR_PWM_CHANNEL = 0;
    public static final int RIGHT_SHOOTER_ENCODER_DIO_CHANNEL_A = 8;
    public static final int RIGHT_SHOOTER_ENCODER_DIO_CHANNEL_B = 9;
    
    // Target throttle settings within the range [-1.0,1.0]
    public static final double KEEPING_SPEED = -0.1;
    public static final double SUCK_SPEED    = -0.5;
    public static final double SHOOT_SPEED   = 1.0;
    
    // Minimum shooting speed is 90% of the max speed (RPM) for the CIM motor
    public static final double MIN_SHOOTING_SPEED = 0.9 * HardwareSpecs.Motors.MiniCIMMotor.MAX_SPEED_RPM; 
    // Allowable difference between left and right motors is 5% of the max speed (RPM) for the CIM motor 
    public static final double MAX_DIFFERENCE = 0.05 * HardwareSpecs.Motors.MiniCIMMotor.MAX_SPEED_RPM;
    // Increment to use when decreasing the throttle setting [-1.0, 1.0]
    public static final double DECREASE_VALUE = .01;

    private static final double KICKER_OUT_POSITION = 0;
    private static final double KICKER_IN_POSITION  = 1;
    
    private final Encoder rightEncoder;
    private final Encoder leftEncoder; 

    private final Motor rightMotor;
    private final Motor leftMotor;
    
    private final Servo loaderArm;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ShooterSubsystem() {
        this.rightMotor = Hardware.Motors.talon(RIGHT_SHOOTER_MOTOR_PWM_CHANNEL);
        this.leftMotor  = Hardware.Motors.talon(LEFT_SHOOTER_MOTOR_PWM_CHANNEL).invert();
        
        // Distance per pulse is the fraction of a rotation you get with each encoder pulse
        double angDistPerPulse = 1.0d / HardwareSpecs.Encoders.CIMcoder.PULSES_PER_REV;
        
        this.rightEncoder = new Encoder(RIGHT_SHOOTER_ENCODER_DIO_CHANNEL_A, RIGHT_SHOOTER_ENCODER_DIO_CHANNEL_B);
        this.rightEncoder.setDistancePerPulse(angDistPerPulse);
        this.rightEncoder.setReverseDirection(false);

        this.leftEncoder = new Encoder(LEFT_SHOOTER_ENCODER_DIO_CHANNEL_A, LEFT_SHOOTER_ENCODER_DIO_CHANNEL_B);
        this.leftEncoder.setDistancePerPulse(angDistPerPulse);
        this.leftEncoder.setReverseDirection(true);
        
        this.loaderArm = new Servo(SERVO_PWM_CHANNEL);
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public Motor getRightShooterMotor() {
        return this.rightMotor;
    }

    public Motor getLeftShooterMotor() {
        return this.leftMotor;
    }
    
    public void extendLoaderArm() {
        this.loaderArm.set(KICKER_OUT_POSITION);
    }   
    
    public void retractLoaderArm() {
        this.loaderArm.set(KICKER_IN_POSITION);
    }
    
    public boolean isLoaderArmRetracted()  {
        return Math.abs(this.loaderArm.get() - KICKER_IN_POSITION) < 0.01;
    }
    
    public boolean isLoaderArmExtended() {
        return Math.abs(this.loaderArm.get() - KICKER_OUT_POSITION) < 0.01;
    }
    
    /**
     * @return the current right motor throttle setting
     */
    public double getRightThrottle() {
        return this.rightMotor.getSpeed();
    }
    
    /**
     * @return the current left motor throttle setting
     */
    public double getLeftThrottle() {
        return this.leftMotor.getSpeed();
    }
    
    /**
     * @return the right motor speed in rev/sec
     */
    public double getRightSpeed() {
        return this.rightEncoder.getRate();
    }
    
    /**
     * @return the left motor speed in rev/sec
     */
    public double getLeftSpeed() {
        return this.leftEncoder.getRate();
    }
    
    /**
     * @return the right motor speed in rev/min
     */
    public double getRightSpeedInRPM() {
        return getRightSpeed() * 60.0d;
    }
    
    /**
     * @return the left motor speed in rev/min
     */
    public double getLeftSpeedInRPM() {
        return getLeftSpeed() * 60.0d;
    }
    
    public void startShooter() {
        this.rightMotor.setSpeed(SHOOT_SPEED);
        this.leftMotor.setSpeed(SHOOT_SPEED);
    }
    
    public void startSucker() {
        this.rightMotor.setSpeed(SUCK_SPEED);
        this.leftMotor.setSpeed(SUCK_SPEED);
    }

    public void stop() {
        this.rightMotor.setSpeed(0);
        this.leftMotor.setSpeed(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right shooter speed", this.rightMotor.getSpeed());
        SmartDashboard.putNumber("Left shooter speed", this.leftMotor.getSpeed());
        SmartDashboard.putNumber("Right encoder speed", getRightSpeed());
        SmartDashboard.putNumber("Left encoder speed", getLeftSpeed());
        SmartDashboard.putNumber("Servo", loaderArm.get());
    }

}
