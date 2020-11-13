package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.DecimalFormat;

@TeleOp(name = "Scrimmage TeleOp", group = "")
public class ScrimmageTeleOp extends LinearOpMode {

    private static int BUTTON_DELAY = 750;

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private Servo grabber;
    private DcMotor lifter;
    private DcMotor intake;
    private DcMotor shooter;
    private Servo loader;




    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        grabber = hardwareMap.servo.get("grabber");
        lifter = hardwareMap.dcMotor.get("lifter");
        intake = hardwareMap.dcMotor.get("intake");
        shooter = hardwareMap.dcMotor.get("shooter");
        loader = hardwareMap.servo.get("loader");

        boolean isBPressed = false;
        boolean isAPressed = false;
        ElapsedTime timeSinceLastPress = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        waitForStart();
        grabber.setPosition(0.5);

        if (opModeIsActive()) {
            double wheelsPowerFactor = 0.6;
            timeSinceLastPress.reset();
            while (opModeIsActive()) {
                double drive = -gamepad1.left_stick_y * wheelsPowerFactor;
                double strafe = -(gamepad1.left_stick_x * wheelsPowerFactor);
                double rotate = gamepad1.right_stick_x * wheelsPowerFactor;
                telemetry.addData("Rotate Value", gamepad1.right_stick_x * wheelsPowerFactor);
                frontLeft.setPower(drive - (strafe - rotate));
                backLeft.setPower(drive + strafe + rotate);
                frontRight.setPower(-(drive + (strafe - rotate)));
                backRight.setPower(-(drive - (strafe + rotate)));

                double v = ((-1.0f/2.0f)*gamepad2.left_stick_y)+(1.0f/2.0f);
                loader.setPosition(v);
                telemetry.addData("servo", v);

                if (gamepad2.a && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                    if (grabber.getPosition() <= 0.3){
                        grabber.setPosition(0.5);
                        timeSinceLastPress.reset();
                    }
                    else {
                        grabber.setPosition(0.2);
                        timeSinceLastPress.reset();
                    }
                }

                lifter.setPower(gamepad2.right_stick_y);

                if (gamepad2.dpad_up){
                    lifter.setPower(1);
                }
                else if (gamepad2.dpad_down){
                    lifter.setPower(-1);
                }
                else {
                    lifter.setPower(0);
                }

                if (gamepad2.x && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                    if (shooter.getPower() == 0){
                        shooter.setPower(-1);
                        timeSinceLastPress.reset();
                    }
                    else {
                        shooter.setPower(0);
                        timeSinceLastPress.reset();
                    }
                }

                if (gamepad2.y && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {
                    if (loader.getPosition() == 0) {
                        loader.setPosition(.5);
                    }
                    else {
                        loader.setPosition(0);
                    }
                    timeSinceLastPress.reset();
                }

                if (gamepad2.b && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                    if (intake.getPower() == 0){
                        intake.setPower(0.75);
                        timeSinceLastPress.reset();
                    }
                    else {
                        intake.setPower(0);
                        timeSinceLastPress.reset();
                    }

                }

                intake.setPower(-gamepad2.left_stick_y);

                if (gamepad1.dpad_up && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                    if (wheelsPowerFactor < 1) {
                        wheelsPowerFactor += 0.1;
                        timeSinceLastPress.reset();
                    }

                }

                if (gamepad1.dpad_down && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                    if (wheelsPowerFactor > 0)
                        wheelsPowerFactor -= 0.1;
                    timeSinceLastPress.reset();

                }
                telemetry.addData("change number", 1.1);
                telemetry.addData("Drive Power:", drive);
                telemetry.addData("Wheel power factor:", df2.format(wheelsPowerFactor));
                telemetry.addData("Intake power factor:", df2.format(intake.getPower()));
                telemetry.update();
            }
        }
    }
}
