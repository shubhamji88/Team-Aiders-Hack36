/*
   HACK 36
   IoT Enabled Heart Rate and SPO2 Monitoring System
   By Team Aiders: VIT Vellore
   Connection
   Sensor---NodeMCU
   vin-5v
   SDA-D2
   SCL-D1
   GND-GND
*/
#include <DFRobot_MAX30102.h>

#include <math.h>
#include <Wire.h>
#include <ESP8266WiFi.h>;
#include <WiFiClient.h>;
#include <ThingSpeak.h>;
#include <Adafruit_GFX.h> // For OLED
#include <Adafruit_SSD1306.h>// Foe OLED
#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 32 // OLED display height, in pixels
// Declaration for an SSD1306 display connected to I2C (SDA, SCL pins)
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);
DFRobot_MAX30102 particleSensor;
WiFiClient client;// creating object for wifi access
const char* ssid = "****";       // Your WiFi Id
const char* password = "*****"; //Your Network Password
unsigned long myChannelNumber = 971402; //Your Channel Number for Distance (Without Brackets)
const char * myWriteAPIKey = "P7OZDXNWMR3T8MAN";   // YOUR_API_KEY for channel P7OZDXNWMR3T8MAN

int sensorPin = A0; // Analog Pin where the S is connected to
long HrtRate = 0; // Heart rate (determined)
long Cal_hrt_rate = 0; // calibrated heart rate
//
int32_t SPO2; //SPO2
int8_t SPO2Valid; //Flag to display if SPO2 calculation is valid
int32_t heartRate; //Heart-rate
int8_t heartRateValid; //Flag to display if heart-rate calculation is valid

void setup() {

  //Init serial
  Serial.begin(115200);
  //Chip IIC address (0x57 in default)
  while (!particleSensor.begin()) {
    Serial.println("MAX30102 was not found");
    delay(1000);
  }
  particleSensor.sensorConfiguration(/ledBrightness=/50, /sampleAverage=/SAMPLEAVG_4, \
      /ledMode=/MODE_MULTILED, /sampleRate=/SAMPLERATE_100, \
      /pulseWidth=/PULSEWIDTH_411, /adcRange=/ADCRANGE_16384);

  delay(100);
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { // Address 0x3D for 128x64
    Serial.println(F("SSD1306 allocation failed"));
    for (;;);
  }
  delay(1000);
  // Display Project details
  welcome();
  delay(3000);
  productinfo();
  delay(3000);

  // Connect to WiFi network
 WiFi.begin(ssid, password);
 ThingSpeak.begin(client);
// Display connection to OLED
  display.clearDisplay();
  delay(100);
  display.setCursor(10, 10);
  display.println("Connected to WiFi");
  display.display();
  delay(2000);
  
  display.clearDisplay();
  delay(100);
  display.setCursor(10, 10);
  display.println("GETTING SENSOR DATA");
  display.display();
  delay(1000);
}

void loop()
{
 // Serial.println(F("Wait about four seconds"));
  particleSensor.heartrateAndOxygenSaturation(/*SPO2=/&SPO2, /*SPO2Valid=/&SPO2Valid, /*heartRate=/&heartRate, /*heartRateValid=/&heartRateValid);
  //Print result
  //Serial.print(F("heartRate="));
  //Serial.print(heartRate, DEC);
  //Serial.print(F(", heartRateValid="));
  //Serial.print(heartRateValid, DEC);
  //Serial.print(F("; SPO2="));
  //Serial.print(SPO2, DEC);
  //Serial.print(F(", SPO2Valid="));
  //Serial.println(SPO2Valid, DEC);

  ThingSpeak.setField(1,heartRate);
  ThingSpeak.setField(2,SPO2);
  ThingSpeak.writeFields(myChannelNumber, myWriteAPIKey); //Update in ThingSpeak
  display.clearDisplay();
  delay(100);
  display.setTextSize(1);
  display.setCursor(0, 5);
  display.println("HEART RATE:");
  //display.setTextSize(2);
  display.setCursor(75, 5);

  if (heartRate > 60 && heartRate < 100)
  { display.println(heartRate);
  }
  //display.println(heartRate);
  display.setCursor(95, 5);
  display.println("BPM");

  display.setCursor(0, 20);
  display.println("SPO2:");
  display.setCursor(75, 20);


  if (SPO2 > 90 && SPO2 < 100)
  { display.println(SPO2);
  }
  display.setCursor(95, 20);
  display.println("%");
  display.display();
  delay(1000);

}// loop stage closes

void welcome()
{
  display.clearDisplay();
  delay(300);
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(0, 5);
  display.println("WELCOME TO HACK36 4.0");
  display.setCursor(20, 20);
  display.println("BY: TEAM AIDERS");
  display.display();
 }

void productinfo()
{
  display.clearDisplay();
  delay(100);
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(10, 0);
  display.println("HEART RATE & SPO2");
  display.setCursor(10, 17);
  display.println("MONITORING SYSTEM ");
  display.display();
  delay(3000);
  display.clearDisplay();
  delay(100);
  display.setCursor(60, 2);
  display.println("By");
  display.setCursor(30, 20);
  display.println("TEAM AIDERS");
  display.display();
  }