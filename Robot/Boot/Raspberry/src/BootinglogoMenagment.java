import gipo.devices.MyMatrixLed;
import logos.Bootinglogo;
import logos.ConnectingLogo;

public class BootinglogoMenagment {

	
	private  MyMatrixLed ml;
	private Thread thlogo;
	
	
	public BootinglogoMenagment(MyMatrixLed ml) {
		super();
		this.ml = ml;
	}


	public void startWaitingLogin() {
		Runnable logo = new Runnable() {
			public void run() {
				Bootinglogo.init();
				while(true) {
					try {
						ml.draw(0,Bootinglogo.convertMatrix(Bootinglogo.next()));				
						//Bootinglogo.printMatrix(Bootinglogo.next());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thlogo = new Thread(logo);
		thlogo.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startWaitingConnection() {
		Runnable logo = new Runnable() {
			public void run() {
				
				while(true) {
					try {
						ml.draw(0,ConnectingLogo.next());				
						//Bootinglogo.printMatrix(Bootinglogo.next());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thlogo = new Thread(logo);
		thlogo.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void stop() {
		thlogo.stop();
	}
	
	
}
