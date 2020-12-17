package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Scrimmage TeleOp", group = "")
public class VedantTeleOpCode extends LinearOpMode {

    private static int BUTTON_DELAY = 750;

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;




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
        ElapsedTime timeSinceLastPress = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        waitForStart();


        if (opModeIsActive()) {
            double wheelsPowerFactor = 0.6;
            timeSinceLastPress.reset();
            while (opModeIsActive()) {
                double driveFrontLeft = -gamepad1.a;
                double driveFrontRight = gamepad1.b;
                double driveBackLeft = -gamepad1.x;
                double driveBackRight = gamepad1.y;


                telemetry.addData("Rotate Value", gamepad1.right_stick_x * wheelsPowerFactor);
                frontLeft.setPower(driveFrontLeft);
                backLeft.setPower(driveBackLeft);
                frontRight.setPower(-(driveFrontRight)
                        backRight.setPower(-(driveBackRight));

                //double v = ((-1.0f/2.0f)*gamepad2.left_stick_y)+(1.0f/2.0f);
                //loader.setPosition(v);
                //telemetry.addData("servo", v);





            }

            if (gamepad1.dpad_down && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){
                if (wheelsPowerFactor > 0)
                    wheelsPowerFactor -= 0.1;
                timeSinceLastPress.reset();

            }
            telemetry.addData("change number", 1.1);
            telemetry.addData("Front Left Motor Driving" driveFrontLeft);
            telemetry.addData("Front Right Motor Driving" driveFrontRight);
            telemetry.addData("Back Left Motor Driving" driveBackLeft);
            telemetry.addData("Back Right Motor Driving" driveBackRight);
            telemetry.update();
        }
    }
}
}
