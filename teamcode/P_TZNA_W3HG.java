package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static java.lang.Thread.sleep;


public class P_TZNA_W3HG {
       //public enum DriveModes {ENCODERS, NO_ENCODERS, POSITION};


        private DcMotor leftF;
        private DcMotor rightF;
        private DcMotor leftB;
        private DcMotor rightB;

        public IMU imu;

        private ElapsedTime timer;
        public double error;
        public double gain;

        static final double COUNTS_PER_MOTOR_REV  = 383.6;    // eg: TETRIX Motor Encoder
        static final double DRIVE_GEAR_REDUCTION  = 2;     // This is < 1.0 if geared UP
        static final double WHEEL_DIAMETER_INCHES = 3.93;     // For figuring circumference
        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.14159);

        Telemetry tel;

        public P_TZNA_W3HG(HardwareMap hardwareMap){
                leftF   = hardwareMap.get(DcMotor.class, "leftF");
                rightF  = hardwareMap.get(DcMotor.class, "rightF");
                leftB   = hardwareMap.get(DcMotor.class, "leftB");
                rightB  = hardwareMap.get(DcMotor.class, "rightB");

                leftF .setDirection(DcMotorSimple.Direction.FORWARD);
                rightF.setDirection(DcMotorSimple.Direction.REVERSE);
                leftB .setDirection(DcMotorSimple.Direction.FORWARD);
                rightB.setDirection(DcMotorSimple.Direction.REVERSE);

                leftF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



                imu = new IMU(hardwareMap,"IMU");
        }
        public void Turn(double targetHeading, double inPower, double timeout) {
                gain = 0.025;

                double power = inPower;
                double correction;
                double left;
                double right;

                timer.reset();
                leftF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                leftF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                while (timer.milliseconds() < timeout) {
                        error = imu.getError(targetHeading);
                        correction = error * gain;
                        left = power + correction;
                        right = -power - correction;
                        double correctedPowers[] = {left, right};
                        leftF.setPower(correctedPowers[0]);   rightF.setPower(correctedPowers[1]);
                        leftB.setPower(correctedPowers[0]);   rightB.setPower(correctedPowers[1]);
                }
                leftF .setPower(0);       rightF.setPower(0);
                leftB .setPower(0);       rightB.setPower(0);
        }
        public void setDriveEncoders(double targetHeading, double inPower, double inches, boolean directionReversed) {
              //resetea encoder
                leftF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                gain = 0.05;

                double power = inPower;

                double correction;
                double left;
                double right;

                double targetPosition = inches * COUNTS_PER_INCH;

                leftF.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftB.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                while ( Math.abs(leftF.getCurrentPosition()) < targetPosition) {
                        error = imu.getError(targetHeading);
                        correction = error * gain;
                        left = power + correction;
                        right = power - correction;
                        double correctedPowers[] = {left, right};

                        if(directionReversed) {
                                leftF.setPower(correctedPowers[1]);   rightF.setPower(correctedPowers[0]);
                                leftB.setPower(correctedPowers[1]);   rightB.setPower(correctedPowers[0]);

                        }else {
                                leftF.setPower(correctedPowers[0]);           rightF.setPower(correctedPowers[1]);
                                leftB.setPower(correctedPowers[0]);            rightB.setPower(correctedPowers[1]);
//set power
                        }
                }

                            //power to 0
        }

       public  void FORWARD(double Inches, boolean forward) {
               leftF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
               rightF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
               leftB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
               rightB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

               double Inches1, Inches2, Inches3;

               Inches =  Inches * COUNTS_PER_INCH;
               Inches1 = Inches + leftB.getCurrentPosition();
               Inches2 = Inches + leftF.getCurrentPosition();
               Inches3 = Inches + rightF.getCurrentPosition();
               Inches += rightB.getCurrentPosition();

               leftF .setTargetPosition((int) Inches1);    rightF.setTargetPosition((int) Inches2);
               leftB .setTargetPosition((int) Inches3);    rightB.setTargetPosition((int) Inches);
             if(forward) {
                     leftB.setPower(.5);        leftF.setPower(.5);
                     rightF.setPower(.5);       rightB.setPower(.5);
             }else{
                     leftB.setPower(-.5);       leftF.setPower(-.5);
                     rightF.setPower(-.5);      rightB.setPower(-.5);
             }
               leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               leftF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               rightF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              if(!leftB.isBusy()){
                      leftF .setPower(0);      rightF.setPower(0);
                      leftB .setPower(0);      rightB.setPower(0);
              }

       }
        public  void Strafe(double Inches, boolean right) {

                Inches = Inches * COUNTS_PER_INCH;
                Inches += leftB.getTargetPosition();

                leftB.setTargetPosition((int)Inches);    leftF.setTargetPosition((int)Inches);
                rightF.setTargetPosition((int)Inches);    rightB.setTargetPosition((int)Inches);
                 if(right) {
                         leftB.setPower(-.5);     leftF.setPower(.5);
                         rightF.setPower(-.5);    rightB.setPower(.5);
                 }else{
                         leftB.setPower(.5);   leftF.setPower(-.5);
                         rightF.setPower(.5);  rightB.setPower(-.5);
                 }
                leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(!leftB.isBusy()){
                        leftF .setPower(0);      rightF.setPower(0);
                        leftB .setPower(0);       rightB.setPower(0);
                }
        }


}
