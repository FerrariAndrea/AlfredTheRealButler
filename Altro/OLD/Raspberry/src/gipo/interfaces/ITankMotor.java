package gipo.interfaces;

public interface ITankMotor {

	public void forward();
	public void backward();
	public void rotate(boolean left);
	public void left(int ratio);
	public void right(int ratio);
	public void stop();
	public void setSpeed(int speed);
	
}
