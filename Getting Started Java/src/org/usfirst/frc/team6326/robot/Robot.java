package org.usfirst.frc.team6326.robot;

import org.usfirst.frc.team6326.robot.commands.DriveAndShootAutonomous;
import org.usfirst.frc.team6326.robot.commands.DriveForward;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import variables.Joysticks;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive drive = new RobotDrive(0, 1);
	Joystick stick = new Joystick(0);
	Timer timer = new Timer();
	Spark windowMotor;
	Spark climber;
	Encoder left, right;
	DigitalInput limitSwitch = new DigitalInput(9);
	DigitalInput bottomStop = new DigitalInput(8);//These are just random ports.
	DigitalInput topStop = new DigitalInput(7);
	SendableChooser autoChooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture(0);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		windowMotor = new Spark(2);
		climber = new Spark(3);

		left = new Encoder(0, 0, 0);//3 channel encoder

		edu.wpi.first.wpilibj.PowerDistributionPanel pdp = new PowerDistributionPanel();
		//		pdp.getCurrent(channel); //For the climber, you can pull the amperage on the pdp and when it spikes, stop the motor. It can be used as a backup in case your driver doesnt stop it.

		autoChooser = new SendableChooser();
		autoChooser.addDefault("Auto 1", "Auto 1");
		autoChooser.addObject("Auto 2", "Auto 2");
		SmartDashboard.putData("Auto Mode", autoChooser);
	}

	public void updateDashboard()
	{
		//		SmartDashboard.putBoolean("Direction", value);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		switch(autoChooser.getSelected())
		{
		case "Auto 1":
			//code for auto 1 here
			// Drive for 2 seconds
			if (timer.get() < 2.0) {
				drive.drive(-0.5, 0.0); // drive forwards half speed
			} else {
				drive.drive(0.0, 0.0); // stop robot
			}
			break;
		case "Auto 2":
		default:
			//code for auto 2, the default here
			break;
		}


	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		//This is how I reversed the drive. The first case runs the same code as drive.arcadeDrive(stick);, just more open to adjustment.
		if(!stick.getRawButton(Joysticks.B))
			drive.arcadeDrive(stick.getY(), stick.getX());//Drive forwards (same as arcadeDrive(stick))
		else
			drive.arcadeDrive(-stick.getY(), stick.getX());//Drive backwards

		if(stick.getRawButton(Joysticks.A))//Testing code for aligning the window motors.
			windowMotor.set(.5);
		else windowMotor.set(0.0);

		limitSwitch.get();//Gets boolean from limit switch, depending on how you wire it, it may be open or closed.

		//example for limiting drive speed based on a trigger, so you can slow down the robot for aligning. 
		//The more you hold down the left trigger, the slower it will go.
		double driveSpeedModifier = (1-stick.getRawAxis(Joysticks.LEFT_TRIGGER));
		drive.arcadeDrive(stick.getY()*driveSpeedModifier, stick.getX()*driveSpeedModifier);

		//This is what I would do for the gear mechanism if theres is a limit switch on top and bottom:
		if (!(bottomStop.get() || topStop.get()) || (bottomStop.get() && stick.getRawAxis(Joysticks.RIGHT_Y) >= 0)
				|| (topStop.get() && stick.getRawAxis(Joysticks.RIGHT_Y) <= 0))
		{
			windowMotor.set(stick.getRawAxis(Joysticks.RIGHT_Y));
		}

		//toggle example
		boolean isOn = false;
		boolean held = false;

		if(stick.getRawButton(Joysticks.Y) && !held)
		{
			isOn = !isOn;
			held = true;
		}

		if(held && !stick.getRawButton(Joysticks.Y))
		{
			held = false;
		}


		updateDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
