import java.util.Scanner;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


import gipo.devices.*;
import myDevice.DeviceArg;
import myDevice.MotoDetectionLed;
import myDevice.MyDistanceMonitor;
import myDevice.MyHeadligths;

public class Main {
	public static void main(String[] args) 
	{
		System.out.println("Benvenuto");
		Scanner scanner = new Scanner(System.in);	
		MyHeadligths led = new MyHeadligths(RaspiPin.GPIO_04);
		PirSensorDigital pir = new PirSensorDigital(RaspiPin.GPIO_00);
		MotoDetectionLed mdl = new MotoDetectionLed(led);
		MyDistanceMonitor ultrasonic_0 = new MyDistanceMonitor(RaspiPin.GPIO_29,RaspiPin.GPIO_28);
		MyDistanceMonitor ultrasonic_1 = new MyDistanceMonitor(RaspiPin.GPIO_27,RaspiPin.GPIO_26);
		MyDistanceMonitor ultrasonic_2= new MyDistanceMonitor(RaspiPin.GPIO_21,RaspiPin.GPIO_06);
		MyDistanceMonitor ultrasonic_3 = new MyDistanceMonitor(RaspiPin.GPIO_25,RaspiPin.GPIO_24);
		MyDistanceMonitor ultrasonic_4 = new MyDistanceMonitor(RaspiPin.GPIO_23,RaspiPin.GPIO_22);
		pir.addObserver(mdl);
		MotorDevice md = new MotorDevice(RaspiPin.GPIO_03,RaspiPin.GPIO_02);
		boolean stay =true; 
		while(stay) {
			System.out.println("Lista comandi:");
			System.out.println("3-->exit");
			System.out.println("0->ledOff,1-->LedOn,2;22-->blink");
			System.out.println("4->motioON,5-->motioOff");
			System.out.println("6-->sonic sensor 0");
			System.out.println("7-->sonic sensor 1");
			System.out.println("8-->sonic sensor 2");
			System.out.println("9-->sonic sensor 3");
			System.out.println("10-->sonic sensor 4");
			System.out.println("11-->Motore avanti");
			System.out.println("12-->Motore indietro");
			System.out.println("13-->Motore stop");
			System.out.println("Inserisci il comando:");
			switch(scanner.nextInt()) {
			case 0:	  	   
				led.turnOff();
			break;
			case 1:
				led.turnOn();
			break;
			case 2:
				led.Blinking(500);
			break;	
			case 22:
				led.Blinking(100);
			break;	
			case 3:
				stay=false;
				GpioFactory.getInstance().shutdown();
			break;
			case 4:				
				pir.setEnable(true);
			break;
			case 5:				
				pir.setEnable(false);
			break;
			case 6:				
				try {
					System.out.println("Distance: " + ultrasonic_0.measureDistance()+"cm");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			case 7:				
				try {
					System.out.println("Distance: " + ultrasonic_1.measureDistance()+"cm");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			case 8:				
				try {
					System.out.println("Distance: " + ultrasonic_2.measureDistance()+"cm");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			case 9:				
				try {
					System.out.println("Distance: " + ultrasonic_3.measureDistance()+"cm");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			case 10:				
				try {
					System.out.println("Distance: " + ultrasonic_4.measureDistance()+"cm");
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;
			case 11:				
				md.forward();
			break;
			case 12:				
				md.backward();
			break;
			case 13:				
				md.stop();
			break;
			default:
				
			}
		}
		
	}
}
