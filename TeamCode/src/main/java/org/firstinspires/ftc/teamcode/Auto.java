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

    DcMotor frontLeft,backLeft, frontRight, backRight;
    Servo grabber;
    //GyroSensor gyro;
    BNO055IMU imu;
    ColorSensor colorSensor;
    Servo backServo;
    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        //imu.initialize(new BNO055IMU.Parameters());

        waitForStart();
        ElapsedTime t = new ElapsedTime();

        grabber.setPosition(0.5);
        while (opModeIsActive()&& t.seconds()<=2.60) {
            setFront(0.5);
            telemetry.addData("Time", t.seconds());

        }

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



