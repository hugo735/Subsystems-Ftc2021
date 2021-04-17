package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilderKt;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Wooble;

@Autonomous(name="FinalB", group=" Trayectorias")
public class TargetZoneFinalB extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Wooble wooble = new Wooble(hardwareMap);

        wooble.setIntakeW(1);
        wooble.setArm(0);

        Pose2d startPose = new Pose2d(-63, -26, Math.toRadians(0));

        Trajectory first = drive.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-20, -13, Math.toRadians(-90)))
                .build();
        Trajectory builder1 = drive.trajectoryBuilder(first.end())
                .lineToConstantHeading(new Vector2d(45.0, -29.0))
                .build();
        Trajectory builder2 = drive.trajectoryBuilder(builder1.end())
                .lineToLinearHeading(new Pose2d(-10.0, -17.0, Math.toRadians(0.0)))
                .build();
        Trajectory gotoWooble = drive.trajectoryBuilder(builder2.end())
                .lineToLinearHeading(new Pose2d(-15, -70, Math.toRadians(90)))
                .build();
        Trajectory builder3 = drive.trajectoryBuilder(gotoWooble.end())
                .strafeLeft(15.0)
                .build();
        Trajectory builder4 = drive.trajectoryBuilder(builder3.end())
                .lineToLinearHeading(new Pose2d(45.0, -50.0,Math.toRadians(-90.0)))
                .build();
        Trajectory strafeFinal = drive.trajectoryBuilder(builder4.end())
                .strafeRight(15)
                .build();
        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);

        drive.followTrajectory(first);
        drive.followTrajectory(builder1);
        wooble.setArm(1);
        sleep(500);
        wooble.setIntakeW(0);
        drive.followTrajectory(builder2);
        drive.followTrajectory(gotoWooble);
        wooble.setArm(0.9);
        drive.followTrajectory(builder3);
        wooble.setIntakeW(1);
        sleep(500);
        wooble.setArm(0);
        drive.followTrajectory(builder4);
        wooble.setArm(1);
        sleep(500);
        wooble.setIntakeW(0);
        drive.followTrajectory(strafeFinal);
    }

}
