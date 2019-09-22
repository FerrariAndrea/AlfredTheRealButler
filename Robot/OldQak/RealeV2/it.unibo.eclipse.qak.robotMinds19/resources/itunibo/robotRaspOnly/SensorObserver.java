package itunibo.robotRaspOnly;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensorObserver;

public class SensorObserver<T extends ISensorData>   implements ISensorObserver<T>{

	public SensorObserver( ) { 
 	}
	@Override
	public void notify(T data) {
		System.out.println("SensorObserver: " + data.getDefStringRep() + " | " + data.getJsonStringRep());
	}

}
