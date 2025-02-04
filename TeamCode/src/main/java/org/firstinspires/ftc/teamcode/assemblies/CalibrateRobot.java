package org.firstinspires.ftc.teamcode.assemblies;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.libs.teamUtil;

@TeleOp(name = "CalibrateRobot", group = "LinearOpMode")
public class CalibrateRobot extends LinearOpMode {

    Robot robot;


    public void runOpMode() {
        teamUtil.init(this);


        robot = new Robot();
        robot.initialize();
        robot.initCV(false);// TODO: false for competition


        telemetry.addLine("Calibrating");
        telemetry.update();

        robot.calibrate();
        teamUtil.justRanCalibrateRobot = true;

        waitForStart();
        telemetry.addLine("Calibrate Done");
        telemetry.update();
    }
}

