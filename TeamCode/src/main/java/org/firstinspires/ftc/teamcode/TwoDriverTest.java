package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Created by NateH on 12/23/17.
 */

public class TwoDriverTest {
    @TeleOp(name="Pushbot: TwoDriver Tank", group="Pushbot")
//@Disabled
    public class PushbotTeleopTank_Iterative extends OpMode {

        /* Declare OpMode members. */
        HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
        // could also use HardwarePushbotMatrix class.
        double          clawOffset  = 0.0 ;                  // Servo mid position
        final double    CLAW_SPEED  = 0.02;                  // sets rate to move servo
        double          colorArm    = 0.0 ;
        final double    colorSpeed  = 0.02;
        double[] powerCurve = {-1, -0.9, -0.6, -0.3, -0.2, -0.15, -0.1, -0.05, -0.01, 0, 0.01, 0.05, 0.1, 0.12, 0.15, 0.2, 0.3, 0.6, 0.9, 1};

        /*
         * Code to run ONCE when the driver hits INIT
         */
        @Override
        public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
            robot.init(hardwareMap);

            // Send telemetry message to signify robot waiting;
            telemetry.addData("Say", "Hello Driver");    //
        }

        /*
         * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
         */
        @Override
        public void init_loop() {
        }

        /*
         * Code to run ONCE when the driver hits PLAY
         */
        @Override
        public void start() {
        }

        /*
         * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
         */
        @Override
        public void loop() {
            float left;
            float right;

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;

            robot.leftDrive.setPower(powerCurve[Math.round(left*10)+10]);
            robot.rightDrive.setPower(powerCurve[Math.round(right*10)+10]);


            // Use gamepad left & right Bumpers to open and close the claw
            if (gamepad2.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad2.left_bumper)
                clawOffset -= CLAW_SPEED;

            if (gamepad2.y)
                colorArm += colorSpeed;
            else if (gamepad2.a)
                colorArm -= colorSpeed;

            if(gamepad1.left_trigger > 0) {
                robot.leftDrive.setPower(1);
                robot.rightDrive.setPower(-1);
            }
            else if (gamepad1.right_trigger > 0) {
                robot.leftDrive.setPower(-1);
                robot.rightDrive.setPower(1);
            }

            // Move both servos to new position.  Assume servos are mirror image of each other.
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            robot.leftClaw.setPosition(HardwarePushbot.MID_SERVO + clawOffset);
            robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

            // Use gamepad buttons to move the arm up (Y) and down (A)

            robot.leftArm.setPower(robot.ARM_UP_POWER * gamepad2.left_stick_y);

            // Send telemetry message to signify robot running;
            telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
        }

        /*
         * Code to run ONCE after the driver hits STOP
         */
            public void stop() {
            }
        }



}
