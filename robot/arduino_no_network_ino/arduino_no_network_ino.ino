
#include <SPI.h>
#include <Servo.h>

Servo servoLeft;
Servo servoRight;

boolean done = true;

void setup() {
  Serial.begin(9600);
  servoRight.attach (11) ;
  servoLeft.attach (10) ;
  
  servoLeft.writeMicroseconds(1700);
  servoRight.writeMicroseconds(1700);  
  
  servoLeft.writeMicroseconds(1500) ;
  servoRight.writeMicroseconds(1500) ;
}

void loop()  {

}  


void forward() {
  Serial.println("forward");
  servoLeft.writeMicroseconds(1300);
  servoRight.writeMicroseconds(1700);
}

void backward() {
  Serial.println("backward");
  servoLeft.writeMicroseconds(1700);
  servoRight.writeMicroseconds(1300);
}

void right() {
  Serial.println("right");
  servoLeft.writeMicroseconds(1700) ; 
  servoRight.writeMicroseconds(1500) ;
}
void left() {
  Serial.println("left");
  servoLeft.writeMicroseconds(1500) ;
  servoRight.writeMicroseconds(1700) ; 
}

void stopeu() {
  Serial.println("stop");
  servoLeft.writeMicroseconds(1500) ;
  servoRight.writeMicroseconds(1500) ;
}


/* ----------------------------------------------------------- */
/* -Servo control value -
/* - -
/* ----------------------------------------------------------- */
