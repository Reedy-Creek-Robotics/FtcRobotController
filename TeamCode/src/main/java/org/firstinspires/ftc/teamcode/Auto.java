package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Auto", group = "Mechanum")
public class Auto extends LinearOpMode {

    DcMotor frontLeft,backLeft,frontRight,backRight;
    Servo grabber;
    public static double TICKS_PER_CM = 17.1;// 17.112 tics/cm traveled
    //Ticks per revoltion = 537.6
    //wheel size is 100mm and circumfrence ~31.415 cm

    public void runOpMode() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        grabber = hardwareMap.servo.get("grabber");



        double distance = 180;
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        waitForStart();
        ElapsedTime t = new ElapsedTime();
        frontLeft.setPower(0.25);
        backLeft.setPower(0.25);
        frontRight.setPower(0.25);
        backRight.setPower(0.25);

        while (opModeIsActive())  {
            setFront(1);
            telemetry.addData("Time", t.seconds());
            telemetry.addData("encoder-bck-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-bck-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.addData("encoder-fwd-left", frontLeft.getCurrentPosition() + "  busy=" +frontLeft.isBusy());
            telemetry.addData("encoder-fwd-right", frontRight.getCurrentPosition() + "  busy=" + frontRight.isBusy());
            telemetry.update();
            idle();
        }
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);

        resetStartTime();

    }
    public void setFront(double Front) {
        frontLeft.setPower(-Front);
        backLeft.setPower(-Front);
        frontRight.setPower(Front);
        backRight.setPower(Front);

    }
    public void setBack(double Back) {
        frontLeft.setPower(Back);
        backLeft.setPower(Back);
        frontRight.setPower(-Back);
        backRight.setPower(-Back);
    }
    public void setLeft(double Left) {
        frontLeft.setPower(Left);
        backLeft.setPower(-Left);
        frontRight.setPower(Left);
        backRight.setPower(-Left);
    }
    public void setRight(double Right) {
        frontLeft.setPower(-Right);
        backLeft.setPower(Right);
        frontRight.setPower(-Right);
        backRight.setPower(Right);
    }
    public void setTurnL(double TurnL) {
        frontLeft.setPower(-TurnL);
        backLeft.setPower(-TurnL);
        frontRight.setPower(TurnL);
        backRight.setPower(TurnL);
    }
    public void setTurnR(double TurnR) {
        frontLeft.setPower(TurnR);
        backLeft.setPower(TurnR);
        frontRight.setPower(-TurnR);
        backRight.setPower(-TurnR);
    }


}


