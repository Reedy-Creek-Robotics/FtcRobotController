package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name = "Auto", group = "Mechanum")
public class Auto extends LinearOpMode {

    DcMotor frontLeft,backLeft,frontRight,backRight;
    Servo grabber,rotator;
    DcMotor lifter;
    ElapsedTime t;
    int numRings;
    double open, close;
    //public static double TICKS_PER_CM = 17.1; // 17.112 tics/cm traveled(regular)
    public static double TICKS_PER_CM = 17.112; // 17.83 tics/cm traveled(Strafer)
    public static double WHEEL_POWER = 0.6;
    public static double CORRECTION = 1.0;
    public static double ROTATION_CORRECTION = (62/90);
    //Ticks per revolution = 537.6(same for both)
    //wheel size is 100mm and circumfrence ~31.415 cm(regular)
    //wheel size is 96mm and circumference~30.15 cm(strafer chassis)

    public void runOpMode() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        grabber = hardwareMap.servo.get("grabber");
        rotator = hardwareMap.servo.get("rotator");
        lifter = hardwareMap.dcMotor.get("lifter");
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        open = 0.17;
        close = 0.5;
        grabber.setPosition(close);
        ImageUtility iu = new ImageUtility();
        iu.init(telemetry, hardwareMap);

        waitForStart();
        t = new ElapsedTime();

        moveStraight(100, WHEEL_POWER);
        sleep(500);
        numRings = iu.getRings();
        System.out.println("Number of Rings:" + numRings);
        MoveWobble();
        //shoot
        strafeLeft(95, WHEEL_POWER);
        sleep(250);
        rotator.setPosition(1);
        moveStraight(-91, WHEEL_POWER);
        strafeRight(5, WHEEL_POWER);
        grabber.setPosition(close);
        //strafeRight(100);
        moveStraight(-30, WHEEL_POWER);

        sleep(5000);
    }
    public void MoveWobble() {
        if(numRings == 0){
            moveStraight(75, WHEEL_POWER);
            grabber.setPosition(open);
            moveStraight(-30, WHEEL_POWER);
            strafeLeft(65, WHEEL_POWER);
        }
        else if(numRings == 1){
            moveStraight(140, WHEEL_POWER);
            sleep(250);
            strafeLeft(65, WHEEL_POWER);
            grabber.setPosition(open);
            moveStraight(-100, WHEEL_POWER);
        }
        else if(numRings == 4){
            moveStraight(200, WHEEL_POWER);
            grabber.setPosition(open);
            moveStraight(-153, WHEEL_POWER);
            strafeLeft(65, WHEEL_POWER);
        }
    }
    public void moveStraight(double distance, double speed) {
        backLeft.setTargetPosition(-(int) (distance * TICKS_PER_CM * CORRECTION)); //ticks
        frontLeft.setTargetPosition(-(int) (distance * TICKS_PER_CM * CORRECTION));
        frontRight.setTargetPosition(-(int) (distance * TICKS_PER_CM * CORRECTION));
        backRight.setTargetPosition(-(int) (distance * TICKS_PER_CM * CORRECTION));
        double wheelpower = speed;
        move(wheelpower);
    }

    public void strafeLeft(double distance, double speed) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        double wheelpower = speed;
        move(wheelpower);
    }
    public void strafeRight(double distance, double speed) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        double wheelpower = speed;
        move(wheelpower);
    }
    public void turnLeft(double distance, double speed) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        double wheelpower = speed;
        move(wheelpower);
    }
    public void turnRight(double distance, double speed) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        double wheelpower = speed;
        //move(wheelpower);
    }
    public void move(double speed){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(WHEEL_POWER );
        backLeft.setPower(WHEEL_POWER  );
        frontRight.setPower(WHEEL_POWER  );
        backRight.setPower(WHEEL_POWER  );

        while (opModeIsActive() && (backRight.isBusy() || backLeft.isBusy() || frontLeft.isBusy() || frontRight.isBusy()))
        {
            telemetry.addData("Time", t.seconds());
            telemetry.addData("encoder-bck-left", backLeft.getCurrentPosition() + " power= " + backLeft.getPower() +  "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-bck-right", backRight.getCurrentPosition() + " power= " + backRight.getPower() +  "  busy=" + backRight.isBusy());
            telemetry.addData("encoder-fwd-left", frontLeft.getCurrentPosition() + " power= " + frontLeft.getPower() +  "  busy=" +frontLeft.isBusy());
            telemetry.addData("encoder-fwd-right", frontRight.getCurrentPosition() + " power= " + frontRight.getPower() +  "  busy=" + frontRight.isBusy());
            telemetry.update();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        //resetStartTime();


    }
}


