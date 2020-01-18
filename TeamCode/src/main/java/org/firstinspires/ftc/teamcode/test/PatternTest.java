package org.firstinspires.ftc.teamcode.test;

import org.firstinspires.ftc.teamcode.mode.AutonomousNew;
import org.firstinspires.ftc.teamcode.robot.CompetitionBot;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="PatternTest", group="Test")
public class PatternTest extends AutonomousNew {
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new CompetitionBot(hardwareMap, telemetry);

        waitForStart();

        //sonarPatternTest(telemetry);
        encoderPatternTest(telemetry);
    }
}
