package logos;
import java.awt.Point;
import java.util.HashMap;

import gipo.devices.MyMatrixLed.IMG;

public class ConnectingLogo {

	public static int actual =-1;
	
	public static byte[]   next() {
		actual++;
		if(actual>8) {
			actual=0;
		}
		if(actual==0) {
			return ImgFactory(IMG.ONE);
		}else if(actual==1) {
			return ImgFactory(IMG.ZERO);
		}else if(actual==2) {
			return ImgFactory(IMG.ONE);
		}else if(actual==3) {
			return ImgFactory(IMG.TOW);
		}else if(actual==4) {
			return ImgFactory(IMG.ONE);
		}else if(actual==5) {
			return ImgFactory(IMG.TOW);
		}else if(actual==6) {
			return ImgFactory(IMG.THREE);
		}else if(actual==7) {
			return ImgFactory(IMG.TOW);
		}else{
			return ImgFactory(IMG.THREE);			
		}
		
	}

	public static byte[] ImgFactory(IMG type) {
		if(type==IMG.ONE) {
			return new byte[] {0x00, 0x00,  0x00,0x00, (byte) 0xc0,  (byte) 0xc0, (byte) 0xc0, (byte) 0xc0};
		}
		if(type==IMG.TOW) {
			return new byte[] {0x00, 0x00,  (byte) 0x18,(byte) 0x18, (byte) 0xd8,  (byte) 0xd8, (byte) 0xd8, (byte) 0xd8};
		}
		if(type==IMG.THREE) {
			return new byte[] {(byte) 0x3,  (byte) 0x3, (byte) 0x1b, (byte) 0x1b, (byte) 0xdb,  (byte) 0xdb	,  (byte) 0xdb,  (byte) 0xdb};
		}
		return new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	}
	public enum IMG{
		ONE,
		TOW,
		THREE,
		ZERO
	}
}
