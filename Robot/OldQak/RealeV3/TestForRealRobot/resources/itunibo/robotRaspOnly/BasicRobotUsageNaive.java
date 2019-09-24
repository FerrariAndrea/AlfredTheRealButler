/*
 * ======================================================================================
 * The file ./hardwareConfiguration.properties  must contain the robot name:
  		configuration=mocksimple
 * The file ./configuration/mocksimple/iotRobot.properties must contain the robot configuration
* ======================================================================================
*/
package itunibo.robotRaspOnly;
import java.io.IOException;
import it.unibo.iot.baseRobot.hlmodel.BasicRobot;
import it.unibo.iot.baseRobot.hlmodel.IBasicRobot;
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
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.ISensorObserver;
 

public class BasicRobotUsageNaive{ 
private final IBaseRobotSpeed SPEED_LOW    = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW);
private final IBaseRobotSpeed SPEED_MEDIUM = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM);
private final IBaseRobotSpeed SPEED_HIGH   = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH);
private IBasicRobot basicRobot;
private IBaseRobot robot ;

	public BasicRobotUsageNaive() {
		basicRobot = BasicRobot.getRobot();
		robot      = basicRobot.getBaseRobot();
//		addObserverToSensors(basicRobot);  				
	}
	public void handleUserCommands() {
		try {
			while(true) {
 				int v    =  System.in.read();
				if( v == 13 || v == 10 ) continue;
				char cmd = (char)v;
				System.out.println( "INPUT= " +  cmd + "(" + v +")");
				executeTheCommand( cmd );
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}		
	}	
	public void executeTheCommand( char cmd ) {
		IBaseRobotCommand command = null;
		switch( cmd ) {
			case 'h' : command = new BaseRobotStop(SPEED_LOW );break;
			case 'w' : command = new BaseRobotForward(SPEED_HIGH );break;
			case 's' : command = new BaseRobotBackward(SPEED_HIGH );break;
			case 'a' : command = new BaseRobotLeft(SPEED_MEDIUM );break;
			case 'd' : command = new BaseRobotRight(SPEED_MEDIUM );break;
			default: System.out.println( "Sorry, command not found");
		}
		if( command != null ) robot.execute(command);
	}	
	protected void addObserverToSensors( ){
		ISensorObserver observer = new SensorObserver();
//		for (ISensor<?> sensor : basicRobot.getSensors()) {  
//			System.out.println( "doJob sensor= "  + sensor.getDefStringRep() + " class= "  + sensor.getClass().getName() );
// 			sensor.addObserver(observer);
//		}		
		addObserverToSensors(observer);
	}
	
	public void addObserverToSensors( ISensorObserver observer ){
		for (ISensor<?> sensor : basicRobot.getSensors()) {  
			System.out.println( "adding observer to sensor: "  + sensor.getDefStringRep()  );
 			sensor.addObserver(observer);
		}				
	}
 	
 public static void main(String[] args) throws Exception{
  		new BasicRobotUsageNaive().handleUserCommands();
 }
}
