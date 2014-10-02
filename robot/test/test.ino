#include <Servo.h>                           // Include servo library
 
Servo servoLeft;                             // Declare left servo
Servo servoRight;

void setup()                                 // Built in initialization block
{
  servoLeft.attach(13);                      // Attach left signal to pin 13
  servoLeft.writeMicroseconds(1700);  // 1.3 ms full speed clockwise
  servoLeft.writeMicroseconds(1500);
  servoRight.attach(12);
  servoRight.writeMicroseconds(1700);
  servoRight.writeMicroseconds(1500);
}  
 
void loop()                                  // Main loop auto-repeats
{                                            // Empty, nothing needs repeating
}
