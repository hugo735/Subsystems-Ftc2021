package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="FinalA", group=" Trayectorias")
public class TargetZoneFinalA extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-52, -26, Math.toRadians(360));

        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-5.0, -62.0), Math.toRadians(0.0))
                //DisplacmentMarker para dejar el wooble PD: crear funcion dejar wooble y retraer garra por separados
                // retraer la garra en el mismo o en otro
                .splineToConstantHeading(new Vector2d(-5.0,-22.0), Math.toRadians(180.0))
                //displacment marker para disparar a powerShots, posiblemente usemos la imu para los angulos o con tiempos
                //pero con los markers de tipo global porque manejan tiempos
                .splineToConstantHeading(new Vector2d(-40.0,-50.0),Math.toRadians(180.0))

                .build();

        Trajectory builder2 = drive.trajectoryBuilder(builder1.end())
                //.splineToConstantHeading(Vector2d(-5.0,-22.0),180.0.ToRadians)

                .build();

        Trajectory builder3 = drive.trajectoryBuilder(builder1.end(),true)
                //     .splineToSplineHeading(Pose2d(-30.0,-45.0,0.0.ToRadians),180.0.ToRadians)
                //temporalyMArker para agarrar el wooble
                .build();

        Trajectory builder4 = drive.trajectoryBuilder(builder1.end())
                .lineTo(new Vector2d(-3.0,-55.0))
                //displacementMarker para girar y dejar el wooble
                //mejor creemos una funcion de girar en base con la imu o el que hizo venegas mas bien
                .splineToConstantHeading(new Vector2d(10.0,-45.0),Math.toRadians(0.0))
                .build();

        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);

        drive.followTrajectory(builder1);
        telemetry.addData("deja el wooble","");
        telemetry.update();


        drive.followTrajectory(builder4);
        telemetry.addData("","");
        telemetry.update();

    }

}
