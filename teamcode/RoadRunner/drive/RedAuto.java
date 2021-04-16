package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Red Autonomous", group = "Concept")
public class RedAuto extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            " -- YOUR NEW VUFORIA KEY GOES HERE  --- ";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-52, -26, Math.toRadians(360));

        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            tfod.setZoom(2.5, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 0 ) {

                            // empty list.  no objects recognized.
                            telemetry.addData("TFOD", "No items detected.");
                            telemetry.addData("Target Zone", "A");

                            Trajectory builder1 = drive.trajectoryBuilder(startPose)
                                    .splineToConstantHeading(new Vector2d(-5.0, -62.0),Math.toRadians(0.0))
                                    //DisplacmentMarker para dejar el wooble PD: crear funcion dejar wooble y retraer garra por separados
                                    // retraer la garra en el mismo o en otro
                                    .splineToConstantHeading(new Vector2d(-5.0,-22.0),Math.toRadians(180.0))
                                    //displacment marker para disparar a powerShots, posiblemente usemos la imu para los angulos o con tiempos
                                    //pero con los markers de tipo global porque manejan tiempos
                                    .build();


                            Trajectory builder3 = drive.trajectoryBuilder(builder1.end(),true)
                                    .splineToSplineHeading(new Pose2d(-30.0,-25.0), Math.toRadians(180.0))
                                    //temporalyMArker para agarrar el wooble
                                    .build();

                            Trajectory builder4 = drive.trajectoryBuilder(builder3.end())
                                    .lineTo(new Vector2d(-3.0,-53.0))
                                    //displacementMarker para girar y dejar el wooble
                                    //mejor creemos una funcion de girar en base con la imu o el que hizo venegas mas bien
                                    .splineToConstantHeading(new Vector2d(10.0,-45.0),Math.toRadians(0.0))
                                    .build();
                            waitForStart();

                            if(isStopRequested()) return;
                            drive.setPoseEstimate(startPose);
                            drive.followTrajectory(builder1);
                            drive.followTrajectory(builder3);
                            drive.followTrajectory(builder4);

                        } else {
                            // list is not empty.
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                // check label to see which target zone to go after.
                                if (recognition.getLabel().equals("Single")) {
                                    telemetry.addData("Target Zone", "B");
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
                                    drive.followTrajectory(builder2);
                                    drive.followTrajectory(builder3);
                                    drive.followTrajectory(builder4);
                                } else if (recognition.getLabel().equals("Quad")) {
                                    telemetry.addData("Target Zone", "C");

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
                                    drive.followTrajectory(builder1);
                                    drive.followTrajectory(builder2);
                                    drive.followTrajectory(builder3);
                                    drive.followTrajectory(builder4);
                                } else {
                                    telemetry.addData("Target Zone", "UNKNOWN");
                                }
                            }
                        }

                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
