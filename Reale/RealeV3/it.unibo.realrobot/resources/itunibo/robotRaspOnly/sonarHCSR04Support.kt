package itunibo.robotRaspOnly

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import java.io.InputStream

object sonarHCSR04Support {
	lateinit var readerPrimary : BufferedReader
	//lateinit var readerStream : InputStream
	lateinit var _actor : ActorBasic
	val maxDiff : Int = 3
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create( actor : ActorBasic, todo : String="" ){
		println("sonarHCSR04Support CREATING")
		var p = Runtime.getRuntime().exec("sudo ./SonarAlone")	
		readerPrimary = BufferedReader(  InputStreamReader(p.getInputStream()))
		_actor= actor
		startRead()		
	}
	
	fun sendTo(value : Int){
			GlobalScope.launch{
				_actor.emit("sonarRobot", "sonar( $value )" )
			}
	}
	
	 fun startRead(   ){				
			Thread {
			
			var old_data = 0
			var data  =readerPrimary.readLine()
			  while(data!=null){
				
				//println("sonarHCSR04Support data = $data"   )
				if( data != null){
					var newData =data.toInt()
					var assoluto = Math.abs(old_data-newData)
					println("------------------------------->sonarHCSR04Support m1 = $newData"   )
					//val m1 = "sonar( $data )"	
	 				if(old_data>newData){
	 						//val m1 = "sonar( $data )"
							sendTo(newData)
							if(assoluto>maxDiff){
								old_data=newData
							}						
					}else if(assoluto>maxDiff){
	 						//val m1 = "sonar( $data )"				
							sendTo(newData)
							old_data=newData
							//println("------------------------------->sonarHCSR04Support m1 = $m1"   )
					}
					
				}
				  data=readerPrimary.readLine()//sperandop sia bloccante			
			}
				
			}.start()
			
			
		
	}
}