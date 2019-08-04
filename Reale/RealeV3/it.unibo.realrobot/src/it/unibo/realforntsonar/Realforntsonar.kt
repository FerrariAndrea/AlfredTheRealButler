/* Generated by AN DISI Unibo */ 
package it.unibo.realforntsonar

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Realforntsonar ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
			
				var ActualTimer : Long = 80
				var PollingModeOn =true
				var NeedAck =true
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						surpluss.pollingSonar.instance(  )
						println("Start realSonar")
					}
					 transition( edgeName="goto",targetState="pollingMode", cond=doswitchGuarded({PollingModeOn}) )
					transition( edgeName="goto",targetState="reqResMode", cond=doswitchGuarded({! PollingModeOn}) )
				}	 
				state("pollingMode") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_pollingMode", 
							scope, context!!, "local_tout_realforntsonar_pollingMode", ActualTimer )
					}
					 transition(edgeName="t044",targetState="doNotifyAll",cond=whenTimeout("local_tout_realforntsonar_pollingMode"))   
					transition(edgeName="t045",targetState="changeMode",cond=whenDispatch("internalSonarChangeMode"))
				}	 
				state("changeMode") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("internalSonarChangeMode(MODE)"), Term.createTerm("internalSonarChangeMode(MODE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val MODE: Int = Integer.parseInt( payloadArg(0) ) 
								if(MODE==0){ PollingModeOn=true;NeedAck=true;
								 }
								else
								 { PollingModeOn=false
								  }
						}
					}
					 transition( edgeName="goto",targetState="pollingMode", cond=doswitchGuarded({PollingModeOn}) )
					transition( edgeName="goto",targetState="reqResMode", cond=doswitchGuarded({! PollingModeOn}) )
				}	 
				state("doNotifyAll") { //this:State
					action { //it:State
						val Distance = surpluss.pollingSonar.askToSonar().toInt()
						if(Distance>0){ emit("sonarRobot", "sonar($Distance)" ) 
						 }
					}
					 transition( edgeName="goto",targetState="pollingMode", cond=doswitchGuarded({PollingModeOn}) )
					transition( edgeName="goto",targetState="reqResMode", cond=doswitchGuarded({! PollingModeOn}) )
				}	 
				state("reqResMode") { //this:State
					action { //it:State
						if(NeedAck){ replyToCaller("internalSonarChangeMode", "internalSonarChangeMode(2)")
						 }
					}
					 transition(edgeName="t046",targetState="changeMode",cond=whenDispatch("internalSonarChangeMode"))
					transition(edgeName="t047",targetState="doNotifyAll",cond=whenDispatch("internalSonarReq"))
				}	 
			}
		}
}
