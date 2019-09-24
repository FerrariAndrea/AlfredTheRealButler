package itunibo.robotRaspOnly;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.BaseRobotBackward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotForward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotLeft;
import it.unibo.iot.models.commands.baseRobot.BaseRobotRight;
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeed;
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeedValue;
import it.unibo.iot.models.commands.baseRobot.BaseRobotStop;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotSpeed;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.ISensorObserver;

public class TerminalControl {
	public static void main(String[] args) throws IOException, InterruptedException, NoSuchMethodException, 
		SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		IBaseRobotSpeed speedLow      = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW);
		IBaseRobotSpeed speedHigh     = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH);
		IBaseRobotSpeed speedMedium   = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM);
		IBaseRobotCommand forward     = new BaseRobotForward(speedHigh);
		IBaseRobotCommand backward    = new BaseRobotBackward(speedHigh);
		IBaseRobotCommand left        = new BaseRobotLeft(speedMedium);
		IBaseRobotCommand right       = new BaseRobotRight(speedMedium);
		IBaseRobotCommand stop        = new BaseRobotStop( speedLow );
		IBaseRobotCommand last        = stop;

		IBaseRobot robot = Configurator.getInstance().getBaseRobot();

		Configurator configurator = Configurator.getInstance();
	 
		new Thread() {
			public void run() {
				  for (ISensor<?> sensor : configurator.getSensors()) {
					  System.out.println("SENSOR " + sensor );
				  sensor.addObserver(new ISensorObserver() {		   
					 @Override 
					 public void notify(ISensorData data) {
						 System.out.println( data.getDefStringRep()); 
					 } 
				   });
				  }				
			}
		}.start();
		  
//		 Set<ISensor<?>> lineSet= configurator.getSensors(SensorType.DISTANCE);
//		 for (ISensor<?> iSensor : lineSet) {
//			 System.out.println("!!!!!! " + iSensor.getDefStringRep());
//		 }
		robot.execute(forward);
		Thread.sleep(1000);
		robot.execute(backward);
		Thread.sleep(1000);
		robot.execute(stop);
		
		System.out.println("w = forward, a =  left, d = right, s = back, h = stop");
		
//		String[] cmd = { "/bin/sh", "-c", "stty  -icanon min 1  raw -echo </dev/tty" };
//		Runtime.getRuntime().exec(cmd).waitFor();
		
		Console console = System.console();
		Reader reader   = console.reader();

		while (true) {

			int t = reader.read();
			if( t == 10 || t == 13 ) continue;
			char cmd = (char) t;
			System.out.println("EXCECUTE "+cmd + " " + t); 
			
			switch( cmd ) {
				case 'h' : robot.execute(stop); break;
				case 'w' : robot.execute(forward); break;
				case 's' : robot.execute(backward); break;
				case 'a' : robot.execute(left); break;
				case 'd' : robot.execute(right); break;
				default: { System.out.println("Bye "); System.exit(0);}
 			}
//			if (b == 'h') {
//				robot.execute(stop);
//			} else if (b == 'w') {
//				robot.execute(forward);
//				last = forward;
//			} else if (b == 's') {
//				robot.execute(backward);
//				last = backward;
//			} else if (b == 'a') {
//				if (last.equals(forward) || last.equals(backward)) {
//					robot.execute(left);
//					Thread.sleep(70);
//					robot.execute(last);
//				} else {
//					robot.execute(left);
//					last = left;
//				}
//			} else if (b == 'd') {
//				if (last.equals(forward) || last.equals(backward)) {
//					robot.execute(right);
//					Thread.sleep(70);
//					robot.execute(last);
//				} else {
//					robot.execute(right);
//					last = right;
//				}
//			} else {
//				robot.execute(stop);
//				last = stop;
//				break;
//			}
		}
//		cmd = new String[] { "/bin/sh", "-c", "stty sane </dev/tty" };
//		Runtime.getRuntime().exec(cmd).waitFor();
//		cmd = new String[] { "/bin/sh", "-c", "stty erase '^H' </dev/tty" };
//		Runtime.getRuntime().exec(cmd).waitFor();
//		Runtime.getRuntime().exec("killall hc-sr04");
//		System.out.println("Bye ");
//		System.exit(0);
	}
} 
