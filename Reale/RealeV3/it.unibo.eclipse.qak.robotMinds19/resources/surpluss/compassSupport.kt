package surpluss

import java.io.BufferedReader
import it.unibo.kactor.ActorBasic
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.OutputStreamWriter


object compassSupport {
	
	lateinit var reader_compass: BufferedReader
	lateinit var writer_compass : BufferedWriter
	fun instance(){
		println("bussolaSuport CREATING")
		val p = Runtime.getRuntime().exec("python bussola.py")
		reader_compass = BufferedReader(  InputStreamReader(p.getInputStream() ))
		writer_compass = BufferedWriter(  OutputStreamWriter(p.getOutputStream() ))
		
	}

	
	
	fun askToCompass( ): String{
		
				writer_compass.write("0\n")
				writer_compass.flush()
				var data = reader_compass.readLine()
				if( data != null ){
					return  "$data"  			
				}else{
					return  "null" 
				}
		
	
	}
	
	/*
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
	*/
}