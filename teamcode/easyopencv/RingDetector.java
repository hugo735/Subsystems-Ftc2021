package org.firstinspires.ftc.teamcode.easyopencv;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RingDetector extends OpenCvPipeline {

    Telemetry telemetry;
    Mat mat = new Mat();
    public enum  RingQuantity{
        ONE,
        FOUR,
        NONE
    }
    private RingQuantity location;

    static final Rect TOP_REC = new Rect(
            new Point(50, 135),
            new Point(115, 90));
    static final Rect LOW_REC = new Rect(
            new Point(50, 135),
            new Point(115, 150));
    static double PERCENT_COLOR_THRESHOLD = 0.5;

    public RingDetector(Telemetry t) { telemetry = t; }
    @Override
        public Mat processFrame(Mat input) {

            // Img processing
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
            Scalar LowRedHSV = new Scalar(2, 206, 0);
            Scalar HighRedHSV = new Scalar(42, 255, 179);

            Core.inRange(mat, LowRedHSV, HighRedHSV, mat);


            Mat low = mat.submat(LOW_REC);
            Mat top = mat.submat(TOP_REC);

            double lowValue = Core.sumElems(low).val[0] / LOW_REC.area() / 255;
            double topValue = Core.sumElems(top).val[0] / TOP_REC.area() / 255;

            low.release();
            low.release();

            telemetry.addData("Left raw value", (int) Core.sumElems(low).val[0]);
            telemetry.addData("Right raw value", (int) Core.sumElems(top).val[0]);
            telemetry.addData("Left percentage", Math.round(lowValue * 100) + "%");
            telemetry.addData("Right percentage", Math.round(topValue * 100) + "%");

        boolean OneRing = lowValue > PERCENT_COLOR_THRESHOLD;
        boolean FourRings = topValue > PERCENT_COLOR_THRESHOLD;

        if (OneRing && FourRings) {
            location = RingQuantity.FOUR;
            telemetry.addData("Ring Quantity", "Four");
        }
        else if (OneRing) {
            location = RingQuantity.ONE;
            telemetry.addData("Ring Quantity", "One");
        }
        else {
            location = RingQuantity.NONE;
            telemetry.addData("RingQuantity", "None");
        }
        telemetry.update();

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar NotFound = new Scalar(255, 0, 0);
        Scalar Found = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LOW_REC, location == RingQuantity.ONE || location == RingQuantity.FOUR  ? Found : NotFound);
        Imgproc.rectangle(mat, TOP_REC, location == RingQuantity.FOUR ? Found : NotFound);


        return mat;
        }
    }