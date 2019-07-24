package itunibo.robotRaspOnly

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay

object sonarHCSR04Support {
	lateinit var readerPrimary : BufferedReader
	
	
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create( actor : ActorBasic, todo : String="" ){
		println("sonarHCSR04Support CREATING")
		val p = Runtime.getRuntime().exec("sudo ./SonarAlone")
		readerPrimary = BufferedReader(  InputStreamReader(p.getInputStream() ))
		startRead( actor )
	}
	
	fun startRead( actor: ActorBasic  ){
		GlobalScope.launch{
			var old_data = 0
			while( true ){
				var data = readerPrimary.readLine()
				//println("sonarHCSR04Support data = $data"   )
				if( data != null ){					
	 				val m1 = "sonar( $data )"
					if(Math.abs(old_data-data.toInt())>3){
							actor.emit("sonarRobot",m1 )
							old_data=data.toInt()
					}
					//println("sonarHCSR04Support m1 = $m1"   )
				
				}
				delay( 120 )
			}
		}
	}
}