//Summary of controls
    //Drivetrain
        //vertical movement = the left stick on controller 1(moving on the y-axis)
        //Strafing = the left stick on controller 1(moving on the x-axis)
        //Rotating = the right stick on controller 1(moving on the x-axis)
        //press up on the dpad to raise the power by 0.1 and down to lower it by 0.1 (on controller 1, default power=0.6)

    //Wobble
        //grabber = pressing a on controller 2(this will close the arm to open them press a again)
        //lifter = press up on the dpad to raise the lifter and down to lower it (on controller 2)
        //rotator =

    //shooter
        //shooter = pressing x on controller 2(this will turn the shooter on, press x again to turn it off)
        //loader = pressing y on controller 2(this will push the ring, press y again to let another ring in)

    //intake
        //intake = pressing b on controller 2(this will turn the intake on, press b again to turn it off)
        //conveyor =

    //occupied controls
        //Controller 1
            //left stick x-axis
            //left stick x-axis
            //right stick y-axis
            //dpad up
            //dpad down

        //Controller 2
            //a
            //b
            //x
            //y
            //dpad up
            //dpad down


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
    private Servo rotator;
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
        rotator = hardwareMap.servo.get("rotator");
        lifter = hardwareMap.dcMotor.get("lifter");
        //intake = hardwareMap.dcMotor.get("intake");
        //shooter = hardwareMap.dcMotor.get("shooter");
        //loader = hardwareMap.servo.get("loader");

        boolean isBPressed = false;
        boolean isAPressed = false;
        ElapsedTime timeSinceLastPress = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        waitForStart();
        grabber.setPosition(0.5);
        rotator.setPosition(0);

        if (opModeIsActive()) {
            double wheelsPowerFactor = 0.6;
            timeSinceLastPress.reset();

            while (opModeIsActive()) {
                //Drivetrain
                double drive = -gamepad1.left_stick_y * wheelsPowerFactor;//vertical movement = the left stick on controller one(moving on the y-axis)
                double strafe = -(gamepad1.left_stick_x * wheelsPowerFactor);//Strafing = the left stick on controller 1(moving on the x-axis)
                double rotate = gamepad1.right_stick_x * wheelsPowerFactor;//Rotating = the right stick on controller 1(moving on the x-axis)
                telemetry.addData("Rotate Value", gamepad1.right_stick_x * wheelsPowerFactor);
                frontLeft.setPower(drive - (strafe - rotate));
                backLeft.setPower(drive + strafe + rotate);
                frontRight.setPower(-(drive + (strafe - rotate)));
                backRight.setPower(-(drive - (strafe + rotate)));

                //double v = ((-1.0f/2.0f)*gamepad2.left_stick_y)+(1.0f/2.0f);
                //loader.setPosition(v);
                //telemetry.addData("servo", v);
                if (gamepad1.dpad_up && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){//press up on the dpad to raise the power by 0.1 and down to lower it by 0.1 (on controller 1, default power=0.6)
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

                //wobble
                if (gamepad2.a && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){//grabber = pressing a on controller 2(this will close the arm to open them press a again)
                    if (grabber.getPosition() <= 0.3){
                        grabber.setPosition(0.5);
                        timeSinceLastPress.reset();
                    }
                    else {
                        grabber.setPosition(0.2);
                        timeSinceLastPress.reset();
                    }
                }

                if (gamepad2.b && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){//grabber = pressing a on controller 2(this will close the arm to open them press a again)
                    if (rotator.getPosition() != 1){
                        rotator.setPosition(1);
                        timeSinceLastPress.reset();
                    }
                    else {
                        rotator.setPosition(0);
                        timeSinceLastPress.reset();
                    }
                }

                // lifter
                if (gamepad2.dpad_up){//press up on the dpad to raise the lifter and down to lower it (on controller 2)
                    lifter.setPower(wheelsPowerFactor);
                }
                else if (gamepad2.dpad_down){
                    lifter.setPower(-wheelsPowerFactor);
                }
                else {
                    lifter.setPower(0);
                }

                double lifter = (gamepad2.right_stick_y - 0.5) * 2;

                //shooter
                /*
                if (gamepad2.x && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){//shooter = pressing x on controller 2(this will turn the shooter on, press x again to turn it off)
                    if (shooter.getPower() == 0){
                        shooter.setPower(-1);
                        timeSinceLastPress.reset();
                    }
                    else {
                        shooter.setPower(0);
                        timeSinceLastPress.reset();
                    }
                }
                 */
                /*
                if (gamepad2.y && timeSinceLastPress.milliseconds() >= BUTTON_DELAY) {//loader = pressing y on controller 2(this will push the ring, press y again to let another ring in)
                    if (loader.getPosition() < 0.3) {
                        loader.setPosition(.5);
                    }
                    else {
                        loader.setPosition(0.2);
                    }
                    timeSinceLastPress.reset();
                }
                */


                //intake
                /*
                if (gamepad2.b && timeSinceLastPress.milliseconds() >= BUTTON_DELAY){//intake = pressing b on controller 2(this will turn the intake on, press b again to turn it off)
                    if (intake.getPower() == 0){
                        intake.setPower(0.75);
                        timeSinceLastPress.reset();
                    }
                    else {
                        intake.setPower(0);
                        timeSinceLastPress.reset();
                    }

                }
                */


                //intake.setPower(-gamepad2.left_stick_y);


                telemetry.addData("change number", 1.1);
                telemetry.addData("Drive Power:", drive);
                telemetry.addData("Wheel power factor:", df2.format(wheelsPowerFactor));
                //telemetry.addData("Intake power factor:", df2.format(intake.getPower()));
                telemetry.update();
            }
        }
    }
}
