package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestTargetC_Vol2", group=" Opmode")
public class TestTargetC_Vol2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-52, -26, Math.toRadians(360));

        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .forward(35.0)
                //el spline solo representa al forward con un drive.turn, que es el que se usara
                // son 3 drive turns y en cada uno se dispara



                .build();

        Trajectory builder2 = drive.trajectoryBuilder(builder1.end(),true)
                .splineToConstantHeading(new Vector2d(-20.0, -38.0),Math.toRadians(180.0))
                //DisplacementMarker(tipo global) para que inicialize el intake/feeder
                .splineToSplineHeading(new Pose2d(-35.0,-38.0,Math.toRadians(0.0)),Math.toRadians(180.0))
                //aqui dispara los 3 que ya tiene
                .back(10.0)
                //regresa, dispara y otro displacementMarker para que apague el intake/feeder
                .build();

        Trajectory builder3 = drive.trajectoryBuilder(builder2.end(),true)
                .lineTo(new Vector2d(42.0,-62.0))
                //displacementMarker para hacer el giro con la fun que crearemos
                //deja el wooble
                .splineTo(new Vector2d(-40.0, -48.0),Math.toRadians(180.0))

                //DisplacementMarker que gire y agarre el wooble
                .build();

        Trajectory builder4 = drive.trajectoryBuilder(builder3.end(),false)
                .lineTo(new Vector2d(39.0,-58.0))
                //displacementMarker para que gire y deje el wooble
                .splineToConstantHeading(new Vector2d(10.0,-45.0),Math.toRadians(90.0))
                //nada, aqui se estaciona
                .build();

        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);

        telemetry.addData("splines","");
        telemetry.update();
    }
}
