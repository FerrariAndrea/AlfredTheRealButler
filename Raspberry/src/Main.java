import java.io.IOException;
import java.util.Scanner;

import com.pi4j.io.*;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

import gipo.devices.*;
import gipo.devices.MyMatrixLed.IMG;
import gipo.interfaces.IMotor;


public class Main {
	public static void main(String[] args)  
	{
		System.out.println("Benvenuto");
		Scanner scanner = new Scanner(System.in);	
		MyHeadligths led = new MyHeadligths(RaspiPin.GPIO_04);
		
		
		
		MyDistanceMonitor ultrasonic_0 = new MyDistanceMonitor(RaspiPin.GPIO_29,RaspiPin.GPIO_28);
		MyDistanceMonitor ultrasonic_1 = new MyDistanceMonitor(RaspiPin.GPIO_27,RaspiPin.GPIO_26);
		MyDistanceMonitor ultrasonic_2= new MyDistanceMonitor(RaspiPin.GPIO_21,RaspiPin.GPIO_06);
		MyDistanceMonitor ultrasonic_3 = new MyDistanceMonitor(RaspiPin.GPIO_25,RaspiPin.GPIO_24);
		MyDistanceMonitor ultrasonic_4 = new MyDistanceMonitor(RaspiPin.GPIO_23,RaspiPin.GPIO_22);
		MyMatrixLed ml = new MyMatrixLed((short)1);		
		ml.open();
		ml.brightness((byte) 15);
		
		PirSensorDigital pir = new PirSensorDigital(RaspiPin.GPIO_00);
		MotoDetectionLed mdl = new MotoDetectionLed(led);
		MotoDetectionMatrixLed mdml = new MotoDetectionMatrixLed(ml);
		pir.addObserver(mdl);
		pir.addObserver(mdml);
		IMotor md0 = new MotorDeviceConstantSpeed(RaspiPin.GPIO_03,RaspiPin.GPIO_02);
		IMotor md1 = new MotorDeviceConstantSpeed(RaspiPin.GPIO_07,RaspiPin.GPIO_05);
		TankMotor tm = new TankMotor(md0,md1,1000);
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
			System.out.println("14-->Test matrixled_1");
			System.out.println("15-->Test matrixled_2");
			System.out.println("16-->TankMotor");
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
				ml.close();
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
				md0.forward();
				md1.forward();
			break;
			case 12:				
				md0.backward();
				md1.backward();
			break;
			case 13:				
				md0.stop();
				md1.stop();
			break;
			case 14:				
				
				
				ml.orientation(45);

				//DEMO1
				//ml.letter((short)0, (short)'Y');
				//ml.letter((short)1, (short)'C');
				//ml.flush();
				
				//DEMO2
				//c.letter((short)0, (short)0,Font.CHN_FONT,false);
				//c.letter((short)1, (short)1,Font.CHN_FONT,false);
				//c.flush();
				
				//DEMO3
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ml.showMessage("THIS IS A REAL HELLO WORLD");
			break;
			case 15:				
				
				
				ml.orientation(0);
			
				try {
					for(int x = 0;x<20;x++) {
						
							ml.draw(0,MyMatrixLed.ImgFactory(IMG.SMILE));
							Thread.sleep(50);
							ml.draw(0,MyMatrixLed.ImgFactory(IMG.WINK));
							Thread.sleep(110);
							ml.draw(0,MyMatrixLed.ImgFactory(IMG.SMILE));
						Thread.sleep(500);
					}
					ml.draw(0,MyMatrixLed.ImgFactory(IMG.SMILE));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//DEMO1
				//ml.letter((short)0, (short)'Y');
				//ml.letter((short)1, (short)'C');
				//ml.flush();
				
				//DEMO2
				//c.letter((short)0, (short)0,Font.CHN_FONT,false);
				//c.letter((short)1, (short)1,Font.CHN_FONT,false);
				//c.flush();
				
				//DEMO3
				
			break;
			case 16:				
				
				try {
					TankMotorControll(tm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			break;
			default:
				
			}
		}
		
	}
	
	private static void TankMotorControll(TankMotor tm) throws IOException {
		System.out.println("Lista comandi: WASD->muovi P->esci ZX->velocità QE->ruota F->ferma");
		boolean stay =true; 
		while(stay) {
			switch((char) System.in.read()) {
			case 'p':
				tm.stop();
				stay =false; 
			case 'w':	
				tm.forward();
			case 's':	
				tm.backward();
			case 'a':	
				tm.left(2);
			case 'd':	
				tm.right(2);
			case 'z':	
				tm.setSpeed(tm.getSpeed()-10);
			case 'x':	
				tm.setSpeed(tm.getSpeed()+10);
			case 'q':	
				tm.rotate(true);
			case 'e':	
				tm.rotate(false);
			break;
			case 'f':	
				tm.stop();
			break;
			default:
				
			}
		}
	
	}
}



