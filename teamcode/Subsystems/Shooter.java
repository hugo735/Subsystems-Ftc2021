package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Shooter {

    private DcMotorEx shooter0;

    Telemetry tel;

    public Shooter(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {

        shooter0 = map.get(DcMotorEx.class, "shooterM");

        shooter0.setDirection(DcMotorSimple.Direction.FORWARD);

        shooter0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        tel = telemetry;
    }

    public double getShooterVelocity(){
        return shooter0.getVelocity();
    }

    public void setShooter(double power)
    {
        shooter0.setPower(power);
    }

}
