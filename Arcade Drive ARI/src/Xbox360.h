#ifndef XBOX360_H_
#define XBOX360_H_

#include <Joystick.h>
#include "WPILib.h"

//Edit this line to reflect the location of the CustomMath library
//#include "../../CustomMath/CustomMath.h"

//Button Mappings
#include "XboxMap.h"

/**
 * This class is designed to interface with an Xbox 360 controller to allow
 * it to control the robot. It contains functions which will access
 * all of the buttons and throttles on the controller.
 *
 * This class uses the Joystick class to interface with the Drivers Station
 * to get the input from the controller
 *
 * This class relies on the library "CustomMath" which Team 2022 developed
 * to provide a complete math library. Please edit the include line to match
 * its location
 */
class Xbox360 : public Joystick
{
public:
    //Constructor
    explicit Xbox360(UINT32 port);

    //Get Analog Stick Values -1 to 1 (Unchanged)
    double GetLeftX();
    double GetLeftY();
    double GetRightX();
    double GetRightY();

    //Get Analog Trigger Value -1 to 1, left adds, right subtracts
    double GetTriggers();

    //Get Buttons for OI Class
    JoystickButton* GetAButton();
    JoystickButton* GetBButton();
    JoystickButton* GetXButton();
    JoystickButton* GetYButton();
    JoystickButton* GetStartButton();
    JoystickButton* GetBackButton();
    JoystickButton* GetLeftBumper();
    JoystickButton* GetRightBumper();

    //Get Button Values Manually
    bool GetAValue();
    bool GetBValue();
    bool GetXValue();
    bool GetYValue();
    bool GetLeftBumperValue();
    bool GetRightBumperValue();
    bool GetBackValue();
    bool GetStartValue();

    //Custom Get Angle Methods
    double GetLeftAngle(bool reflectCircle);
    double GetRightAngle(bool reflectCircle);

    //Custom Get Magnitude Methods
    double GetLeftMagnitude();
    double GetRightMagnitude();

private:
    float GetRawAxis(UINT32 axis);
    bool GetRawButton(UINT32 button);
    double Limit(double num);
};

#endif
