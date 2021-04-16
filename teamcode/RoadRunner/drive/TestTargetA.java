package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestTargetA", group=" Opmode")
public class TestTargetA extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-52, -48, Math.toRadians(280));

        drive.setPoseEstimate(startPose);
        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-5.0, -62.0), 0.0)
                .splineToConstantHeading(new Vector2d(-5.0,-22.0),Math.toRadians(180.0))
                .build();
        Trajectory builder5 = drive.trajectoryBuilder(startPose,true)
                .splineTo(new Vector2d(-14.0,-28.0),Math.toRadians(150))
                .splineTo(new Vector2d(-36.0,-25.0),Math.toRadians(280))
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.setPoseEstimate(startPose);

        drive.followTrajectory(builder1);

        telemetry.addData("splines","");
        telemetry.update();
    }
}
