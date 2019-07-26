#include <iostream>
#include <wiringPi.h>
#include <fstream>
#include <cmath>

//#define TRUE 1
//Wiring Pi numbers for radar with stepper
#define TRIG 28  //4
#define ECHO 29  //5
#define TRIG_1 26
#define ECHO_1 27
#define TRIG_2 22
#define ECHO_2 23

using namespace std;
/*
 * in the directory: of SonarAlone.c:
1)  [ sudo ../../pi-blaster/pi-blaster ] if servo
2)  g++  SonarAlone.c -l wiringPi -o  SonarAlone
sudo ./SonarAlone
In nat/servosonar:
sudo java -jar   SonarAloneP2PMain.jar
sudo python radar.py
 */
void setup() {
	wiringPiSetup();
	pinMode(TRIG, OUTPUT);
	pinMode(ECHO, INPUT);
	pinMode(TRIG_1, OUTPUT);
	pinMode(ECHO_1, INPUT);
	pinMode(TRIG_2, OUTPUT);
	pinMode(ECHO_2, INPUT);

	//TRIG pin must start LOW
	digitalWrite(TRIG, LOW);
	digitalWrite(TRIG_1, LOW);
	digitalWrite(TRIG_2, LOW);

	delay(30);
}

int getCM() {
	//---------------------------------------------------1
	//Send trig pulse
	digitalWrite(TRIG, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG, LOW);

	//Wait for echo start
	while(digitalRead(ECHO) == LOW);

	//Wait for echo end
	long startTime = micros();
	while(digitalRead(ECHO) == HIGH);
	long travelTime = micros() - startTime;

	//Get distance in cm
	int distance = travelTime / 58;
	
//---------------------------------------------------2
	delay(5);
	//Send trig pulse
	digitalWrite(TRIG_1, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG_1, LOW);

	//Wait for echo start
	while(digitalRead(ECHO_1) == LOW);

	//Wait for echo end
	startTime = micros();
	while(digitalRead(ECHO_1) == HIGH);
	travelTime = micros() - startTime;

	//Get distance in cm
	int distance_1 = travelTime / 58;
//---------------------------------------------------3
	delay(5);
	//second sensor
	//Send trig pulse
	digitalWrite(TRIG_2, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG_2, LOW);

	//Wait for echo start
	while(digitalRead(ECHO_2) == LOW);

	//Wait for echo end
	startTime = micros();
	while(digitalRead(ECHO_2) == HIGH);
	travelTime = micros() - startTime;

	//Get distance in cm
	int distance_2 = travelTime / 58;
	
	int ris=distance;
	if(distance_2<distance){
		distance=distance_2;
	}
	if(distance_1<distance){
		distance=distance_1;
	}
 	return distance;
}

int main(void) {
	int cm ;	
 	setup();
	while(1) {
		cm = getCM();
		cout <<  cm <<   endl;
		delay(50);
	}
	return 0;
}
