package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mode.AutonomousNew;
import org.firstinspires.ftc.teamcode.robot.CompetitionBot;

@Autonomous(name="LineDetectTest", group="Test")
public class LineDetectTest extends AutonomousNew {
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new CompetitionBot(hardwareMap, telemetry);

        waitForStart();
        while (opModeIsActive()) {
            detectLineAndStop(false, .2, 5, telemetry);
        }
    }
}
