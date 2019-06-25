package it.unibo.robots19.basic


import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BasicRobotExecutor( name : String, scope: CoroutineScope) : ActorBasic(name,scope){
/*
    val c = clientWenvTcp("clientRobotVirtual", scope)
    val sink = Sink("sink", scope )
    init{ c.subscribe(sink) }
*/
    val c = "tcpClient"

    override suspend fun actorBody(msg: ApplMessage){
        println("BasicRobotExecutor | receives $msg   ")
        when( msg.msgId() ){    //robotCmd
            RobotCmds.id ->
                when( msg.msgContent()  ) {
                    RobotCmds.forwardStr -> //robotCmd(forward)
                    //{ clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.forwardStr)!!) }
                        forward( "send", RobotCmds.cmdMap.get(RobotCmds.forwardStr)!!, c )
                    RobotCmds.backwardStr ->
                    //{ clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.backwardStr)!!) }
                        forward( "send", RobotCmds.cmdMap.get(RobotCmds.backwardStr)!!, c)
                    RobotCmds.stopStr ->
                   // { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.stopStr)!!) }
                        forward( "send", RobotCmds.cmdMap.get(RobotCmds.stopStr)!!, c)
                    RobotCmds.turnLeftStr ->
                    //{ clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.turnLeftStr)!!) }
                        forward( "send", RobotCmds.cmdMap.get(RobotCmds.turnLeftStr)!!, c)
                    RobotCmds.turnRightStr ->
                    //{ clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.turnRightStr)!!) }
                        forward( "send", RobotCmds.cmdMap.get(RobotCmds.turnRightStr)!!, c)
                    else -> println("BasicRobotExecutor | msg content UNKNOWN")
                }
            else -> println("BasicRobotExecutor | $msg UNKNOWN ")
        }
    }
}

//Rapide check
fun main() = runBlocking {
    println("START   ")
    val a = BasicRobotExecutor("robotExecutor", this)
//    val sink = Sink("sink", this )
//    a.subscribe(sink)

    MsgUtil.sendMsg("main",RobotCmds.id, RobotCmds.turnLeftStr, a)
    delay(1000)
    MsgUtil.sendMsg("main",RobotCmds.id, RobotCmds.turnRightStr, a)
    delay(1000)
    MsgUtil.sendMsg("main",RobotCmds.id, RobotCmds.forwardStr, a)
    delay(1500)
    MsgUtil.sendMsg("main",RobotCmds.id, RobotCmds.backwardStr, a)
    delay(1000)
    MsgUtil.sendMsg("main",RobotCmds.id, RobotCmds.stopStr, a)

    println("END   ")
}