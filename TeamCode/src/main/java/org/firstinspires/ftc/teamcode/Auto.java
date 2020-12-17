package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Auto", group = "Mechanum")
public class Auto extends LinearOpMode {

    DcMotor frontLeft,backLeft,frontRight,backRight;
    Servo grabber;
    ElapsedTime t;
    //public static double TICKS_PER_CM = 17.1;// 17.112 tics/cm traveled(regular)
    public static double TICKS_PER_CM = 17.83;// 17.112 tics/cm traveled(Strafer)
    public static double WHEEL_POWER = .50;
    public static double CORRECTION = 1;
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

        waitForStart();
        t = new ElapsedTime();
        strafeRight(100);

        //moveForward(10);
        //moveBackward(10);
        //turnLeft(62);//62=90 degrees
        //turnRight(124);
        //turnLeft(124);
        //turnRight(62);
        //strafeRight(10);
        //strafeLeft(10);
        //turnRight(62);
        //strafeLeft(180);
        sleep(5000);
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
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        //move();
    }
    public void turnRight(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
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


