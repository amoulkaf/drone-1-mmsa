#include <WiFly.h>
#include <SPI.h>
#include <Servo.h>

String httpRequest;
boolean msgAvailable=false;

Servo servoLeft;
Servo servoRight;

WiFlyServer server(8080);


void setup() {
  Serial.begin(9600);

  /* ----------------------------------------------------------- */
  /* -Create the ad Hoc network used for remote control -
  /* ----------------------------------------------------------- */
  Serial.println("**************Starting WiFly**************");
  // Enable Adhoc mod
  WiFly.begin(true);
  Serial.println("Client Arduino");
  
  if (WiFly.joinAdHocNetwork("ARDRONE","192.168.1.50","255.255.255.0")){
    Serial.println("Association established");
  }
  else 
  {
    Serial.println("Association failed.");
    while (1) {
      // Hang on failure.
    }
  }

  Serial.println("Connecting to the ad-hoc network...");

  /* ----------------------------------------------------------- */


  Serial.println("Starting Server...");
  server.begin();
  Serial.println("Server started, waiting for client.");


  /* ----------------------------------------------------------- */
  /* -Set up Servo PINs (TO DO : plug the servo on PIN 3 & 4) -
  /* ----------------------------------------------------------- */
  pinMode(2,OUTPUT);
  pinMode(3,OUTPUT);
  servoRight.attach (3) ;
  servoLeft.attach (2) ; 
  /* ----------------------------------------------------------- */
}

void loop()  {
 
  
  /* ----------------------------------------------------------- */
  /* -Handle HTTP Request when a client is connected for remote control -
  /* -Call maneuverControl() to handle the control order -
  /* ----------------------------------------------------------- */
  
  WiFlyClient client = server.available();
  if(client){    
    while(client.connected()){ 
      if (client.available()){
        char c = client.read();//read data from client

        if (msgAvailable){
          if(c!='\n'&&c!='\r')
            httpRequest+=c;
        }
        if (c=='='){
          msgAvailable=true; 
        }
        if (c=='\n'){ 
          //header
          client.println(F("HTTP/1.1 200 OK"));
          client.println(F("Content-Type: text/html"));
          client.println(F("Connnection: close"));
          client.println();
          //body
          client.println(F("<!DOCTYPE html>"));
          client.println(F("<html>"));
          client.println(F("<body>"));
          client.println(F("<h1>Arduino Control Test Page</h1>"));
          client.println(F("<hr>"));
          client.println(F("<form name=\"input\" action=\"\" method=\"get\">"));
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Forward\">"));
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Left\">"));
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Right\">"));
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Backward\">"));
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Stop\">"));
          client.println(F("</form>"));
          client.println(F(""));
          client.println(F("</body>"));
          client.println(F("</html>"));

          delay(100);
          Serial.println(httpRequest);
          maneuverControl(); //Oui
          msgAvailable=false;
          break;
        }
      }
    }
    delay(100);//delay is very important
    Serial.println("test");
    client.flush();
    client.stop(); 
  }
}  



void maneuverControl() {
  //Serial.print(httpRequest);
  if(httpRequest[0]=='F'){
    Serial.println("forward");
    forward();
  }
  else if(httpRequest[0]=='S'){
    Serial.println("stop");
    stopeu();
  }
  else if(httpRequest[0]=='L'){
    Serial.println("left");
    left();
  }
  else if(httpRequest[0]=='R'){
    Serial.println("right");
    right();
  }
  else if(httpRequest[0]=='B'){
    Serial.println("backward");
    backward();
  }
  httpRequest=""; 
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
  servoRight.writeMicroseconds(1300) ; 
}

void stopeu() {
  Serial.println("stop");
  servoLeft.writeMicroseconds(1500) ;
  servoRight.writeMicroseconds(1500) ;
}



