package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Intake {

    public enum IntakeAction {IN, OUT, STOP}

    ;
    IntakeAction[] actualAction = new IntakeAction[2];
    private DcMotor mIntake0;
    private DcMotor mIntake1;

    Telemetry tel;

    public Intake(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {

        mIntake0 = map.get(DcMotor.class, "intake" );
        mIntake1 = map.get(DcMotor.class,"intake2");

        mIntake1.setDirection(DcMotor.Direction.FORWARD);
        mIntake0.setDirection(DcMotor.Direction.REVERSE);

        tel = telemetry;
    }

    public void setIntake(double speed){
        mIntake1.setPower(speed);
        mIntake0.setPower(speed);
    }
}
