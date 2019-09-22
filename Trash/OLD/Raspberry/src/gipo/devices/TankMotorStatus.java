package gipo.devices;

import java.util.Date;

public class TankMotorStatus {
	private Date date;
	private int speed_left;
	private int speed_right;
	private MotorState left;
	private MotorState right;
	
	public TankMotorStatus( int speed_left, int speed_right, MotorState left, MotorState right) {
		this.date = new Date();
		this.speed_left = speed_left;
		this.speed_right = speed_right;
		this.left = left;
		this.right = right;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getSpeed_left() {
		return speed_left;
	}
	public void setSpeed_left(int speed_left) {
		this.speed_left = speed_left;
	}
	public int getSpeed_right() {
		return speed_right;
	}
	public void setSpeed_right(int speed_right) {
		this.speed_right = speed_right;
	}
	public MotorState getLeft() {
		return left;
	}
	public void setLeft(MotorState left) {
		this.left = left;
	}
	public MotorState getRight() {
		return right;
	}
	public void setRight(MotorState right) {
		this.right = right;
	}
	
	
}
