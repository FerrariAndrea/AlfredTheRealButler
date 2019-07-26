package itunibo.robotRaspOnly;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import it.unibo.iot.models.sensorData.DirectionRelativeValue;
import it.unibo.iot.models.sensorData.PositionValue;
import it.unibo.iot.models.sensorData.distance.DistanceSensorData;
 
/*
 * HC-SR04  UltraSonic Distance Measure Module Range Sensor
 */
public class SonarMain  {
	protected BufferedReader reader;
	protected int distance;
	protected PositionValue pos;
	protected DirectionRelativeValue dir;
	protected DistanceSensorData dsd;     
	
	public SonarMain() {
		doJob();
	}
 
 	protected void doJob()  {
		try{
			System.out.println(" STARTING ... ");
			Process p = Runtime.getRuntime().exec("./SonarAlone");
	 		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 		while(true){
	 			String data = reader.readLine();
	 			System.out.println("data = " + data );
 	 		}
 		}catch(Exception e){
 			System.out.println(" ERROR " + e.getMessage() );
		}
 	}
		
  	
	public static void main(String[] args) throws Exception {
 		new SonarMain( );
	}	
}
