package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.libs.TeamGamepad;
import org.firstinspires.ftc.teamcode.libs.teamUtil;


@TeleOp(name = "findServoPositions", group="Linear Opmode")

public class findServoPositions extends LinearOpMode {
    public static final double MAJOR_INCREMENT = 0.05;
    public static final double MINOR_INCREMENT = 0.01;
    public static final double MINOR_MINOR_INCREMENT = 0.001;
    public static double INITIAL_POS = .5;
    public double currentPosition = INITIAL_POS;
    TeamGamepad gamepad;
    private Servo servo;
    private int port = 0;
    private boolean ch = true; // Start on Control Hub
    public AnalogInput potentiometer;

    private void updateServo()
    {
        String name = "Servo";
        if (ch) {
            name = name+"CH";
        } else {
            name = name+"EH";
        }
        name = name + port;
        servo = hardwareMap.servo.get(name);
        if (!ch && port == 4) {
            ServoControllerEx flickerControl = (ServoControllerEx) servo.getController();
            int flickerPort = servo.getPortNumber();
            PwmControl.PwmRange flickerRange = new PwmControl.PwmRange(500, 2500);
            flickerControl.setServoPwmRange(flickerPort, flickerRange);
        }
        servo.setPosition(INITIAL_POS);
        currentPosition = INITIAL_POS;
        if (port < 4) {
            String potName = "Pot";
            if (ch) {
                potName = potName + "CH";
            } else {
                potName = potName + "EH";
            }
            potName = potName + port;
            potentiometer = hardwareMap.analogInput.get(potName);
        } else {
            potentiometer=null;
        }

    }

    public void runOpMode() {
        teamUtil.init(this); // Don't use, since it will try to load the Blinkin instead of Servo 0 on the EH
        gamepad = new TeamGamepad();
        gamepad.initilize(true);
        waitForStart();
        updateServo();

        while (opModeIsActive()) {
            gamepad.loop();
            if (gamepad.wasUpPressed() && (currentPosition < 1)) {
                currentPosition = currentPosition + MAJOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasDownPressed() && (currentPosition > 0)) {
                currentPosition = currentPosition - MAJOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasLeftPressed() && (currentPosition < 1)) {
                currentPosition = currentPosition + MINOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasRightPressed() && (currentPosition > 0)) {
                currentPosition = currentPosition - MINOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasBPressed()){
                servo.setPosition(0);
            }
            if (gamepad.wasRightTriggerPressed()){
                servo.setPosition(1);
            }
            if (gamepad.wasRightBumperPressed() && (currentPosition < 1)) {
                currentPosition = currentPosition + MINOR_MINOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasLeftBumperPressed() && (currentPosition > 0)) {
                currentPosition = currentPosition - MINOR_MINOR_INCREMENT;
                servo.setPosition(currentPosition);
            }
            if (gamepad.wasXPressed() ){
                ch = !ch;
                updateServo();
            }
            if (gamepad.wasYPressed() ){
                port++;
                if (port == 6) {
                    port = 0;
                }
                updateServo();
            }
            if (gamepad.wasAPressed() ){
                port--;
                if (port == -1) {
                    port = 5;
                }
                updateServo();
            }
            telemetry.addLine("Hub: " + (ch ? "Control" : "Expansion"));
            telemetry.addData("Port: ", port);
            telemetry.addData("Position: ", servo.getPosition());
            telemetry.addLine("Potentiometer: " + (((potentiometer==null)?"NONE": potentiometer.getVoltage())));
            telemetry.update();
        }
    }
}



