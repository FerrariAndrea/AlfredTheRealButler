#include <iostream>
#include <wiringPi.h>
#include <fstream>
#include <cmath>
#include <stdio.h>
#include <stdlib.h>

//#define TRUE 1
//Wiring Pi numbers for radar with stepper
#define PIN 04  //4

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
	pinMode(PIN, OUTPUT);

	//TRIG pin must start LOW
	digitalWrite(PIN, LOW);
	delay(30);
}



int main(void) {
 	setup();
	while(1) {
		int on=0;
		fscanf(stdin, "%d", &on);
		if(on==0){
			digitalWrite(PIN, LOW);
		}else{
			digitalWrite(PIN, HIGH);
		}
	}
	return 0;
}
