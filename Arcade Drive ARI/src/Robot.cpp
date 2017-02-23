#include <WPILib.h>
//#include <Xbox360.h>
//#include <XboxMap.h>
#include <RobotDrive.h>
#include "ADIS16448_IMU.h"

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */

class Robot: public frc::SampleRobot {
public:
	frc::RobotDrive myRobot { 0, 1 };  // robot drive system
	frc::Joystick stick { 0 };  // only joystick
//public:
	class Spark *LeftSide, *RightSide;	//Spark speed-controller class
	//myRobot.SetInvertedMotor(RobotDrive::0, true);
	//drive.SetInvertedMotor(RobotDrive::kRearRightMotor, true)


public:
	Robot() {
		myRobot.SetExpiration(0.1);
		LeftSide = new Spark(0);
		RightSide = new Spark(1);
		LeftSide->SetInverted(false);
		RightSide->SetInverted(true);
		//myRobot = new RobotDrive(LeftSide,RightSide);
	}

	/**
	 * Runs the motors with tank steering.
	 */
	void OperatorControl() {
		while (IsOperatorControl() && IsEnabled()) {
			/*LeftSide->SetInverted(false);
			RightSide->SetInverted(false);*/
			//drivetrain.TankDrive(joystick.GetY(), joystick.GetRawAxis(5);
			myRobot.TankDrive(stick.GetY(), stick.GetRawAxis(5), stick.); // drive with arcade style (use right stick)
			frc::Wait(0.005);			// wait for a motor update time
		}
	}
};

START_ROBOT_CLASS(Robot)
