package it.unibo.robots19.basic

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import org.json.JSONObject

    class clientWenvTcp( name : String, scope: CoroutineScope) : ActorBasic(name,scope){
        private var hostName = "localhost"
        private var port     = 8999
        private val sep      = ";"
        private var outToServer: PrintWriter?     = null
        private var inFromServer: BufferedReader? = null
/*
        companion object {
            //var worker : clientWenvTcp


        }//companion object

 */
        init{
            initClientConn()
        }

        fun initClientConn(hostName: String = "localhost", portStr: String = "8999"  ) {
            //hostName         = hostNameStr
            port             = Integer.parseInt(portStr)
            try {
                val clientSocket = Socket(hostName, port)
                println("clientWenvTcp |  CONNECTION DONE")
                inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                outToServer  = PrintWriter(clientSocket.getOutputStream())
                startTheReader()
            }catch( e:Exception ){
                println("clientWenvTcp | ERROR $e")
            }
        }

        override suspend fun actorBody(msg: ApplMessage) {
            println("clientWenvTcp | receives $msg   ")
            when( msg.msgId() ){
                "start" -> initClientConn()
                "send"  -> sendMsg( msg.msgContent() )
                else -> println("clientWenvTcp $msg UNKNOWN ")
            }
        }

        fun sendMsg(v: String) {
            val jsonString = v.replace("'{","{").replace("}'","}")
            println("clientWenvTcp | sendMsg $jsonString   ")
            val jsonObject = JSONObject(jsonString)
            val msg = "$sep${jsonObject.toString()}$sep"
            outToServer?.println(msg)
            outToServer?.flush()
        }

        private fun startTheReader(   ) {
            GlobalScope.launch {
                while (true) {
                    try {
                        val inpuStr = inFromServer?.readLine()
                        val jsonMsgStr =
                            inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        //println("clientWenvTcp | inpuStr= $jsonMsgStr")
                        val jsonObject = JSONObject(jsonMsgStr)
                        //println( "type: " + jsonObject.getString("type"));
                        when (jsonObject.getString("type")) {
                            "webpage-ready" -> println("webpage-ready ")
                            "sonar-activated" -> {
                                //println("sonar-activated ")
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val sonarName = jsonArg.getString("sonarName")
                                val distance = jsonArg.getInt("distance")
                                println("clientWenvTcp | sonarName=$sonarName distance=$distance")
                                val m = MsgUtil.buildEvent("tcp", sonarName,""+distance )
                                emitLocalStreamEvent( m )
                                emit( m )
                            }
                            "collision" -> {
                                //println( "collision"   );
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val objectName = jsonArg.getString("objectName")
                                println("clientWenvTcp | collision objectName=$objectName")
                                val m = MsgUtil.buildEvent( "tcp", "collision",objectName)
                                emitLocalStreamEvent( m )
                                emit( m )
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
         }//startTheReader
}//clientTcp


//RAPID CHECK
suspend fun doJob(scope: CoroutineScope) {
    val c = clientWenvTcp("clientRobotVirtual", scope)
    val sink = Sink("sink", scope )
    c.subscribe( sink )
    MsgUtil.sendMsg("start","start",c)
    var jsonString = ""
     for (i in 1..3) {
        jsonString = "{ 'type': 'moveForward', 'arg': -1 }"
         MsgUtil.sendMsg("send",jsonString,c)
        delay(1500)
        jsonString = "{ 'type': 'moveBackward', 'arg': -1 }"
         MsgUtil.sendMsg("send",jsonString,c)
        delay(1000)
    }
}


fun main( ) = runBlocking {
    println("==============================================")
    println("PLEASE, ACTIVATE WENV ")
    println("==============================================")
    doJob(this)
}
