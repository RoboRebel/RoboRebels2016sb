package org.stlpriory.robotics.hardware;

/**
 * Collection of specs for various robot hardware components. This are used
 * to setup output scaling values when WPI library wrapper classes.
 */
public class HardwareSpecs {
    
    public static final class Encoders {
        /**
         * Specifications for the E4T OEM Miniature Optical Encoder Kit (am-3132). The E4T 
         * miniature optical encoder provides digital quadrature encoder feedback for high 
         * volume, limited space applications. The E4T is designed to be a drop in replacement 
         * for the E4P that offers higher maximum speed and increased output drive.
         * 
         * @see <a href="http://www.andymark.com/product-p/am-3132.htm"> Andy Mark E4T OEM Optical Encoder</a>
         * @see <a href="http://cdn.usdigital.com/assets/assembly/E4T%20Assembly%20Instructions.pdf"> Encoder Assembly Instructions</a>
         * @see <a href="http://cdn.usdigital.com/assets/drawings/20465.pdf"> Encoder Drawings</a>
         */
        public final class AMOpticalEncoder {
            public static final int PULSES_PER_REV  = 1440;
            public static final int CYCLES_PER_REV  = 360;
            public static final double MIN_VOLTAGE  = 5.0d;  // volts
            
            private AMOpticalEncoder() {}
        }
        
        /**
         * Specifications for the Andy Mark CIMcoder. This encoder mounts to the nose of a CIM Motor and 
         * senses the rotations of the CIM Motor output shaft. A housing, mounted to the CIM Motor, protects 
         * the encoder circuitry while a collet spins with the motor output shaft. Pull up resistors are 
         * necessary to generate an output signal for the desired voltage level. This encoder has a 2 channel 
         * quadrature output with 20 pulses per channel per revolution for sensing speed and direction.
         * 
         * @see <a href="http://www.andymark.com/encoder-p/am-3314.htm"> Andy Mark CIMcoder</a>
         * @see <a href="http://files.andymark.com/PDFs/CIMcoder_Spec_Sheet_1-27-16.pdf"> CIMcoder Spec Sheet</a>
         */
        public final class CIMcoder {
            public static final int PULSES_PER_REV = 20;
            public static final double MIN_VOLTAGE = 4.0d;  // volts
            public static final double MAX_VOLTAGE = 24.0d; // volts
            
            private CIMcoder() {}
        }
        
    }
    
    public static final class Motors {
        /**
         * Specifications for the VEX Robotics Motor Data - CIM Motor (217-2000). 
         * @see <a href="http://motors.vex.com/cim-motor"> VEX CIM Motor</a>
         */
        public final class CIMMotor {
            public static final int MAX_SPEED_RPM   = 5330;
            public static final double MAX_VOLTAGE  = 12.0d;  // volts
            public static final double FREE_CURRENT = 2.7d; // amps
            
            private CIMMotor() {}
        }
        
        /**
         * Specifications for the VEX Robotics Motor Data - Mini CIM Motor (217-3371). 
         * @see <a href="http://motors.vex.com/mini-cim-motor"> VEX Mini CIM Motor</a>
         */
        public final class MiniCIMMotor {
            public static final int MAX_SPEED_RPM   = 5840;
            public static final double MAX_VOLTAGE  = 12.0d;  // volts
            public static final double FREE_CURRENT = 1.5d; // amps
            
            private MiniCIMMotor() {
                // do not allow instances
            }
        }
        
        /**
         * Specifications for the Andy Mark Snow Blower Motor (am-2235)
         * @see <a href="http://www.andymark.com/Motor-p/am-2235.htm"> Andy Mark Snow Blower Motor</a>
         */
        public final class SnowBlowerMotorSpecs {
            public static final int MAX_SPEED_RPM   = 100;
            public static final double MAX_VOLTAGE  = 12.0d;  // volts
            public static final double FREE_CURRENT = 5.0d;   // amps
            
            private SnowBlowerMotorSpecs() {}
        }
        
    }

}
