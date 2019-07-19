package itunibo.robotRaspOnly

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay

object otherSonarsSupport {
	val leftMsgName = "sonarLeft"
	val rigthMsgName = "sonarRigth"
	val belancerMsgName = "sonarBelancer"
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create( actor : ActorBasic,left : Boolean=true, rigth : Boolean=true, belancer: Boolean=true ){
		println("otherSonarsSupport CREATING")
		val pLeft = Runtime.getRuntime().exec("sudo ./SonarLeft")
		val pRigth = Runtime.getRuntime().exec("sudo ./SonarRigth")
		val pBelancer = Runtime.getRuntime().exec("sudo ./SonarForBelance")		
		var readerLeft = BufferedReader(  InputStreamReader(pLeft.getInputStream() ))
		var readerRigth = BufferedReader(  InputStreamReader(pRigth.getInputStream() ))
		var readerBelancer = BufferedReader(  InputStreamReader(pBelancer.getInputStream() ))		
		if(belancer) {
			println("otherSonarsSupport belancer on")		
			startRead( actor,readerBelancer,2,belancerMsgName)
		}
		if(left) {
			println("otherSonarsSupport left on")	
			startRead( actor,readerLeft,6,leftMsgName)
		}
		if(rigth) {
			println("otherSonarsSupport rigth on")
			startRead( actor,readerRigth,6,rigthMsgName)
		}
	}
	
	fun startRead( actor: ActorBasic , reader : BufferedReader, windowSize : Int, msgName : String ){
		GlobalScope.launch{
			var old_data = 0
			while( true ){
				var data = reader.readLine()
				//println("sonarHCSR04Support data = $data"   )
				if( data != null ){					
	 				val m1 = "sonar( $data )"
					if(Math.abs(old_data-data.toInt())>windowSize){
							actor.emit(msgName,m1 )
							old_data=data.toInt()
					}
					//println("sonarHCSR04Support m1 = $m1"   )
				
				}
				delay( 250 )
			}
		}
	}
}