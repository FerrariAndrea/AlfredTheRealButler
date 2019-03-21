package gipo.devices;


import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import gipo.interfaces.IHeadlights;
public class Headlights implements IHeadlights {

	private GpioPinDigitalOutput pin;
	private boolean state;
	
	public Headlights(GpioPinDigitalOutput pin, boolean state) {
		this.pin = pin;
		this.state = state;
	}
	public Headlights(Pin pin) {
		this.pin = GpioFactory.getInstance().provisionDigitalOutputPin(pin, "MyLED", PinState.LOW);
		this.pin.setShutdownOptions(true, PinState.LOW);		
		this.state = false;
	}

	public GpioPinDigitalOutput getPin() {
		return pin;
	}

	public void setPin(GpioPinDigitalOutput pin) {
		turnOff();// turnOff old gipo
		this.pin = pin;
		turnOff();// turnOff this new gipo
	}
	
	

	@Override
	public void turnOff() {
		pin.low();
		this.state= false;
	}

	@Override
	public boolean getState() {
		return this.state;
	}
	
	@Override
	public void turnOn() {
		pin.high();
		this.state = true;
	}
	
	
}
