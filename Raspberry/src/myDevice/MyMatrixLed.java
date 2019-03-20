package myDevice;

import java.io.IOException;

import gipo.devices.MatrixLed;
import gipo.devices.MatrixLed.Constants;
import gipo.devices.MatrixLed.Font;

public class MyMatrixLed extends MatrixLed{

	
	
	
	public MyMatrixLed(short cascaded) {
		super(cascaded);
		// TODO Auto-generated constructor stub
	}

	public void draw(int device,byte[] img) throws Exception {
			
		short col = Constants.MAX7219_REG_DIGIT0;
		for(byte value:img){
			if(col>Constants.MAX7219_REG_DIGIT7) return;
			
			this._setbyte(device,col,(byte)(value&0xff));
			col+=1;
		}
		super.flush();
	}
	
	public static byte[] ImgFactory(IMG type) {
		if(type==IMG.SMILE) {
			return new byte[] {0x00,(byte) 0xe7,  (byte) 0xa5, (byte) 0xe7, 0x00,  (byte) 0xc3,  (byte) 0x66,  (byte) 0x3c};
		}
		if(type==IMG.WINK) {
			return new byte[] {0x00,(byte) 0xE0,  (byte) 0xa7, (byte) 0xe0, 0x00,  (byte) 0x66,  (byte) 0x3c,  (byte) 0x00};
		}
		if(type==IMG.SAD) {
			return new byte[] {(byte) 0xe7,  (byte) 0xa5, (byte) 0xe7, 0x2, 0x00, 0x3C, 0x42, (byte) 0x81};
		}
		return new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	}
	public enum IMG{
		SMILE,
		SAD,
		WINK
	}
}
