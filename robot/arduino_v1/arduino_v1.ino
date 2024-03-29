
#include <WiFly.h>
#include <SPI.h>
#include <Servo.h>

String httpRequest;
boolean msgAvailable=false;

Servo servoLeft;
Servo servoRight;

WiFlyServer server(80);

char ssid[] = "Arduino1";
char *ip = "192.168.0.1";
char *netMask = "255.255.255.0";
int test;

void setup() {
  

  
  Serial.begin(9600);

    Serial.println("**************Starting WiFly**************");
    // Enable Adhoc mod
    WiFly.begin(true);
    Serial.println("WiFly started, creating network.");

    if (!WiFly.createAdHocNetwork("ardrone1")) 
    {
        Serial.print("Failed to create ad hoc network.");
        while (1) 
        {
            // Hang on failure.
        }
    }
    // Set IP & NetMask for Adhoc Network
    WiFly.SetNetwork(ip, netMask);
    
    Serial.println("Network created");
    Serial.print("IP: ");
    Serial.println(WiFly.ip());

    Serial.println("Starting Server...");
    server.begin();
    Serial.println("Server started, waiting for client.");
  
}

void loop()  {
   WiFlyClient client = server.available();
  
  if(client){    
    while(client.connected()){ 
      if (client.available()){
        char c=client.read();//read data from client
   
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
    // give the web browser time to receive the data
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
 }else if(httpRequest[0]=='S'){
  Serial.println("stop");
  stopeu();
 }else if(httpRequest[0]=='L'){
  Serial.println("left");
  left();
 }else if(httpRequest[0]=='R'){
  Serial.println("right");
  right();
 }else if(httpRequest[0]=='B'){
  Serial.println("backward");
  backward();
 }
 httpRequest=""; 
}

void forward() {
 servoRight.attach(13);
 servoLeft.attach(12);
  
     
 servoRight.writeMicroseconds(1300); 
 servoLeft.writeMicroseconds(1700); 
 delay(100); 


 servoLeft.detach();                
 servoRight.detach();
}

void backward() {
    
 servoRight.attach(13);
 servoLeft.attach(12);
     
 servoRight.writeMicroseconds(1700); 
 servoLeft.writeMicroseconds(1300); 
 delay(100); 

 servoLeft.detach();                
 servoRight.detach();
}

void left() {

 servoRight.attach(13);
 servoLeft.attach(12);

 servoRight.writeMicroseconds(1500) ;
 servoLeft.writeMicroseconds(1700) ;
 delay(100); 

 servoLeft.detach();                
 servoRight.detach();
}

void right() {

 servoRight.attach(13);
 servoLeft.attach(12);

 servoRight.writeMicroseconds(1300) ;
 servoLeft.writeMicroseconds(1500) ;
 delay(100); 

 servoLeft.detach();                
 servoRight.detach();

}

void stopeu() {
     
 servoRight.attach(13);
 servoLeft.attach(12);
  
 servoRight.writeMicroseconds(1500); 
 servoLeft.writeMicroseconds(1500); 
 delay(100); 
  
 servoLeft.detach();                
 servoRight.detach();
 
}

