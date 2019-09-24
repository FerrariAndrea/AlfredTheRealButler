/* Generated by AN DISI Unibo */ 
package it.unibo.basicrobot

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Basicrobot ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val MotorWorkTime :Int =20;val MotorSleepTime :Int =10;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start basicrobot")
						solve("consult('basicRobotConfig.pl')","") //set resVar	
						solve("robot(R,PORT)","") //set resVar	
						surpluss.motorsSupport.create(myself ,"internalRobotRes" )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t02",targetState="handleRobotCmd",cond=whenDispatch("robotCmd"))
					transition(edgeName="t03",targetState="handleRobotCmd",cond=whenDispatch("internalRobotReq"))
					transition(edgeName="t04",targetState="handleRobotCmd",cond=whenDispatch("internalRobotRes"))
				}	 
				state("handleRobotCmd") { //this:State
					action { //it:State
						delay(50) 
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("robotCmd(CMD)"), Term.createTerm("robotCmd(MOVE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val NonStep =0
								surpluss.motorsSupport.askToMotors( "msg(${payloadArg(0)})"  )
						}
						if( checkMsgContent( Term.createTerm("internalRobotReq(CMD,STEPS,WT,ST)"), Term.createTerm("internalRobotReq(MOVE,STEP,WT,ST)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val Step= payloadArg(1).toInt()
								val WT= payloadArg(2).toInt()
								val ST= payloadArg(3).toInt()
								if(WT<0 || ST <0){ surpluss.motorsSupport.askToMotors( "msg(${payloadArg(0)})", Step, MotorWorkTime, MotorSleepTime  )
								 }
								else
								 { surpluss.motorsSupport.askToMotors( "msg(${payloadArg(0)})", Step, WT, ST  )
								  }
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}