package surpluss

import java.io.BufferedReader
import it.unibo.kactor.ActorBasic
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object bussolaSupport {
		fun askToCompass( actor : ActorBasic, todo : String="" ){
		val p = Runtime.getRuntime().exec("python bussola.py")
		var reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		var data = reader.readLine()
		GlobalScope.launch{
				if( data != null ){
					val m = "compass( $data )"
					actor.emit("compassRobot",m)
				}else{
					val m = "compass( error )"
					actor.emit("compassRobot",m)
				}
				
		}
		
	
	}
	
}