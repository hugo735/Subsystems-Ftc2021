package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Wooble;

@Autonomous(name="FinalC", group=" Trayectorias")
public class TargetZoneFinalC extends LinearOpMode {
    @Override
    public void runOpMode() {

        Wooble wooble = new Wooble(hardwareMap);

        wooble.setIntakeW(1);
        wooble.setArm(0);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-63, -26, Math.toRadians(0));

        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-14.0, -20))
                .build();

        Trajectory builder3 = drive.trajectoryBuilder(builder1.end())
                .lineToLinearHeading(new Pose2d(53.0,-64.0,Math.toRadians(-90.0)))
                .build();

        Trajectory builder4 = drive.trajectoryBuilder(builder3.end())
                .lineToLinearHeading(new Pose2d(0, -55, Math.toRadians(90)))
                .build();

        Trajectory align = drive.trajectoryBuilder(builder4.end())
                .lineToConstantHeading(new Vector2d(-30, -42))
                .build();

        Trajectory strafe = drive.trajectoryBuilder(align.end())
                .strafeLeft(22)
                .build();

        Trajectory builder5 = drive.trajectoryBuilder(strafe.end())
                .lineToSplineHeading(new Pose2d(45.0,-74.0,Math.toRadians(-100.0)))
                .build();

        Trajectory builder6 = drive.trajectoryBuilder(builder5.end())
                .strafeRight(40)
                .build();

        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);
        /*
        drive.turn(Math.toRadians(2.0));
        //aqui dispara
        drive.turn(Math.toRadians(3.0));
        //aqui dispara
        drive.turn(Math.toRadians(5.0));
        //aqui dispara
        drive.turn(Math.toRadians(143.0));
         */

        drive.followTrajectory(builder1);
        drive.followTrajectory(builder3);
        wooble.setArm(1);
        sleep(500);
        wooble.setIntakeW(0);
        drive.followTrajectory(builder4);
        drive.followTrajectory(align);

        wooble.setArm(0.9);

        drive.followTrajectory(strafe);

        wooble.setIntakeW(1);
        sleep(500);
        wooble.setArm(0);

        drive.followTrajectory(builder5);

        wooble.setArm(1);
        sleep(500);
        wooble.setIntakeW(0);

        drive.followTrajectory(builder6);

    }

}
