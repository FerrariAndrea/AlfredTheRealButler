package gipo.devices;

import java.util.Observable;
import java.util.Observer;

import gipo.interfaces.IMotor;
import gipo.interfaces.ITankMotor;

public class TankMotor extends Observable implements ITankMotor {
	

	
	private IMotor leftMotor;
	private IMotor rightMotor;
	private int speed;
	
	
	public TankMotor(IMotor leftMotor, IMotor rightMotor,int speed) {
		super();
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.speed=speed;
	}

	public IMotor getLeftMotor() {
		return leftMotor;
	}

	public void setLeftMotor(IMotor leftMotor) {
		this.leftMotor = leftMotor;
	}

	public IMotor getRightMotor() {
		return rightMotor;
	}

	public void setRightMotor(IMotor rightMotor) {
		this.rightMotor = rightMotor;
	}

	@Override
	public void forward() {
		
		this.leftMotor.forward();
		this.rightMotor.forward();
		this.leftMotor.setSpeed(speed);
		this.rightMotor.setSpeed(speed);
		this.notifyObservers();
	}

	@Override
	public void backward() {
		this.leftMotor.backward();
		this.rightMotor.backward();
		this.rightMotor.setSpeed(speed);
		this.leftMotor.setSpeed(speed);
		this.notifyObservers();
		
	}

	@Override
	public void rotate(boolean left) {
		// TODO Auto-generated method stub
		if(left) {
			this.leftMotor.backward();
			this.rightMotor.forward();
		}else {
			this.leftMotor.forward();
			this.rightMotor.backward();
		}
		this.rightMotor.setSpeed(speed);
		this.leftMotor.setSpeed(speed);
		this.notifyObservers();
	}

	@Override
	public void left(int ratio) {
		// TODO Auto-generated method stub
		if(ratio>0 && ratio<100) {
			this.leftMotor.setSpeed(this.leftMotor.getSpeed()/ratio);
		}
		this.leftMotor.backward();
		this.rightMotor.forward();
		this.rightMotor.setSpeed(speed);
		this.notifyObservers();
	}

	@Override
	public void right(int ratio) {
		// TODO Auto-generated method stub
		if(ratio>0 && ratio<100) {
			this.rightMotor.setSpeed(this.rightMotor.getSpeed()/ratio);
		}
		this.rightMotor.backward();
		this.leftMotor.forward();
		this.leftMotor.setSpeed(speed);
		this.notifyObservers();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		this.rightMotor.stop();
		this.leftMotor.stop();
		this.leftMotor.setSpeed(speed);
		this.rightMotor.setSpeed(speed);
	}

	@Override
	public void setSpeed(int speed) {
		this.speed=speed;
		this.leftMotor.setSpeed(speed);
		this.rightMotor.setSpeed(speed);		
		
	}
	
	public int getSpeed() {		
		return this.speed;		
	}
	@Override //from Observable and IButtonObservable
	public void addObserver(Observer observer) {
		super.addObserver(observer);
	}
	
	@Override 
	public void notifyObservers() {
		this.setChanged();
		super.notifyObservers(new TankMotorStatus(leftMotor.getSpeed(),rightMotor.getSpeed(),leftMotor.getStatus(),rightMotor.getStatus()));
	}
	
	

}


