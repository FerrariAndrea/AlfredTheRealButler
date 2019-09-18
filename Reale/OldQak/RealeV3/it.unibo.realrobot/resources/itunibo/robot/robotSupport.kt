package itunibo.robot
import it.unibo.kactor.ActorBasic
 

object robotSupport{
	lateinit var robotKind : String
	
	fun create( actor: ActorBasic, robot : String, port: String ){
		robotKind = robot
		when( robotKind ){
			"virtual"    ->  { itunibo.robotVirtual.clientWenvObjTcp.initClientConn( actor, "localhost" ) }
			"realmbot"   ->  { itunibo.robotMbot.mbotSupport.create( actor, port ) }  //port="/dev/ttyUSB0"   "COM6"
			"realnano" ->    { it.unibo.robotRaspOnly.nanoSupport.create(actor, true ) }
			else -> println( "robot unknown" )
		}
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		//println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { itunibo.robotVirtual.clientWenvObjTcp.sendMsg(  cmd ) }	
			"realmbot" -> { itunibo.robotMbot.mbotSupport.move( cmd ) }
			"realnano" -> { it.unibo.robotRaspOnly.nanoSupport.move( cmd ) }
			else       -> println( "robot unknown" )
		}
		
	}
	
}