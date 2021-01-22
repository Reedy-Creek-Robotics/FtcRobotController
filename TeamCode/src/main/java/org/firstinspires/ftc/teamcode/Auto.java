package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;



import java.util.List;


@Autonomous(name = "Auto", group = "Mechanum")
public class Auto extends LinearOpMode {

    DcMotor frontLeft,backLeft,frontRight,backRight;
    Servo grabber,rotator;
    DcMotor lifter, shooter;
    DistanceSensor distanceLeft, distanceBack;
    ElapsedTime t;
    int numRings;
    double openWobble, closeWobble;
    //public static double TICKS_PER_CM = 17.1; // 17.112 tics/cm traveled(regular)
    public static double TICKS_PER_CM = 17.112; // 17.83 tics/cm traveled(Strafer)
    public static double WHEEL_POWER = 0.6;
    public static double CORRECTION = 1.0;
    public static double ROTATION_CORRECTION = (62/90);
    public static double WOBBLE_OUT = 0;
    public static double WOBBLE_IN = 1;
    //Ticks per revolution = 537.6(same for both)
    //wheel size is 100mm and circumference ~31.415 cm(regular)
    //wheel size is 96mm and circumference~30.15 cm(strafer chassis)

    public void runOpMode() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        grabber = hardwareMap.servo.get("grabber");
        rotator = hardwareMap.servo.get("rotator");
        lifter = hardwareMap.dcMotor.get("lifter");
        shooter = hardwareMap.dcMotor.get("shooter");
        distanceLeft = hardwareMap.get(DistanceSensor.class,"distanceLeft");
        distanceBack = hardwareMap.get(DistanceSensor.class,"distanceBack");
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        openWobble = 0.17;
        closeWobble = 0.5;
        grabber.setPosition(closeWobble);
        ImageUtility iu = new ImageUtility();
        iu.init(telemetry, hardwareMap);

        waitForStart();
        t = new ElapsedTime();

        moveStraight(100, WHEEL_POWER);
        sleep(500);
        numRings = iu.getRings(); //iu.getRings();
        log("Number of Rings:", numRings);
        MoveWobble();
        //shooter.setPower(1);
        //sleep(0);//fill in time
        //shooter.setPower(0);
        //strafeLeft(95, WHEEL_POWER);

        moveWithDistance(54, 0);
        sleep(250);

        moveStraight(-91, WHEEL_POWER);
        strafeRight(5, WHEEL_POWER);
        grabber.setPosition(closeWobble);
        //strafeRight(100);
        /*moveStraight(-30, WHEEL_POWER);

        moveStraight(-132, WHEEL_POWER);
        moveStraight(51, WHEEL_POWER);
        strafeRight(20, WHEEL_POWER);
        grabber.setPosition(closeWobble);
        //strafeRight(100);
        sleep(200);*/
        //moveStraight(-30);



        sleep(5000);
    }
    public void MoveWobble() {
        if(numRings == 0){
            moveStraight(75, WHEEL_POWER);
            log("Move Wobble:", true);
            strafeLeft(25, WHEEL_POWER);
            rotator.setPosition(WOBBLE_OUT);
            sleep(1000);
            grabber.setPosition(openWobble);
            sleep(1000);
            strafeLeft(40, WHEEL_POWER);
            moveStraight(-45, WHEEL_POWER);

        }
        else if(numRings == 1){
            moveStraight(140, WHEEL_POWER);
            sleep(250);
            strafeLeft(100, WHEEL_POWER);
            rotator.setPosition(WOBBLE_OUT);
            sleep(1000);
            grabber.setPosition(openWobble);
            sleep(100);
            moveStraight(-100, WHEEL_POWER);
            strafeRight(35, WHEEL_POWER);
        }
        else if(numRings == 4){
            moveStraight(200, WHEEL_POWER);
            strafeLeft(25, WHEEL_POWER);
            rotator.setPosition(WOBBLE_OUT);
            sleep(1000);
            grabber.setPosition(openWobble);
            sleep(1000);
            strafeLeft(40, WHEEL_POWER);
            moveStraight(-153, WHEEL_POWER);
        }
    }
    public void moveWithDistance(double wantedDistanceX, double wantedDistanceY){
        double currentDistanceX = distanceLeft.getDistance(DistanceUnit.CM);
        log("Before Move Distance", currentDistanceX);
        telemetry.update();
        //double currentDistanceY = distanceBack.getDistance(DistanceUnit.CM);
        double distanceX = currentDistanceX - wantedDistanceX;
        log("Strafe Distance", distanceX);
        telemetry.update();
        //double distanceY = wantedDistanceY - currentDistanceY;
        strafeLeft(distanceX, WHEEL_POWER);
        log("After Move Distance",  distanceLeft.getDistance(DistanceUnit.CM));
        telemetry.update();
        //moveStraight(distanceY, WHEEL_POWER);
    }
    
    public void moveStraight(double distance, double speed) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * CORRECTION));
        move();
    }

    public void strafeLeft(double distance, double speed) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        move();
    }
    public void strafeRight(double distance, double speed) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        move();
    }
    public void turnLeft(double distance, double speed) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        move();
    }
    public void turnRight(double distance, double speed) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * ROTATION_CORRECTION));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM * ROTATION_CORRECTION));
        move();
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

        frontLeft.setPower(WHEEL_POWER);
        backLeft.setPower(WHEEL_POWER);
        frontRight.setPower(WHEEL_POWER);
        backRight.setPower(WHEEL_POWER);

        while (opModeIsActive() && (backRight.isBusy() || backLeft.isBusy() || frontLeft.isBusy() || frontRight.isBusy()))
        {
            /*telemetry.addData("Time", t.seconds());
            telemetry.addData("encoder-bck-left", backLeft.getCurrentPosition() + " power= " + backLeft.getPower() +  "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-bck-right", backRight.getCurrentPosition() + " power= " + backRight.getPower() +  "  busy=" + backRight.isBusy());
            telemetry.addData("encoder-fwd-left", frontLeft.getCurrentPosition() + " power= " + frontLeft.getPower() +  "  busy=" +frontLeft.isBusy());
            telemetry.addData("encoder-fwd-right", frontRight.getCurrentPosition() + " power= " + frontRight.getPower() +  "  busy=" + frontRight.isBusy());
            telemetry.addData("distanceLeft", distanceLeft.getDistance(DistanceUnit.CM));

            telemetry.update();*/

        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        //resetStartTime();


    }

    protected void log(String caption, Object value) {
        telemetry.addData(caption, value);
        RobotLog.a("[BBTC] " + caption + ": " + value);
    }
}


