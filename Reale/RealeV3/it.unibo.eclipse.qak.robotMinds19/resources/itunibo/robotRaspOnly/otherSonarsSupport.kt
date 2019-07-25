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
		if(belancer) {
			println("otherSonarsSupport belancer on")
			val pBelancer = Runtime.getRuntime().exec("sudo ./SonarForBelance")
			var readerBelancer = BufferedReader(  InputStreamReader(pBelancer.getInputStream() ))		
			startRead( actor,readerBelancer,2,belancerMsgName)
		}
		if(left) {
			println("otherSonarsSupport left on")
			val pLeft = Runtime.getRuntime().exec("sudo ./SonarLeft")
			var readerLeft = BufferedReader(  InputStreamReader(pLeft.getInputStream() ))
			startRead( actor,readerLeft,5,leftMsgName)
		}
		if(rigth) {			
			println("otherSonarsSupport rigth on")
			val pRigth = Runtime.getRuntime().exec("sudo ./SonarRigth")	
			var readerRigth = BufferedReader(  InputStreamReader(pRigth.getInputStream() ))
			startRead( actor,readerRigth,5,rigthMsgName)
		}
	}
	
	fun startRead( actor: ActorBasic , reader : BufferedReader, windowSize : Int, msgName : String ){
		GlobalScope.launch{
			var old_data = 0
			while( true ){
				var data = reader.readLine()
				//println("sonarHCSR04Support data = $data"   )
				if( data != null ){					
	 				val m1 = "$msgName( $data )"
					if(Math.abs(old_data-data.toInt())>windowSize){
							actor.emit(msgName,m1 )
							old_data=data.toInt()
					}
					//println("sonarHCSR04Support m1 = $m1"   )
				
				}
				//delay( 250 )
			}
		}
	}
}