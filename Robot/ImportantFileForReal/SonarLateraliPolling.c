#include <iostream>
#include <wiringPi.h>
#include <fstream>
#include <cmath>

//#define TRUE 1
//Wiring Pi numbers for radar with stepper
#define TRIG_L 24
#define ECHO_L 25
#define TRIG_R 06
#define ECHO_R 21

using namespace std;
/*
 * in the directory: of SonarLateraliPolling.c:
1)  [ sudo ../../pi-blaster/pi-blaster ] if servo
2)  g++  SonarLateraliPolling.c -l wiringPi -o  SonarLateraliPolling
sudo ./SonarAlone
In nat/servosonar:
sudo java -jar   SonarAloneP2PMain.jar
sudo python radar.py
 */
void setup() {
	wiringPiSetup();
	
	pinMode(TRIG_L, OUTPUT);
	pinMode(ECHO_L, INPUT);
	pinMode(TRIG_R, OUTPUT);
	pinMode(ECHO_R, INPUT);
	

	//TRIG pin must start LOW
	digitalWrite(TRIG_L, LOW);
	digitalWrite(TRIG_R, LOW);

	delay(30);
}

int getCM_L() {
	//---------------------------------------------------Sonar1
	//Send trig pulse
	digitalWrite(TRIG_L, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG_L, LOW);

	//Wait for echo start
	while(digitalRead(ECHO_L) == LOW);

	//Wait for echo end
	long startTime = micros();
	while(digitalRead(ECHO_L) == HIGH);
	long travelTime = micros() - startTime;

	//Get distance in cm
	int distance = travelTime / 58;
	
 	return distance;
}
int getCM_R() {
	//---------------------------------------------------Sonar1
	//Send trig pulse
	digitalWrite(TRIG_R, HIGH);
	delayMicroseconds(20);
	digitalWrite(TRIG_R, LOW);

	//Wait for echo start
	while(digitalRead(ECHO_R) == LOW);

	//Wait for echo end
	long startTime = micros();
	while(digitalRead(ECHO_R) == HIGH);
	long travelTime = micros() - startTime;

	//Get distance in cm
	int distance = travelTime / 58;
	
 	return distance;
}

int main(void) {
	int cm =-1;
 	setup();
	int selector=0;
	while(1){
		scanf("%d" , &selector );
		if(selector){
			cm = getCM_L();
		}else{
			cm = getCM_R();
		}
		cout <<  cm <<   endl;
	}
	
}
