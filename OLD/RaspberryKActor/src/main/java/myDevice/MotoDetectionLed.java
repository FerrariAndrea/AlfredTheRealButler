package myDevice;

import java.util.Observable;
import java.util.Observer;
import gipo.devices.PirSensorDigital;
import gipo.devices.DistanceMonitor;
import gipo.devices.Headlights;

public class MotoDetectionLed implements Observer {

	private Headlights led;
	
	public MotoDetectionLed(Headlights led) {
		this.led = led;
	}

	public Headlights getLed() {
		return led;
	}

	public void setLed(Headlights led) {
		this.led = led;
	}

	@Override
	public void update(Observable o, Object arg) {		
		//if(o.getClass().equals(PirSensorDigital.class)) {
			;
			if(((PirSensorDigital)o).therIsMoto()){
				led.turnOn();
			}else {
				led.turnOff();
			}
		//}
		
	}
	

}
