package org.firstinspires.ftc.teamcode.imu;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Learn IMU")
public class IMUOpMode extends LinearOpMode {
    DcMotor backLeft, backRight, frontLeft, frontRight;

    private BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle;

    @Override
    public void runOpMode() throws InterruptedException {
        // setup so we manually clear telemetry, it will keep appending to screen
        telemetry.setAutoClear(false);

        // INIT
        initMotors();  // REPLACE this with whatever you use to initialize your motors
        initIMU();

        log("Mode", "waiting for start");
        waitForStart();

        // AFTER START

        // ACTION LOOP
        while (opModeIsActive()) {
            log("Heading (degrees)", Double.toString(getHeading(AngleUnit.DEGREES)), true);
        }
    }

    /**************************************
     * Initialization and Basic Heading Functions
     **************************************/
    public void initIMU() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.clear();
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();
    }

    public double getHeading(AngleUnit angleUnit) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit);
        return angles.firstAngle;
    }

    /**************************************
     * Advanced Angle and Rotate Functions
     **************************************/
    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    //ROTATE FUNCTION

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     *
     * @param degrees Degrees to turn, + is left - is right
     */
    public void rotate(int degrees, double rotate) {
        DcMotor[] motorList = {backLeft, backRight, frontLeft, frontRight};
        for (DcMotor m : motorList) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        double flPower, blPower, frPower, brPower;

        // restart imu movement tracking.
        //resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn right.
            flPower = rotate;
            blPower = rotate;
            frPower = rotate;
            brPower = rotate;
        } else if (degrees > 0) {   // turn left.
            flPower = -rotate;
            blPower = -rotate;
            frPower = -rotate;
            brPower = -rotate;
        } else return;

        // set power to rotate.
        setMotorSpeeds(1, flPower, blPower, frPower, brPower);

        // rotate until turn is completed.
        if (degrees < 0) {
            log("rotate", "turning clockwise (right, -)");
            // On right turn we have to get off zero first.
            double angle = getAngle();
            while (opModeIsActive() && angle == 0) {
                log("rotate clockwise,0", "getAngle=" + getAngle());
                angle = getAngle();
            }

            while (opModeIsActive() && angle > degrees) {
                log("rotate clockwise, not 0", "getAngle=" + getAngle());
                angle = getAngle();
            }
        } else {   // left turn.
            log("rotate", "turning counter-clockwise (left, +)");
            while (opModeIsActive() && getAngle() < degrees) {}
        }

        // turn the motors off.
        setMotorSpeeds(1, 0, 0, 0, 0);

        log("rotate", "end of method angle: " + getAngle());

        // reset angle tracking on new heading.
        resetAngle();
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }


    /**************************************
     * Other functions
     **************************************/
    protected void initMotors() {
        // read all the motors from the map
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontRight");
    }

    public void setMotorSpeeds(double speedFactor, double flPower, double blPower, double frPower, double brPower) {
        if (speedFactor > 1) {
            speedFactor = 1;
        } else if (speedFactor < 0) {
            speedFactor = 0;
        }
        //log("power bl fl br fr: ", blPower + ", " + flPower + ", " + brPower + ", " + frPower);
        telemetry.addData("power: ", String.format("bl %.5f fl %.5f br %.5f fr %.5f",blPower,flPower,brPower,frPower));
        telemetry.update();

        frontLeft.setPower(speedFactor * flPower);
        backRight.setPower(speedFactor * blPower);
        frontRight.setPower(speedFactor * frPower);
        backLeft.setPower(speedFactor * brPower);
    }

    protected void log(String caption, String message) {
        log(caption, message, false);
    }

    protected void log(String caption, String message, boolean clear) {
        if( clear ) {
            telemetry.clear();
        }
        telemetry.addData(caption, message);
        telemetry.update();
        RobotLog.a(getRuntime() + " [BBTC]" + caption + ": " + message);
    }
}
