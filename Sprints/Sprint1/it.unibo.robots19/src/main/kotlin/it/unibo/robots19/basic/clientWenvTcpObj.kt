package it.unibo.robots19.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import org.json.JSONObject

    object clientWenvTcpObj {
        private var hostName = "localhost"
        private var port     = 8999
        private val sep      = ";"
        private var outToServer: PrintWriter?     = null
        private var inFromServer: BufferedReader? = null


         fun initClientConn(hostNameStr: String = hostName, portStr: String = "" + port) {
            hostName         = hostNameStr
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

         fun sendMsg(jsonString: String) {
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

                            }
                            "collision" -> {
                                //println( "collision"   );
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val objectName = jsonArg.getString("objectName")
                                println("clientWenvTcp | collision objectName=$objectName")
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

/*
suspend fun doJobObject() {
    clientWenvTcp.initClientConn()
    var jsonString = ""
     for (i in 1..3) {
        jsonString = "{ 'type': 'moveForward', 'arg': 800 }"
         clientWenvTcp.sendMsg(jsonString)
        delay(1000)

        jsonString = "{ 'type': 'moveBackward', 'arg': 800 }"
         clientWenvTcp.sendMsg(jsonString)
        delay(1000)
    }
}

fun mainn( ) = runBlocking {
    println("==============================================")
    println("PLEASE, ACTIVATE WENV ")
    println("==============================================")
    doJobObject()
}
*/