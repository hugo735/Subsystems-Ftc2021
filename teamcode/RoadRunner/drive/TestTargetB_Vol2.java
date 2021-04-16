package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="TestTargetB_Vol2", group=" Opmode")
public class TestTargetB_Vol2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-52, -26, Math.toRadians(360));

        Trajectory builder1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(18.0, -39.0), Math.toRadians(0.0))

                .build();

        Trajectory builder2 = drive.trajectoryBuilder(builder1.end())


                .build();

        Trajectory builder3 = drive.trajectoryBuilder(builder1.end(),true)
                .back(13.0)
                //displacmentMarker pa disparar
                .back(20.0)
                //displacementMarker(tipo global)
                //y que sea con tiempos pero NO USAR "sleep()" NI DE PEDO
                .splineToConstantHeading(new Vector2d(-35.0,-25.0), Math.toRadians(180.0))

                .build();

        Trajectory builder4 = drive.trajectoryBuilder(builder3.end())
                .lineTo(new Vector2d(15.0,-30.0))
                //.splineToConstantHeading(Vector2d(10.0,-45.0),0.0.ToRadians)
                .build();

        waitForStart();

        if(isStopRequested()) return;
        drive.setPoseEstimate(startPose);

        drive.followTrajectory(builder1);
        telemetry.addData("deja el wooble","");
        telemetry.update();

       /* drive.followTrajectory(builder2);
        telemetry.addData("va a disparar","");
        telemetry.update();
*/
        drive.followTrajectory(builder3);
        telemetry.addData("deja segundo wobble","");
        telemetry.update();

        drive.followTrajectory(builder4);
        telemetry.addData("","");
        telemetry.update();

    }

}
