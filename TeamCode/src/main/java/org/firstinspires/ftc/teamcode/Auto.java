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

    DcMotor m1, m2, m3, m4;
    Servo grabber;
    //GyroSensor gyro;
    BNO055IMU imu;
    ColorSensor colorSensor;
    Servo backServo;
    public void runOpMode() {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabber = hardwareMap.servo.get("grabber");


        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());


        waitForStart();
        ElapsedTime t = new ElapsedTime();

        grabber.setPosition(0.5);
        while (opModeIsActive()&& t.seconds()<=2.60) {
            setFront(1);
            telemetry.addData("Time", t.seconds());

        }

    }

    public void setFront(double Front) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(Front);
        m2.setPower(Front);
        m3.setPower(Front);
        m4.setPower(Front);

    }
    public void setBack(double Back) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(-Back);
        m2.setPower(-Back);
        m3.setPower(-Back);
        m4.setPower(-Back);
    }
    public void setLeft(double Left) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(Left);
        m2.setPower(-Left);
        m3.setPower(Left);
        m4.setPower(-Left);
    }
    public void setRight(double Right) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(-Right);
        m2.setPower(Right);
        m3.setPower(-Right);
        m4.setPower(Right);
    }
    public void setTurnL(double TurnL) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(-TurnL);
        m2.setPower(-TurnL);
        m3.setPower(TurnL);
        m4.setPower(TurnL);
    }
    public void setTurnR(double TurnR) {
        m1 = hardwareMap.dcMotor.get("frontLeft");
        m2 = hardwareMap.dcMotor.get("backLeft");
        m3 = hardwareMap.dcMotor.get("frontRight");
        m4 = hardwareMap.dcMotor.get("backRight");
        m1.setPower(TurnR);
        m2.setPower(TurnR);
        m3.setPower(-TurnR);
        m4.setPower(-TurnR);
    }

}



