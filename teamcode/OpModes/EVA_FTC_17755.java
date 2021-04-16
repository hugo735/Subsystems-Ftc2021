package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ElectricBot;
import org.firstinspires.ftc.teamcode.Resources.Constants;
import org.firstinspires.ftc.teamcode.Resources.Controller;
import org.firstinspires.ftc.teamcode.Resources.Names;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeW;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name = "EVA-02", group = "TeleOp")
public class EVA_FTC_17755 extends OpMode {

    private ElectricBot m_electricBot;
    private Controller controller1;
    private boolean lowVelocity;
    private double driveVel;

    @Override
    public void init() {
        telemetry.addData("Status:", "Initializing...");
        telemetry.addData("Name1: ", Names.driveMotorNames[3]);
        m_electricBot = new ElectricBot(hardwareMap, telemetry);

        controller1 = new Controller(gamepad1);

        telemetry.addData("Status:", "Initialized.");
    }

    @Override
    public void init_loop() {
    }


    @Override
    public void loop() {
        controller1.Update();

        lowVelocity = gamepad1.left_trigger > 0.8 ? true : false;

        driveVel = lowVelocity ? 0.5 : 1;

        m_electricBot.m_drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.left_stick_y * driveVel,
                        -gamepad1.left_stick_x * driveVel,
                        gamepad1.right_stick_x * driveVel
                )
        );

        //INTAKE
        m_electricBot.m_intake.setIntake(gamepad1.right_bumper? 1 : gamepad1.left_bumper ? -1 : 0);

        //shooter
       // m_electricBot.m_shooter.setShooter(gamepad1.b ? 0.1 : gamepad1.y ? -0.1 : 0);

        if(gamepad1.b){
            m_electricBot.m_shooter.setShooter(-0.1);
        } else{
            m_electricBot.m_shooter.setShooter(0);
        }
        //Wooble Intake
        if(gamepad1.dpad_right){
            m_electricBot.m_intakeW.setIntakeW(1);
        } else if(gamepad1.dpad_left){
            m_electricBot.m_intakeW.setIntakeW(0);
        }

        //Wooble Arm
        if(gamepad1.dpad_up){
            m_electricBot.m_intakeW.setArm(1);
        } else if(gamepad1.dpad_down){
            m_electricBot.m_intakeW.setArm(0);
        }

        telemetry.addData("XValue", gamepad1.left_stick_x);
        telemetry.addData("YValue", gamepad1.left_stick_y);

    }

    @Override
    public void stop() {
        super.stop();
    }
}
