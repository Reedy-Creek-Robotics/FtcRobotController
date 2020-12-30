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
    Servo grabber;
    ElapsedTime t;
    int numRings;
    //public static double TICKS_PER_CM = 17.1;// 17.112 tics/cm traveled(regular)
    public static double TICKS_PER_CM = 17.83;// 17.112 tics/cm traveled(Strafer)
    public static double WHEEL_POWER = 1;
    public static double CORRECTION = 1;
    public static double ROTATION_CORRECTION = (62/90);
    //Ticks per revolution = 537.6(same for both)
    //wheel size is 100mm and circumfrence ~31.415 cm(regular)
    //wheel size is 96mm and circumference~30.15 cm(strafer chassis)

    public void runOpMode() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        ImageUtility iu = new ImageUtility();
        iu.init(telemetry, hardwareMap);

        waitForStart();
        t = new ElapsedTime();

        moveStraight(100);
        sleep(500);
        numRings = iu.getRings();
        System.out.println("Number of Rings:" + numRings);
        MoveWobble();
        //shoot
        strafeLeft(65);
        moveStraight(-113);
        //grab wobble
        strafeRight(105);
        moveStraight(-40);

        sleep(5000);
    }
    public void MoveWobble() {
        if(numRings == 0){
            moveStraight(75);
            //drop wobble
            moveStraight(-30);
            strafeLeft(65);
        }
        else if(numRings == 1){
            moveStraight(140);
            strafeLeft(65);
            //drop wobble
            moveStraight(-100);
        }
        else if(numRings == 4){
            moveStraight(200);
            //drop wobble
            moveStraight(-153);
            strafeLeft(65);
        }
    }
    public void moveStraight(double distance) {
        //distance = distance + 1.5;
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION)); //negative
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION)); //negative
        move();
    }

    public void strafeLeft(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        move();
    }
    public void strafeRight(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        move();
    }
    public void turnLeft(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        //move();
    }
    public void turnRight(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        //move();
    }
    public void move(){
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


