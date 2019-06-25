package gipo.devices;
import java.util.Observable;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;

import gipo.interfaces.IPirSensor;

//non funziona
public class PirSensorAnalog extends Observable implements IPirSensor{
	private GpioPinAnalogInput pin;
	private boolean enable = false;
	private GpioPinListenerAnalog listener;
	public PirSensorAnalog(Pin pin) {
		this.pin = GpioFactory.getInstance().provisionAnalogInputPin(pin);
		listener=new GpioPinListenerAnalog() {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getValue());
            }

        };
		this.enable = false;
	}
	@Override
	public boolean therIsMoto() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
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
