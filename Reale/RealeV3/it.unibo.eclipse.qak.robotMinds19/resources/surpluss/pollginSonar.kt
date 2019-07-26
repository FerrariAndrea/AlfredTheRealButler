package surpluss

import java.io.BufferedReader
import java.io.InputStreamReader
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object pollingSonar {
	lateinit var reader : BufferedReader
	fun instance(){
		println("sonarHCSR04Support as pollingSonar CREATING")
		var p = Runtime.getRuntime().exec("sudo ./SonarAlone")	
		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		
	}
	fun askToSonar( ): String{
		
				var data = reader.readLine()
				//svuoto il buffer
				while(reader.ready()){
					data = reader.readLine()
				}
				if( data != null ){
					return  "$data"  			
				}else{
					return  "-1" 
				}
		
	
	}
	
}