package itunibo.robotRaspOnly

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay

object sonarBelancerOnlySupport {
	var actualValue = "0"
	val belancerMsgName = "sonarBelancer"
	var  myActor: ActorBasic? = null
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create( actor : ActorBasic ){
		println("sonarBelancerOnlySupport CREATING")
		myActor=actor
		val pBelancer = Runtime.getRuntime().exec("sudo ./SonarForBelance")	
		var readerBelancer = BufferedReader(  InputStreamReader(pBelancer.getInputStream() ))		
		startRead(readerBelancer)
	}
	
	fun startRead( reader : BufferedReader){
		GlobalScope.launch{
			while( true ){
				var data = reader.readLine()
				if( data != null ){					
	 				actualValue = "sonar( $data )"
				}
				delay( 250 )
			}
		}
	}
	
	fun requestValue(){
		
		GlobalScope.launch{
			delay(2)
			myActor!!.emit(belancerMsgName,actualValue )
		}
	}
}