package gipo.devices;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

import gipo.interfaces.IMotor;

//https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/PwmExample.java
public class MotorDeviceVariableSpeed implements IMotor {

	public final static int MAXSPEED=1000;
	public final static int MINSPEED = 1;
	private static int CLOCK = MAXSPEED/2;
	private GpioPinPwmOutput  pinForward;
	private GpioPinPwmOutput  pinBackward;
	private MotorState state;
	private int speed=MINSPEED;
	
	
	
	public MotorDeviceVariableSpeed(Pin pinForward, Pin pinBackward, int speed) {
		this.pinForward = GpioFactory.getInstance().provisionPwmOutputPin(pinForward, "pinForward");
		this.pinBackward = GpioFactory.getInstance().provisionPwmOutputPin(pinBackward, "pinBackward");
		this.state= MotorState.Stop;
		setSpeed(speed);
		 // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(MAXSPEED);//1000
        com.pi4j.wiringpi.Gpio.pwmSetClock(CLOCK);//500

	}

	@Override
	public void forward() {
		this.pinBackward.setPwm(0);
		this.pinForward.setPwm(speed);
		this.state= MotorState.Forward;
	}
	@Override
	public void backward() {
		this.pinBackward.setPwm(speed);
		this.pinForward.setPwm(0);
		this.state= MotorState.Back;
	}
	@Override
	public void stop() {
		this.pinBackward.setPwm(0);
		this.pinForward.setPwm(0);
		this.state= MotorState.Stop;
	}
	public GpioPinPwmOutput getPinForward() {
		return pinForward;
	}
	public void setPinForward(GpioPinPwmOutput pinForward) {
		this.pinForward = pinForward;
	}
	public GpioPinPwmOutput getPinBackward() {
		return pinBackward;
	}
	public void setPinBackward(GpioPinPwmOutput pinBackward) {
		this.pinBackward = pinBackward;
	}
	
	

	

	@Override
	public void setSpeed(int speed) {
		if(speed>MAXSPEED) {
			this.speed =MAXSPEED;
		}
		if(speed<MINSPEED) {
			this.speed =MINSPEED;
		}
		if(this.state==MotorState.Forward) {
			this.pinForward.setPwm(speed);
		}else if(this.state==MotorState.Back){
			this.pinBackward.setPwm(speed);
		}
		
	}
	
	@Override
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public MotorState getStatus() {
		// TODO Auto-generated method stub
		return state;
	}

	





	
}

