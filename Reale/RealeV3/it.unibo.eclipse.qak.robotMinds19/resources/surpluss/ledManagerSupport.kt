package surpluss

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import java.io.BufferedWriter
import java.io.OutputStream

object ledManagerSupport {
	lateinit var writeFrontLed : OutputStream
	var blinking = false
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create(){//actor : ActorBasic, todo : String=""
		println("ledManagerSupport CREATING")
		val p = Runtime.getRuntime().exec("sudo ./FrontLed")
		writeFrontLed = p.outputStream
	}
	
	
	fun frontLedOn(){
		writeFrontLed.write(1)
	}
	
	fun frontLedOff(){
		blinking=false
		writeFrontLed.write(0)
	}
	
	fun frontLedBlink(intervall:Long){
		blinking=true
		while(blinking){
			frontLedOn()
			Thread.sleep(intervall)
			frontLedOff()
			Thread.sleep(intervall)
		}
	}
	
	
}