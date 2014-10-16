
#include <WiFly.h>
#include <SPI.h>
#include <Servo.h>


Servo servoLeft;
Servo servoRight;

WiFlyServer server(80);

char ssid[] = "Arduino1";
char *ip = "192.168.0.1";
char *netMask = "255.255.255.0";


void setup() {
  servoRight.attach(12);
  servoLeft.attach(13);
  
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
    Serial.print("Server started, waiting for client.");
  
}


void loop() {
  WiFlyClient client = server.available();
  if (client) {
    // an http request ends with a blank line
    boolean current_line_is_blank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        // if we've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so we can send a reply
        if (c == '\n' && current_line_is_blank) {
          //*************************header*************************
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();
          
          //*************************body*************************
          client.println("<!DOCTYPE html>");
          client.println("<html>");
          client.println("<body>");
          
          client.println("</body>");
          client.println("</html>");
          
          // output the value of each analog input pin
          for (int i = 0; i < 6; i++) {
            client.print("analog input ");
            client.print(i);
            client.print(" is ");
            client.print(analogRead(i));
            client.println("<br />");
          }
          break;
        }
        if (c == '\n') {
          // we're starting a new line
          current_line_is_blank = true;
        } else if (c != '\r') {
          // we've gotten a character on the current line
          current_line_is_blank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(100);
    client.stop();
  }
}

void forward() {
    servoRight.writeMicroseconds(1700) ;
    servoLeft . writeMicroseconds(1300) ;
}

void backward() {
    servoRight.writeMicroseconds(1300) ;
    servoLeft.writeMicroseconds(1700) ;
}

void left() {
    servoRight.writeMicroseconds(1300) ;
    servoLeft.writeMicroseconds(1300) ;
}

void right() {
    servoRight.writeMicroseconds(1700) ;
    servoLeft.writeMicroseconds(1700) ;
}

void stop() {
    servoRight.writeMicroseconds(1500) ;
    servoLeft.writeMicroseconds(1500) ;
}

