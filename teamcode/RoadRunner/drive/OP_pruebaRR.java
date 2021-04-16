package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="OP_de_prueba_RR", group=" Opmode")
public class OP_pruebaRR extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory Trajectory = drive.trajectoryBuilder(new Pose2d(0.0,0.0, Math.toRadians(90)))
                .forward(10.0)
                .build();

        Trajectory Trajectory1 = drive.trajectoryBuilder(Trajectory.end())
                .back(10.0)
                .build();

        Trajectory Trajectory2 = drive.trajectoryBuilder(Trajectory1.end())
        .strafeLeft(20.0)
        .build();

        Trajectory Trajectory3 = drive.trajectoryBuilder(Trajectory2.end())
        .strafeRight(20.0)
        .build();

        Trajectory Trajectory4 = drive.trajectoryBuilder(Trajectory3.end())
        .splineTo(new Vector2d(20.0,20.0),Math.toRadians(0))
        .build();

        Trajectory Trajectory5 = drive.trajectoryBuilder(Trajectory4.end(),true)
                .splineTo(new Vector2d(0.0,0.0),Math.toRadians(0))
            .build();

        Trajectory Trajectory6 = drive.trajectoryBuilder(Trajectory5.end())
         .lineTo(new Vector2d(20.0,20.0))
                .build();

        Trajectory Trajectory7 = drive.trajectoryBuilder(Trajectory6.end(),true)
                .lineTo(new Vector2d(0.0,0.0))

                .build();

        waitForStart();

        if(isStopRequested()) return;
        telemetry.addData("Adelante","10 Inch deberia");
        telemetry.update();
        drive.followTrajectory(Trajectory);
        telemetry.addData("atras","10 Inch deberia");
        telemetry.update();

        drive.followTrajectory(Trajectory1);
        telemetry.addData("izquierda","20 Inch deberia");
        telemetry.update();

       drive.followTrajectory(Trajectory2);
        telemetry.addData("derecha","20 Inch deberia");
        telemetry.update();

       drive.followTrajectory(Trajectory3);
        telemetry.addData("spline","o es lineto,pero debe jalar");
        telemetry.update();

       drive.followTrajectory(Trajectory4);
        telemetry.addData("spline","o lineto pero jala");
        telemetry.update();

        drive.followTrajectory(Trajectory5);
        telemetry.addData("spline","o es lineto,pero debe jalar");
        telemetry.update();
        drive.followTrajectory(Trajectory6);
        telemetry.addData("spline","o es lineto,pero debe jalar");
        telemetry.update();
        drive.followTrajectory(Trajectory7);
        telemetry.addData("Giro de 90","");
        telemetry.update();
        sleep(5000);

        drive.turn(Math.toRadians(90));
        telemetry.addData("giro de 180","de 180");
        telemetry.update();
        sleep(5000);


    sleep(100000);
    }
}
