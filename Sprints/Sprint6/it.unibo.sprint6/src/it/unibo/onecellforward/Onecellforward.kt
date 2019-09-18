/* Generated by AN DISI Unibo */ 
package it.unibo.onecellforward

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Onecellforward ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var foundObstacle = false;
				var StepTime = 0L;
				var Duration : Long =0;
				var PauseTime :Long =250;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start onecellforward")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
				state("ready") { //this:State
					action { //it:State
						foundObstacle = false
					}
					 transition(edgeName="t08",targetState="doMoveForward",cond=whenDispatch("onestep"))
					transition(edgeName="t09",targetState="paused",cond=whenDispatch("stop"))
				}	 
				state("paused") { //this:State
					action { //it:State
						println("onecellforward is PAUSED")
					}
					 transition(edgeName="t010",targetState="ready",cond=whenDispatch("resume"))
				}	 
				state("doMoveForward") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("onestep(DURATION)"), Term.createTerm("onestep(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								StepTime = payloadArg(0).toLong()
								forward("modelChange", "modelChange(robot,w)" ,"resourcemodel" ) 
								itunibo.planner.plannerUtil.startTimer(  )
						}
						stateTimer = TimerActor("timer_doMoveForward", 
							scope, context!!, "local_tout_onecellforward_doMoveForward", StepTime )
					}
					 transition(edgeName="t011",targetState="endDoMoveForward",cond=whenTimeout("local_tout_onecellforward_doMoveForward"))   
					transition(edgeName="t012",targetState="handleSonarRobot",cond=whenEvent("sonarRobot"))
				}	 
				state("endDoMoveForward") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						forward("modelUpdate", "modelUpdate(robot,w)" ,"kb" ) 
						replyToCaller("stepOk", "stepOk(ok)")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
				state("handleSonarRobot") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.setDuration(myself)
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								foundObstacle = (Integer.parseInt( payloadArg(0) ) <20)
						}
					}
					 transition( edgeName="goto",targetState="stepFail", cond=doswitchGuarded({foundObstacle}) )
					transition( edgeName="goto",targetState="ready", cond=doswitchGuarded({! foundObstacle}) )
				}	 
				state("stepFail") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						solve("wduration(TIME)","") //set resVar	
						Duration=getCurSol("TIME").toString().toLong()
					}
					 transition( edgeName="goto",targetState="goBackFromFail", cond=doswitch() )
				}	 
				state("goBackFromFail") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,s)" ,"resourcemodel" ) 
						val Jal = Duration/4
						delay(Jal)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						delay(PauseTime)
						replyToCaller("stepFail", "stepFail(obstacle,$Duration) ")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
			}
		}
}