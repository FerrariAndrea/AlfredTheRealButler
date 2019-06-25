package myDevice;



import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;

import gipo.devices.Headlights;


public class MyHeadligths extends Headlights{
	private Thread blink=null;
	private boolean running = false;
	private int interval=250;
	private Runnable bt=new Runnable() {
	    @Override 
	    public void run() {
	    	running=true;
			try {
				while(running) {
						if(getState()) {
							superTurnOff();
						}else {
							superTurnOn();
						}
						Thread.sleep(interval);
					}			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block		
				running=false;
				e.printStackTrace();
			
			}
	    }
	};
	
	public MyHeadligths(Pin pin) {
		super(pin);
		// TODO Auto-generated constructor stub
	}

	public MyHeadligths(GpioPinDigitalOutput pin, boolean state) {
		super(pin, state);		
		// TODO Auto-generated constructor stub
	}
	private void superTurnOn() {
		super.turnOn();
	}
	private void superTurnOff() {
		super.turnOff();
	}
	@Override
	public void turnOn() {
		if(blink!=null && blink.isAlive()){				
			running=false;
		}
		super.turnOn();
	}
	@Override
	public void turnOff() {
		if(blink!=null && blink.isAlive()){				
			running=false;
		}
		super.turnOff();
	}
	

	
	public void Blinking(int interval) {
		if(interval<5){
			this.interval=5;
		}
		else {
			this.interval=interval;
		}
		if(blink==null || !blink.isAlive()){
			blink=new Thread(bt);
			blink.start();  
		}
	}
	
	
}
	

