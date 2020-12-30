package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "VedantTeleOpCode", group = "")
public class RobotTest extends LinearOpMode {

    private static int BUTTON_DELAY = 750;

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;


    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        boolean isBPressed = false;
        boolean isAPressed = false;
        ElapsedTime timeSinceLastPress = new ElapsedTime();
        waitForStart();


        if (opModeIsActive()) {
            double wheelsPowerFactor = 0.6;
            timeSinceLastPress.reset();
            while (opModeIsActive()) {


                //double v = ((-1.0f/2.0f)*gamepad2.left_stick_y)+(1.0f/2.0f);
                //loader.setPosition(v);
                //telemetry.addData("servo", v);


                if (gamepad1.a && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Turn the motor at 0.25 power when a button is pressed, and turn it off once a is pressed again.
                    if (frontLeft.getPower() > 0) {
                        frontLeft.setPower(0);
                    } else {
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        frontLeft.setPower(0.25);
                    }

                    timeSinceLastPress.reset();
                }

                if (gamepad1.b && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Turn the motor at 0.25 power when a button is pressed, and turn it off once a is pressed again.
                    if (frontRight.getPower() > 0) {
                        frontRight.setPower(0);
                    } else {
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        frontRight.setPower(0.25);
                    }

                    timeSinceLastPress.reset();
                }
                if (gamepad1.x && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Turn the motor at 0.25 power when a button is pressed, and turn it off once a is pressed again.
                    if (backLeft.getPower() > 0) {
                        backLeft.setPower(0);
                    } else {
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        backLeft.setPower(0.25);
                    }
                    timeSinceLastPress.reset();
                }
                if (gamepad1.y && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Turn the motor at 0.25 power when a button is pressed, and turn it off once a is pressed again.
                    if (backRight.getPower() > 0) {
                        backRight.setPower(0);
                    } else {
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        backRight.setPower(0.25);
                    }

                    timeSinceLastPress.reset();

                }


                telemetry.addData("change number", 1.1);
                telemetry.addData("A - Front Left", frontLeft.getPower()+","+frontLeft.getCurrentPosition());
                telemetry.addData("B - Front Right", frontRight.getPower()+","+frontRight.getCurrentPosition());
                telemetry.addData("X - Back Left", backLeft.getPower()+","+backLeft.getCurrentPosition());
                telemetry.addData("Y - Back Right", backRight.getPower()+","+backRight.getCurrentPosition());
                telemetry.update();
            }
        }
    }
}