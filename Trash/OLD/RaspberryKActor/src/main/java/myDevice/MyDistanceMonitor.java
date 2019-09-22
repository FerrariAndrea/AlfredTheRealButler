package myDevice;

import com.pi4j.io.gpio.Pin;

import gipo.devices.DistanceMonitor;

public class MyDistanceMonitor extends DistanceMonitor{

	public MyDistanceMonitor(Pin echoPin, Pin trigPin) {
		super(echoPin, trigPin);
		// TODO Auto-generated constructor stub
	}
	@Override
	public float measureDistance() {
		try {
			return super.measureDistance();
		} catch (Exception e) {
			return -1;
		}
		
	}
	@Override
	protected void waitForSignal() throws TimeoutException {
        int countdown = TIMEOUT;
        
        while( this.echoPin.isLow() && countdown > 0 ) {
            countdown--;
            busyWaitNanos(1);
        }
        
        if( countdown <= 0 ) {
            throw new TimeoutException( "Timeout waiting for signal start" );
        }
    }
    
    /**
     * @return the duration of the signal in micro seconds
     * @throws TimeoutException if no low appears in time
     */
	@Override
	protected long measureSignal() throws TimeoutException {
        int countdown = TIMEOUT;
        long start = System.nanoTime();
        while( this.echoPin.isHigh() && countdown > 0 ) {
            countdown--;
            busyWaitNanos(1);
        }
        long end = System.nanoTime();
        
        if( countdown <= 0 ) {
            throw new TimeoutException( "Timeout waiting for signal end" );
        }
        
        return (long)Math.ceil( ( end - start ) / 1000.0 );  // Return micro seconds
    }
	
	 private static void busyWaitNanos(long nanos){
	        long waitUntil = System.nanoTime() + nanos;
	        while(waitUntil > System.nanoTime()){
	            ;
	        }
	    }
}
