package gipo.devices;

import java.util.Observable;
import java.util.Observer;

import gipo.devices.MyMatrixLed.IMG;

public class MotoDetectionMatrixLed implements Observer {

	private MyMatrixLed ml;
	
	public MotoDetectionMatrixLed(MyMatrixLed mml) {
		this.ml = mml;
	}

	public MyMatrixLed getMyMatrixLed() {
		return ml;
	}

	public void setMyMatrixLed(MyMatrixLed mml) {
		this.ml = mml;
	}

	@Override
	public void update(Observable o, Object arg) {		
		//if(o.getClass().equals(PirSensorDigital.class)) {
			
			if(((PirSensorDigital)o).therIsMoto()){
				ml.orientation(0);
				
				try {
					
							ml.draw(0,MyMatrixLed.ImgFactory(IMG.SMILE));
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else {
				try {
					
					ml.draw(0,MyMatrixLed.ImgFactory(IMG.SAD));
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//}
		
	}
	

}
