package surpluss

import java.io.BufferedReader
import java.io.InputStreamReader
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.OutputStreamWriter

object pollingSonar {
	
	lateinit var reader_Alone : BufferedReader
	lateinit var writer_Alone : BufferedWriter
	lateinit var reader_Laterali : BufferedReader
	lateinit var writer_Laterali : BufferedWriter
	var instanziato = false
	fun instance(){
		if(!instanziato){
			instanziato=true
				println("sonarHCSR04Support as pollingSonar CREATING")
				var p = Runtime.getRuntime().exec("sudo ./SonarAlonePolling")	
				reader_Alone = BufferedReader(  InputStreamReader(p.getInputStream() ))
				writer_Alone = BufferedWriter(  OutputStreamWriter(p.getOutputStream() ))
				var p2 = Runtime.getRuntime().exec("sudo ./SonarLateraliPolling")	
				reader_Laterali = BufferedReader(  InputStreamReader(p2.getInputStream() ))
				writer_Laterali = BufferedWriter(  OutputStreamWriter(p2.getOutputStream() ))
		
		}
	
	}
	
	fun askToSonarLaterali(left : Int): String{
		
				writer_Laterali.write("$left\n")
				writer_Laterali.flush()
				var data = reader_Laterali.readLine()
				if( data != null ){
					return  "$data"  			
				}else{
					return  "-1" 
				}
		
	
	}
	
	fun askToSonar( ): String{
		
				writer_Alone.write("0\n")
				writer_Alone.flush()
				var data = reader_Alone.readLine()
				if( data != null ){
					return  "$data"  			
				}else{
					return  "-1" 
				}
		
	
	}
	
}