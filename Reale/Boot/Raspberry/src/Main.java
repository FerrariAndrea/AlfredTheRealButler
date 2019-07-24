
import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import gipo.devices.*;
import gipo.devices.MyMatrixLed.IMG;
import logos.Bootinglogo;

public class Main {
	public static MyMatrixLed ml= new MyMatrixLed((short)1);	
	@SuppressWarnings("deprecation")
	public static void main(String[] args)  
	{
		System.out.println("BootRasp");	
		
		ml.open();
		ml.brightness((byte) 8);		
		ml.orientation(180);	
		try {
			ml.draw(0,MyMatrixLed.ImgFactory(IMG.BOOTING));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Pin> gipos= new ArrayList<Pin>();
		try {
			File f = new File("./giporeset.txt");
			java.util.List<String> lines =Files.readAllLines(f.toPath());
			for(int x = 0;x<lines.size();x++) {
				gipos.add(RaspiPin.getPinByAddress(Integer.parseInt(lines.get(x))));
			}
			setOff(gipos);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s(1000);
		BootinglogoMenagment btm = new BootinglogoMenagment(ml);
		
		btm.startWaitingConnection();
		s(1500);
		while(ConnectedUser.getGateway()!=null) {
			s(1000);
		}
		btm.stop();
		s(150);
		
		btm.startWaitingLogin();
		
		//------------------------------------------------->DO YOUR JOB HERE!!!!!
		
		while(ConnectedUser.numberOfConnectedUser()<1) {
			s(1000);
		}
		btm.stop();
		s(150);
		try {
			ml.draw(0,MyMatrixLed.ImgFactory(IMG.SMILE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s(500);
		System.out.print("Finish booting :");
		try {
			ml.draw(0,MyMatrixLed.ImgFactory(IMG.CLEAN));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private static void setOff(ArrayList<Pin> gipos) {
		
		Iterator<Pin > iter =gipos.iterator();
		while(iter.hasNext()) {
			GpioFactory.getInstance().provisionDigitalOutputPin(iter.next(), "MyLED", PinState.LOW).low();
		}
	}

	private static void s(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}



