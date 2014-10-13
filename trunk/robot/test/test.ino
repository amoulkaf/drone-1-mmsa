#include <Servo.h>                           // Include servo library
#include <WiFly.h>                           // Include wifly library 
#include <SPI.h>                             // Include spi library 
#include <SoftwareSerial.h>



String httpRequest;        //holds data parsed from the web browser
boolean msgAvailable=false;

SoftwareSerial mySerial( 5, 4);		// used to access the XRF
char ssid[] = "arduino1"; 

WiFlyServer server(80);
 
Servo servoLeft;                             // Declare left and right servos
Servo servoRight;
 
void setup()                                 // Built-in initialization block
{
  Serial.begin(9600);
  mySerial.begin(9600); 
  
  WiFly.begin(true);
  if(!WiFly.createAdHocNetwork(ssid)) {
    while(1) {
      Serial.println("Failed to create network, handle error properly?");
    }
  }
  
  
  
  Serial.println("Network Created");
  Serial.print("IP: ");
  Serial.println(WiFly.ip());
  
  server.begin();
  servoLeft.attach(13);                      // Attach left signal to pin 13
  servoRight.attach(12);                     // Attach right signal to pin 12
  
  Serial.println(WiFly.ip());//Display wifly shield ip address
}  
 
void loop()                                  // Main loop auto-repeats
{
   WiFlyClient client = server.available();
  
  if(client)
  {    
    while(client.connected())
    { //while client is connected
      if (client.available())
      { //if client is available
        char c=client.read();//read data from client
        Serial.print(c);
        if (msgAvailable)
        {
          if(c!='\n'&&c!='\r')
            httpRequest+=c;//parse data to string if condition is true
        }
        if (c=='=')
        {
          msgAvailable=true; //if data sent is '=' intiate data parsing
        }
        if (c=='\n')
        { //send html code to browser once the client is not available
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
          client.println(F(" <input type=\"submit\" name=\"$\" value=\"Stop\">"));
          client.println(F("</form>"));
          client.println(F(""));
          client.println(F("</body>"));
          client.println(F("</html>"));
          
          delay(100);
          Serial.print(httpRequest);
          maneuverControl(); //Oui
          msgAvailable=false;
          break;
        }
      }
    }
    // give the web browser time to receive the data
    delay(100);//delay is very important
    client.flush();
    client.stop();
  }
}   

/* need WiFlySerial lib to see
boolean initSettings()
{
WiFly.SendCommand("set wlan ssid arduino1","AOK");
WiFly.SendCommand("set wlan chan 1","AOK");
WiFly.SendCommand("set ip address 192.168.1.1","AOK");
WiFly.SendCommand("set ip netmask 255.255.0.0","AOK");
WiFly.SendCommand("set ip dhcp 0","AOK");
WiFly.SendCommand("save","AOK");
WiFly.SendCommand("reboot","AOK");
}
*/


void maneuverControl()
{
 if(httpRequest[0]=='F')
  maneuver(200,200);
 else if(httpRequest[0]=='S')
  maneuver(0,0); 
}

void maneuver(int speedLeft, int speedRight)
{
  // speedLeft, speedRight ranges: Backward  Linear  Stop  Linear   Forward
  //                               -200      -100......0......100       200
  servoLeft.writeMicroseconds(1500 + speedLeft);   // Set Left servo speed
  servoRight.writeMicroseconds(1500 - speedRight); // Set right servo speed          
}
