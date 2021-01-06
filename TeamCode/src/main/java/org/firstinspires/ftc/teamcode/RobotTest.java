package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "RobotTest", group = "")
public class RobotTest extends LinearOpMode {

    private static int BUTTON_DELAY = 750;

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    /*public Servo grabber;
    public DcMotor intake;
    public DcMotor shooter;
    public DcMotor lifter;
    public Servo loader;*/

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        /*grabber = hardwareMap.servo.get("grabber");
        intake = hardwareMap.dcMotor.get("intake");
        shooter = hardwareMap.dcMotor.get("shooter");
        lifter = hardwareMap.dcMotor.get("lifter");
        loader = hardwareMap.servo.get("loader");*/


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
                    // Press A to turn it on and off.
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
                    // Press B to turn it on and off.
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
                    // Press X to turn it on and off.
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
                    // Press Y to turn it on and off.
                    if (backRight.getPower() > 0) {
                        backRight.setPower(0);
                    } else {
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        backRight.setPower(0.25);
                    }

                    timeSinceLastPress.reset();

                }
                /*if (gamepad1.dpad_down && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Press DPad Downto turn it on and off.
                    if (shooter.getPower() > 0) {
                        shooter.setPower(0);
                    } else {
                        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        shooter.setPower(0.25);
                    }

                    timeSinceLastPress.reset();

                }
                if (gamepad1.dpad_up && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Press DPad Up to turn it on and off.
                    if (intake.getPower() > 0) {
                        intake.setPower(0);
                    } else {
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        intake.setPower(0.25);
                    }

                    timeSinceLastPress.reset();

                }
                if (gamepad1.dpad_left && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Press DPad Left to turn it on and off.
                    if (grabber.getPosition() > 0) {
                        grabber.setPosition(0);
                    } else {
                        grabber.setPosition(0.25);
                    }

                    timeSinceLastPress.reset();

                }
                if (gamepad1.dpad_right && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Press DPad Right to turn it on and off.
                    if (lifter.getPower() > 0) {
                        lifter.setPower(0);
                    } else {
                        lifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        lifter.setPower(0.25);


                    }
                    timeSinceLastPress.reset();
                }

                if (gamepad1.left_trigger && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    // Press Left Trigger to turn it on and off.
                    if (loader.getPosition() > 0) {
                       loader.setPosition(0);
                    } else {
                        loader.setPosition(0.25);
                    }
                }*/
                telemetry.addData("change number", 1.1);
                telemetry.addData("A - Front Left", frontLeft.getPower()+","+frontLeft.getCurrentPosition());
                telemetry.addData("B - Front Right", frontRight.getPower()+","+frontRight.getCurrentPosition());
                telemetry.addData("X - Back Left", backLeft.getPower()+","+backLeft.getCurrentPosition());
                telemetry.addData("Y - Back Right", backRight.getPower()+","+backRight.getCurrentPosition());
                /*telemetry.addData("DPad Down - Shooter", shooter.getPower()+",");
                telemetry.addData("DPad Up - Intake", intake.getPower()+",");
                telemetry.addData("DPad Left - Grabber", grabber.getPosition()+",");
                telemetry.addData("DPad Right - Lifter", lifter.getPower()+",");
                telemetry.addData("Left Trigger - Loader", loader.getPosition()+",");*/
                telemetry.update();
            }
        }
    }
}