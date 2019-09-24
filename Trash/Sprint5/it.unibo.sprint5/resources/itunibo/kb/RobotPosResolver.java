package itunibo.kb;

import java.awt.Point;

public class RobotPosResolver {

	/*
	private static Point p= new Point(0,0);
	private static int direction =0;
	public static  int getPos_x() {
		return p.x;
	}
	public static  int getPos_y() {
		return p.y;
	}
	*/
	public static  String convertDirection( int direction) {
		if(direction==0) {
			return "sud";
		}
		if(direction==1) {
			return "est";
		}
		if(direction==2) {
			return "nord";
		}
		if(direction==3) {
			return "ovest";
		}
		return null;
	}
	/*
	public static void update(int actualDirection,String msg) {
		if(msg=="a") {
			direction=direction-1;
			if(direction<0) {
				direction=3;
			}
		}
		if(msg=="d") {
			direction=direction+1;
			if(direction>3) {
				direction=0;
			}
		}
		if(msg=="w") {
			if(direction==0) {
				p=new Point(p.x,p.y+1);
			}
			if(direction==1) {
				p=new Point(p.x-1,p.y);			
			}
			if(direction==2) {
				p=new Point(p.x,p.y-1);
			}
			if(direction==3) {
				p=new Point(p.x+1,p.y);
			}
		}
		if(msg=="s") {
			if(direction==0) {
				p=new Point(p.x,p.y-1);
			}
			if(direction==1) {
				p=new Point(p.x+1,p.y);			
			}
			if(direction==2) {
				p=new Point(p.x,p.y+1);
			}
			if(direction==3) {
				p=new Point(p.x-1,p.y);
			}
		}
	}
	*/
}
