#include <iostream>
#include <wiringPi.h>
#include <fstream>
#include <cmath>

//#define TRUE 1
//Wiring Pi numbers for radar with stepper
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
	pinMode(TRIG_1, OUTPUT);
	pinMode(ECHO_1, INPUT);
	pinMode(TRIG_2, OUTPUT);
	pinMode(ECHO_2, INPUT);

	//TRIG pin must start LOW
	digitalWrite(TRIG_1, LOW);
	digitalWrite(TRIG_2, LOW);
	delay(30);
}

int getCM() {
	//Send trig pulse
	digitalWrite(TRIG_1, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG_1, LOW);

	//Wait for echo start
	while(digitalRead(ECHO_1) == LOW);

	//Wait for echo end
	long startTime = micros();
	while(digitalRead(ECHO_1) == HIGH);
	long travelTime = micros() - startTime;

	//Get distance in cm
	int distance_1 = travelTime / 58;
	
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
	
	return distance_1-distance_2;
}

int main(void) {
	int cm ;	
 	setup();
	while(1) {
		cm = getCM();
		cout <<  cm <<   endl;
		delay(300);
	}
	return 0;
}
