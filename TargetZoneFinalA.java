package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Wooble;

@Autonomous(name="FinalA", group=" Trayectorias")
public class TargetZoneFinalA extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Wooble wooble = new Wooble(hardwareMap);

        wooble.setIntakeW(1);
        wooble.setArm(0);

        //TARGET ZONE A
        Pose2d startPose = new Pose2d(-63, -26, Math.toRadians(0));

        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(2.0,-61.0,Math.toRadians(-90.0)))
                .build();

        Trajectory strafe1 = drive.trajectoryBuilder(builder1.end())
                .strafeRight(10)
                .build();

        Trajectory builder2 = drive.trajectoryBuilder(strafe1.end())
                .lineToLinearHeading(new Pose2d(-25.0,-52.0,Math.toRadians(94)))
                .build();

        Trajectory builderStrafe1 = drive.trajectoryBuilder(builder2.end())
                .strafeLeft(15)
                .build();

        Trajectory builder3 = drive.trajectoryBuilder(builderStrafe1.end())
                .lineToLinearHeading(new Pose2d(2.0,-62.0,Math.toRadians(-89.0)))
                .build();

        Trajectory out = drive.trajectoryBuilder(builder3.end())
                .strafeRight(20)
                .build();

        Trajectory back = drive.trajectoryBuilder(out.end())
                .back(30)
                .build();

        Trajectory strafe2 = drive.trajectoryBuilder(back.end())
                .strafeLeft(40)
                .build();

        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);

        drive.followTrajectory(builder1);
        wooble.setArm(1);
        sleep(750);
        wooble.setIntakeW(0);
        sleep(2000);
        drive.followTrajectory(strafe1);
        drive.followTrajectory(builder2);
        wooble.setArm(0.8);
        drive.followTrajectory(builderStrafe1);
        wooble.setIntakeW(1);
        sleep(1000);
        wooble.setArm(0);
        sleep(2000);
        drive.followTrajectory(builder3);
        wooble.setArm(1);
        sleep(500);
        wooble.setIntakeW(0);
        sleep(2000);
        drive.followTrajectory(out);
        drive.followTrajectory(back);
        drive.followTrajectory(strafe2);
    }

}
