package gipo.devices;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

import gipo.interfaces.IMotor;

public class MotorDevice implements IMotor {


	private GpioPinDigitalOutput pinForward;
	private GpioPinDigitalOutput pinBackward;
	private MotorState state;
	
	
	
	public MotorDevice(GpioPinDigitalOutput pinForward, GpioPinDigitalOutput pinBackward) {
		this.pinForward = pinForward;
		this.pinBackward = pinBackward;
		this.pinBackward.setState(PinState.LOW);
		//this.pinBackward.setShutdownOptions(true,PinState.LOW,PinPullResistance.PULL_DOWN);
		//this.pinForward.setShutdownOptions(true,PinState.LOW,PinPullResistance.PULL_DOWN);
		this.pinForward.setState(PinState.LOW);
		this.state= MotorState.Stop;
	}
	public MotorDevice(Pin pinForward, Pin pinBackward) {
		this.pinForward = GpioFactory.getInstance().provisionDigitalOutputPin(pinForward, "pinForward", PinState.LOW);;
		this.pinBackward = GpioFactory.getInstance().provisionDigitalOutputPin(pinBackward, "pinBackward", PinState.LOW);
		//this.pinBackward.setShutdownOptions(true,PinState.LOW,PinPullResistance.PULL_DOWN);
		//this.pinForward.setShutdownOptions(true,PinState.LOW,PinPullResistance.PULL_DOWN);
		this.state= MotorState.Stop;
	}
	@Override
	public void forward() {
		this.pinBackward.setState(PinState.LOW);
		this.pinForward.setState(PinState.HIGH);
		this.state= MotorState.Forward;
	}
	@Override
	public void backward() {
		this.pinBackward.setState(PinState.HIGH);
		this.pinForward.setState(PinState.LOW);
		this.state= MotorState.Back;
	}
	@Override
	public void stop() {
		this.pinBackward.setState(PinState.LOW);
		this.pinForward.setState(PinState.LOW);
		this.state= MotorState.Stop;
	}
	public GpioPinDigitalOutput getPinForward() {
		return pinForward;
	}
	public void setPinForward(GpioPinDigitalOutput pinForward) {
		this.pinForward = pinForward;
	}
	public GpioPinDigitalOutput getPinBackward() {
		return pinBackward;
	}
	public void setPinBackward(GpioPinDigitalOutput pinBackward) {
		this.pinBackward = pinBackward;
	}
	public MotorState getState() {
		return state;
	}
	


	
	
	public enum MotorState {
		Forward,
		Back,
		Stop
		}
	
}

