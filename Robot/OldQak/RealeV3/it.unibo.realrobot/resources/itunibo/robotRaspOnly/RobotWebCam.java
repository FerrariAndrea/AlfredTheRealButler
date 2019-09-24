package itunibo.robotRaspOnly;
import java.io.IOException;

import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedPlainObject;

public class RobotWebCam extends SituatedPlainObject{
	public RobotWebCam(IOutputView outView) throws Exception {
		super(outView);
		setModProbe();		 
	}
	protected void setModProbe() throws IOException{
 			Runtime.getRuntime().exec("sudo modprobe bcm2835-v4l2");		
	}
	
	public void setForImage(int width, int height ){ //width=2592,height=1944
		/*
		 * sudo modprobe bcm2835-v4l2
		 * v4l2-ctl --set-fmt-video=width=2592,height=1944,pixelformat=3
		 *  v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to=somefile.jpg
		 */
		try {
 			Runtime.getRuntime().exec(
 					"v4l2-ctl --set-fmt-video=width="+ width+",height="+ height+",pixelformat=3");
		} catch (IOException e) {
 			e.printStackTrace();
		}
		
	}
	
	public void setForVideo(){
		/*
		 * sudo modprobe bcm2835-v4l2
		 * v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4
		 *  v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to=somefile.jpg
		 */
		try {
 			Runtime.getRuntime().exec(
 					"v4l2-ctl --set-fmt-video=width=1920,height=1088,pixelformat=4");
		} catch (IOException e) {
 			e.printStackTrace();
		}
		
	}
	
	public void captureImg(String fName) throws Exception{
		Runtime.getRuntime().exec("v4l2-ctl --stream-mmap=3 --stream-count=1 --stream-to="+fName);
	}
	public void captureVideo(int nframes, String fName) throws Exception{ //somefile.h264
		Runtime.getRuntime().exec("v4l2-ctl --stream-mmap=3 --stream-count="+nframes+" --stream-to="+fName);
	}
}
