package surpluss

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

object ledManagerSupport {
	lateinit var writeFrontLed : BufferedWriter
	//var blinking = false
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun instance(){//actor : ActorBasic, todo : String=""
		println("ledManagerSupport CREATING")
		val p = Runtime.getRuntime().exec("sudo ./FrontLed")
		writeFrontLed = BufferedWriter( OutputStreamWriter(p.outputStream))
	}
	

	 fun frontLedOn(){
		 writeFrontLed.write("1\n")
		 writeFrontLed.flush()
	}
	
	 fun frontLedOff(){
		 writeFrontLed.write("0\n")
		 writeFrontLed.flush()
	}
	
	/*
	fun frontLedBlink(intervall:Long){
		blinking=true
		while(blinking){
			frontLedOnEnable(true)
			Thread.sleep(intervall)
			if(blinking)
			frontLedOnEnable(false)
			Thread.sleep(intervall)
		}
	}
	*/
	
}