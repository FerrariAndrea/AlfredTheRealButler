package itunibo.robotVirtual

import it.unibo.kactor.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import org.json.JSONObject
import alice.tuprolog.*

    object clientWenvObjTcp {
        private var hostName = "localhost"
        private var port     = 8999
        private val sep      = ";"
        private var outToServer: PrintWriter?     = null
        private var inFromServer: BufferedReader? = null
		
        fun initClientConn(actor:ActorBasic, hostName: String = "localhost", portStr: String = "8999"  ) {
            port  = Integer.parseInt(portStr)
            try {
                val clientSocket = Socket(hostName, port)
                println("clientWenvObjTcp |  CONNECTION DONE")
                inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                outToServer  = PrintWriter(clientSocket.getOutputStream())
                startTheReader( actor )
            }catch( e:Exception ){
                println("clientWenvObjTcp | ERROR $e")
            }
        }

 
        fun sendMsg(v: String) {
			//println("clientWenvObjTcp | sending Msg $v   ")
			var outS = "{'type': 'alarm', 'arg': 0 }"
			val t = Term.createTerm(v) as Struct
			val ts = t.getArg(0).toString()
			when( ts ){
				"w"   -> outS = "{'type': 'moveForward',  'arg': -1 }"
    			"s"  -> outS = "{'type': 'moveBackward', 'arg': -1 }"
				"a"  -> outS = "{'type': 'turnLeft', 'arg': 400 }"
 				"d"  -> outS = "{'type': 'turnRight', 'arg': 400 }"
   			    "h"  -> outS = "{'type': 'alarm', 'arg': 0 }"
 			}
			val jsonObject = JSONObject(outS)
			val msg= "$sep${jsonObject.toString()}$sep"
			//println("clientWenvObjTcp | sendMsg $msg   ")
			outToServer?.println(msg)
            outToServer?.flush()
         }

        private fun startTheReader( actor:ActorBasic  ) {
            GlobalScope.launch {
                while (true) {
                    try {
                        val inpuStr = inFromServer?.readLine()
                        val jsonMsgStr =
                            inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        //println("clientWenvObjTcp | inpuStr= $jsonMsgStr")
                        val jsonObject = JSONObject(jsonMsgStr)
                        //println( "type: " + jsonObject.getString("type"));
                        when (jsonObject.getString("type")) {
                            "webpage-ready" -> println("webpage-ready ")
                            "sonar-activated" -> {
                                //println("sonar-activated ")
                                val jsonArg   = jsonObject.getJSONObject("arg")
                                val sonarName = jsonArg.getString("sonarName")
                                val distance  = jsonArg.getInt("distance")
                                //emitLocalStreamEvent( m )
 								val m1 = "sonar($sonarName, $distance)"
								//println( "clientWenvObjTcp EMIT $m1"   );
							    actor.emit("sonar",m1 );
                            }
                            "collision" -> {
                                //println( "collision"   );
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val objectName = jsonArg.getString("objectName")
                                println("clientWenvObjTcp | collision objectName=$objectName")
                                //val m = MsgUtil.buildEvent( "tcp", "collision","collision($objectName)")
								//println("clientWenvObjTcp | emit $m")
                                //emitLocalStreamEvent( m )
 							     actor.emit("sonarRobot","sonar(5)"
									.replace("TARGET", objectName
									.replace("-", "")));
                           }
                        }
                    } catch (e: IOException) {
                        //e.printStackTrace()
						println("clientWenvObjTcp | ERROR $e   ")
						System.exit(1)
                    }
                }
            }
         }//startTheReader
}//clientWenvObjTcp


