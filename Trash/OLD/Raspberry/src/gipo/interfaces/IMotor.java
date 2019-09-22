package gipo.interfaces;

import gipo.devices.MotorState;

public interface IMotor {

	public void forward();
	public void backward();
	public void stop();
	public void setSpeed(int speed);
	public int getSpeed();
	public MotorState getStatus();
	
}
