package itunibo.robotMbot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object mbotSupport{
	lateinit var actor   : ActorBasic
 	lateinit var conn    : SerialPortConnSupport
	var dataSonar        : Double = 0.0
	
	fun create( myactor: ActorBasic, port : String ){
		actor = myactor
		initConn( port )
	}
	
	fun move( cmd : String ){
		println("mbotSupport move cmd=$cmd conn=$conn")
		when( cmd ){
			"msg(w)" -> conn.sendALine("w")
			"msg(s)" -> conn.sendALine("s")
			"msg(a)" -> conn.sendALine("a")
			"msg(d)" -> conn.sendALine("d")
			"msg(h)" -> conn.sendALine("h")
			else -> println("mbotSupport command unknown")
		}
	}
	
	private fun initConn( port : String ){
		try {
			println("mbotSupport initConn starts")
			val serialConn = JSSCSerialComm()
			conn = serialConn.connect(port)	//returns a SerialPortConnSupport
			println("mbotSupport initConn conn= $conn")
			if( conn === null ) return;
			while(true){ 
				val curDataFromArduino = conn.receiveALine()  //consume "start" sent by Arduino
				val istart = curDataFromArduino.contains("start")
				println("mbotSupport initConn received: $curDataFromArduino istart=$istart "   )
				if( istart ) break
			}
			getDataFromArduino();
		}catch(  e : Exception) {
			println("mbotSupport ERROR ${e }"   );
		}		
	}
	
	private fun getDataFromArduino(   ) {
           GlobalScope.launch {
                while (true) {
 						try {
							var curDataFromArduino = conn.receiveALine();
 	 						println("getDataFromArduino received: $curDataFromArduino"    );
 							var v =    curDataFromArduino.toDouble() ;
							//handle too fast change
 							var delta =  Math.abs( v - dataSonar);
 							if( delta < 7 && delta > 0.5 ) {
								dataSonar = v;
								println("mbotSupport sonar: $dataSonar"   );								
								actor.emit("sonarRobot", "sonar( ${dataSonar.toInt()} )");
 							}
						} catch ( e : Exception) {
 							println("getDataFromArduino | ERROR $e   ")
 							//System.exit(1)
                    }
				}
			}
	}
}