package surpluss

import java.io.BufferedReader
import it.unibo.kactor.ActorBasic
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.OutputStreamWriter


object motorsSupport {
	
	lateinit var reader_compass: BufferedReader
	lateinit var writer_compass : BufferedWriter
	lateinit var actor : ActorBasic
	lateinit var msgType : String
	fun create(actor : ActorBasic, msgType : String){
		println("motorsSupport CREATING")
		val p = Runtime.getRuntime().exec("sudo ./Motors")
		this.reader_compass = BufferedReader(  InputStreamReader(p.getInputStream() ))
		this.writer_compass = BufferedWriter(  OutputStreamWriter(p.getOutputStream() ))
		this.actor=actor
		this.msgType=msgType
	}


	fun askToMotors(cmd : String, steps :Int ){
		when( cmd ){
			"msg(h)" ->{imperativeOrder(0)}			
			"msg(w)" ->{imperativeOrder(1)}
			"msg(a)" ->{imperativeOrder(2)}
			"msg(s)" ->{imperativeOrder(3)}
			"msg(d)" ->{imperativeOrder(4)}
			"msg(am)" ->{asyncAsk(11,steps)}
			"msg(dm)" ->{asyncAsk(12,steps)}
			"msg(ws)" ->{asyncAsk(7,steps)}
			"msg(as)" ->{asyncAsk(8,steps)}
			"msg(ss)" ->{asyncAsk(9,steps)}
			"msg(ds)" ->{asyncAsk(10,steps)}
			}
	}
	
	private fun asyncAsk(cmdForC : Int, steps : Int){
		//reader_compass.reset()//non so se sia opportuno ( è per sicurezza)
		writer_compass.write("$cmdForC-$steps\n")
		writer_compass.flush()		
		 GlobalScope.launch{
				var data = reader_compass.readLine()				
				actor.emit(msgType, msgType+"($data)" )
		}
	}
	
	private fun imperativeOrder(cmdForC : Int){
		writer_compass.write("$cmdForC-0\n")
		writer_compass.flush()
	}
}