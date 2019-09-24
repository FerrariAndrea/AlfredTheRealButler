package gipo.devices;
import java.util.Observable;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import gipo.interfaces.IPirSensor;

public class PirSensorDigital extends Observable implements IPirSensor{
	private GpioPinDigitalInput pin;
	private boolean enable = false;
	private boolean motionDetect = false;
	private GpioPinListenerDigital listener;
	public PirSensorDigital(Pin pin) {
		this.pin = GpioFactory.getInstance().provisionDigitalInputPin(pin,PinPullResistance.PULL_UP);
		listener=new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	  if(event.getState().isHigh()){	
            		  motionDetect=true;
            	  }
                  if(event.getState().isLow()){	
                	  motionDetect=false;
                  }
                  //System.out.println("Prova handleGpioPinDigitalStateChangeEvent");
                  setChanged();
          	      notifyObservers();
            }

        };
		this.enable = false;
	}

	public boolean therIsMoto() {
		return motionDetect;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
		if(this.enable) {
			if(!pin.hasListener(listener)) {
				pin.addListener(listener);
			}
		}else {
			if(pin.hasListener(listener)) {
				pin.removeListener(listener);
			}
		}
	}
	
	
}

