package org.firstinspires.ftc.teamcode.RoadRunner.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.CompetitionBot;
import org.firstinspires.ftc.teamcode.vision.RingsOpenCV;

@Config
@Autonomous(name="RedMainAuto", group="Autonomous")
public class RedMainAuto extends LinearOpMode {
    public static double START_X = -60.375;
    public static double START_Y = -20.75;
    public static double BACK_DIST = 48;
    public static double A_X = 0;
    public static double A_Y = -48;
    public static double B_X = 30;
    public static double B_Y = START_Y;
    public static double C_X = 48;
    public static double C_Y = -48;

    @Override
    public void runOpMode() throws InterruptedException {
        CompetitionBot robot = new CompetitionBot(hardwareMap, telemetry);
        RingsOpenCV vision = new RingsOpenCV(true, hardwareMap, telemetry);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Thread servoThread = new Thread("servoThread");

        Pose2d startingPose = new Pose2d(START_X, START_Y, Math.toRadians(180));
        drive.setPoseEstimate(startingPose);

        waitForStart();

        if (isStopRequested()) return;

        int numRings = vision.getNumberRings();

        robot.FlywheelMotor.setPower(.95);
        sleep(1000);
        robot.ringFeedServo.setPosition(robot.FEED_OPEN);
        sleep(1000);
        robot.FlywheelMotor.setPower(0);
        robot.ringFeedServo.setPosition(robot.FEED_CLOSED);

        numRings = vision.getNumberRings();

        Trajectory traj = drive.trajectoryBuilder(startingPose)
                .back(BACK_DIST)
                .build();

        drive.followTrajectory(traj);

        if (numRings == 0) {
            traj = drive.trajectoryBuilder(traj.end(), true)
                    .splineTo(new Vector2d(A_X, A_Y), Math.toRadians(270))
                    .build();

            drive.followTrajectory(traj);

        } else if (numRings == 1) {
            traj = drive.trajectoryBuilder(traj.end(), true)
                    .splineTo(new Vector2d(B_X, B_Y), Math.toRadians(270))
                    .build();

            drive.followTrajectory(traj);

        } else {
            traj = drive.trajectoryBuilder(traj.end(), true)
                    .splineTo(new Vector2d(C_X, C_Y), Math.toRadians(270))
                    .build();

            drive.followTrajectory(traj);

        }

        sleep(500);
        robot.armRotateServo.setPosition(robot.ARM_MID);
        robot.grabberServo.setPosition(robot.GRABBER_OPEN);
    }
}

class ServoThread implements Runnable {
    private CompetitionBot compRobot;

    public ServoThread(CompetitionBot robot) {
        compRobot = robot;
    }

    public void run() {
        double startTime = System.currentTimeMillis();
        compRobot.armRotateServo.setPosition(compRobot.ARM_MID);
        compRobot.grabberServo.setPosition(compRobot.GRABBER_OPEN);
    }
}