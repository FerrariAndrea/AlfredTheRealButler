package surpluss

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import java.io.InputStream

object timerSupport {
	lateinit var _actor : ActorBasic
	lateinit var _th : Thread
	
	
	fun create( actor : ActorBasic, todo : String="" ){
		println("timerSupport CREATING")
		_actor= actor
		
	}
	
	
	fun startTimer(value :Long){
		_th= Thread {
			  Thread.sleep(value)
			  GlobalScope.launch{
				_actor.autoMsg("internalTickTimer", "internalTickTimer(tick)" )
			  }
		}
		_th.start()
		
	}
	fun resetTimer(){
		_th.destroy()		
	}
	
}