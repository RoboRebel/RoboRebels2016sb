package org.stlpriory.robotics.subsystems;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem {
    public static final int BALL_LOADER_CHANNEL = 4;

    public static final int LEFT_MOTOR_CHANNEL = 3;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_A = 6;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_B = 7;
    
    public static final int RIGHT_MOTOR_CHANNEL = 1;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_A = 8;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_B = 9;
    
    public static final double KEEPING_SPEED = .1;
    public static final double SUCK_SPEED = 1;
    public static final double SHOOT_SPEED = 1;

    private final Motor shooterMotor;
//    private final Encoder rightEncoder;
//    private final Encoder leftEncoder; 
    
    private final Solenoid ballLoaderTrigger;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ShooterSubsystem() {
        this.shooterMotor = Motor.compose(Hardware.Motors.talon(RIGHT_MOTOR_CHANNEL), 
                                          Hardware.Motors.talon(LEFT_MOTOR_CHANNEL).invert());
        
//        this.rightEncoder = new Encoder(RIGHT_MOTOR_ENCODER_CHANNEL_A, RIGHT_MOTOR_ENCODER_CHANNEL_B, false);
//        this.rightEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);
//        
//        this.leftEncoder  = new Encoder(LEFT_MOTOR_ENCODER_CHANNEL_A, LEFT_MOTOR_ENCODER_CHANNEL_B, true);
//        this.leftEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);
        
        this.ballLoaderTrigger  = new Solenoid(BALL_LOADER_CHANNEL);
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================
    
    public Motor getMotor() {
        return this.shooterMotor;
    }
    
//    public Encoder getLeftEncoder() {
//        return this.leftEncoder;
//    }
//    
//    public Encoder getRightEncoder() {
//        return this.rightEncoder;
//    }

    public void extendLoaderArm() {
        this.ballLoaderTrigger.set(true);
    }

    public void retractLoaderArm() {
        this.ballLoaderTrigger.set(false);
    }
    
    public boolean isLoaderArmRetracted() {
        return !this.ballLoaderTrigger.get();
    }
    
    public void startShooter() {
        this.shooterMotor.setSpeed(SHOOT_SPEED);

//      while (true) {
//          // Scale the left motor speed if the difference of the encoder values is > 10%
//          double rEncoderRate = this.rightEncoder.getRate();
//          double lEncoderRate = this.leftEncoder.getRate();
//          double percentDiff  = 100.0 * Math.abs( (rEncoderRate - lEncoderRate) / rEncoderRate);
//          if (percentDiff > 10) {
//              double scaleFactor = rEncoderRate / lEncoderRate;
//              this.leftShooter.set(-SHOOT_SPEED*scaleFactor);
//          } else {
//              break;
//          }
//      }
    }
    
    public void startSucker() {
        this.shooterMotor.setSpeed(SHOOT_SPEED);
    }

    public void stop() {
        this.shooterMotor.setSpeed(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Shooter motor speed", this.shooterMotor.getSpeed());
        SmartDashboard.putBoolean("Loader arm state", this.ballLoaderTrigger.get());
    }

}
