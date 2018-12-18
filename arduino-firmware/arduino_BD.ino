/* Variables, includes and defines from sketch */
unsigned long lastTime = 0;
unsigned long delayMeasure = 2000;
unsigned long timeNow = 0; 
char option;
int   humSoil, lum;
float tempAir, humAir;

#include "DHT.h"


#define DHTTYPE DHT11 // DHT 11
#define BOMB_PIN    7   // --> turnON/turnOff bomb;
#define DHT11_PIN   A0  // --> umidade e temperatura do ar
#define LDR_PIN     A3  // --> sensor de luminosidade
#define LM35_PIN    A4  // --> sensor de umidade do solo

/* Arduino Code to communicate and read sensores with Java */
DHT dht(DHT11_PIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  
  pinMode(BOMB_PIN,OUTPUT);
  
  pinMode(DHT11_PIN,INPUT);
  pinMode(LDR_PIN,INPUT);
  pinMode(LM35_PIN,INPUT);

  dht.begin();
}

void loop() {
  
if(Serial.available()){
      option = Serial.read();   
      switch(option){
		  
        case 'r': // send data from arduino to serial (write)
        delay(50);
        measures();
        humSoil = 1024 - humSoil;
        lum = 1024 - lum;        
        Serial.println(String(humAir)+";"+String(tempAir)+";"+String(humSoil)+";"+String(lum));
        Serial.flush();            
          break;

        case 't': // turn on bomb for a determined time (write)
          delay(50);
          digitalWrite(BOMB_PIN,HIGH);
          Serial.print('L');                    
          break;
          
        case 'o': // turn off bomb (write)
          delay(50);        
          digitalWrite(BOMB_PIN,LOW);
          Serial.print('D');
          break;

        default:       
          break;
      }
  }

//  measures();    
}

void measures(){
  
//  timeNow = millis();
//  if(timeNow - lastTime >= 3000){
//    lum = analogRead(LDR_PIN);       //--> maior é mais claro
//    humSoil = analogRead(LM35_PIN);  //--> menor é mais úmido (conduz mais)
//    humAir = dht.readHumidity();
//    tempAir = dht.readTemperature();
//    lastTime = timeNow;  
//  }
    lum = analogRead(LDR_PIN);       //--> maior é mais claro
    delay(20);
    humSoil = analogRead(LM35_PIN);  //--> menor é mais úmido (conduz mais)
    delay(20);
    humAir = dht.readHumidity();
    tempAir = dht.readTemperature();
}
