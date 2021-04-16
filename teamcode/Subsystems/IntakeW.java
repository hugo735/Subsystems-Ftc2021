package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Names;

public class IntakeW {
    //public enum DriveModes {ENCODERS, NO_ENCODERS, POSITION};
    public enum IntakeWAction {IN, OUT, UP, DOWN}

    // 0 = frontLeft      1 = frontRight
    // 2 = rearLeft       3 = rearRight
    private Servo servo1 = null;

    private Servo servo3 = null;

    private ElapsedTime timer;

    Telemetry tel;

    public IntakeW(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {
        servo1 = map.get(Servo.class,"s1");

        servo3 = map.get(Servo.class, "s3");

        tel = telemetry;
    }

    public void setIntakeW(double position){
        servo1.setPosition(position);
    }

    public void setArm(double position){
        servo3.setPosition(position);
    }


}