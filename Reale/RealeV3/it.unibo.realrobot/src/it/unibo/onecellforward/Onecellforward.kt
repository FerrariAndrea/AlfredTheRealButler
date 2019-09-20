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
		
				val WorkTime : Long =40 //ms
				val SleepTime : Long =5 //ms
				val DistanzaCella : Long =25 //cm
		
				var FoundObstacle = false
				var StepTime = 0L
				var Duration : Long =0
				var DistanzaMinima :Long =15
				
				var ActualStep : Int =0
				var ActualL : Int =0
				var ActualR : Int =0
				var L : Int =-1
				var R : Int =-1
				var DeviazioneL: Int=0
				var DeviazioneR: Int=0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start onecellforward")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
				state("ready") { //this:State
					action { //it:State
						println("onecellforward is READY")
					}
					 transition(edgeName="t036",targetState="checkFirst",cond=whenDispatch("onestep"))
					transition(edgeName="t037",targetState="paused",cond=whenDispatch("stop"))
				}	 
				state("paused") { //this:State
					action { //it:State
						println("onecellforward is PAUSED")
					}
					 transition(edgeName="t038",targetState="ready",cond=whenDispatch("reactivate"))
				}	 
				state("checkFirst") { //this:State
					action { //it:State
						
									storeCurrentMessageForReply()
									FoundObstacle = false 
						if( checkMsgContent( Term.createTerm("onestep(DURATION)"), Term.createTerm("onestep(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								StepTime = payloadArg(0).toLong()
						}
						forward("internalSonarReq", "internalSonarReq(V)" ,"sonarcollector" ) 
					}
					 transition(edgeName="t039",targetState="waitingForcheckFirstSonar",cond=whenDispatch("internalSonarRes"))
				}	 
				state("waitingForcheckFirstSonar") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("internalSonarRes(SonarW,SonarL,SonarR)"), Term.createTerm("internalSonarRes(W,L,R)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val W = Integer.parseInt( payloadArg(0) ) 
												ActualL = Integer.parseInt( payloadArg(1) ) 
												ActualR = Integer.parseInt( payloadArg(2) ) 
												FoundObstacle=W<DistanzaMinima
												DeviazioneL=0
												DeviazioneR=0
								if(FoundObstacle){ replyToCaller("collision", " collision(RealWall) ")
								replyToCaller("stepFail", "stepFail(obstacle,$W) ")
								println("Actor: OneStepForward; State:cantDoOneStep")
								 }
								else
								 { println("Actor: OneStepForward; State: OK-> $W")
								  }
						}
						ActualStep=0
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitchGuarded({FoundObstacle}) )
					transition( edgeName="goto",targetState="doMoveForward", cond=doswitchGuarded({! FoundObstacle}) )
				}	 
				state("doMoveForward") { //this:State
					action { //it:State
						println("doMoveForward")
						delay(50) 
						ActualStep=ActualStep+1
						forward("internalRobotReq", "internalRobotReq(ws,2,$WorkTime,$SleepTime)" ,"basicrobot" ) 
					}
					 transition(edgeName="t040",targetState="checkFinish",cond=whenEvent("internalRobotRes"))
				}	 
				state("checkFinish") { //this:State
					action { //it:State
						println("checkFinish")
						forward("modelUpdate", "modelUpdate(robot,w)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="mustGoOn", cond=doswitchGuarded({(ActualStep<StepTime)}) )
					transition( edgeName="goto",targetState="endDoMoveForward", cond=doswitchGuarded({! (ActualStep<StepTime)}) )
				}	 
				state("mustGoOn") { //this:State
					action { //it:State
						println("mustGoOn")
						forward("internalSonarReq", "internalSonarReq(V)" ,"sonarcollector" ) 
					}
					 transition(edgeName="t041",targetState="checkMove",cond=whenDispatch("internalSonarRes"))
				}	 
				state("checkMove") { //this:State
					action { //it:State
						println("checkMove")
						if( checkMsgContent( Term.createTerm("internalSonarRes(SonarW,SonarL,SonarR)"), Term.createTerm("internalSonarRes(W,L,R)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val W = Integer.parseInt( payloadArg(0) ) 
												ActualL = Integer.parseInt( payloadArg(1) ) 
												ActualR = Integer.parseInt( payloadArg(2) ) 
												FoundObstacle=W<DistanzaMinima
												DeviazioneL=0
												DeviazioneR=0
								println("checkMove $W $ActualR $ActualL")
								if(ActualL<ActualR){ if(L<0){ L=ActualL
								 }
								else
								 { DeviazioneL=L-ActualL
								  }
								 }
								else
								 { if(R<0){ R=ActualR
								  }
								 else
								  { DeviazioneR=R-ActualR
								   }
								  }
						}
					}
					 transition( edgeName="goto",targetState="fixMove", cond=doswitchGuarded({(DeviazioneL!=0 || DeviazioneR!=0)}) )
					transition( edgeName="goto",targetState="checkFail", cond=doswitchGuarded({! (DeviazioneL!=0 || DeviazioneR!=0)}) )
				}	 
				state("fixMove") { //this:State
					action { //it:State
						println("fixMove")
						if(DeviazioneL>0 || DeviazioneR<0){ ActualStep=ActualStep+1
						forward("internalRobotReq", "internalRobotReq(am,2,$WorkTime,$SleepTime)" ,"basicrobot" ) 
						 }
						else
						 { ActualStep=ActualStep+1
						 forward("internalRobotReq", "internalRobotReq(dm,2,$WorkTime,$SleepTime)" ,"basicrobot" ) 
						  }
					}
					 transition( edgeName="goto",targetState="checkFail", cond=doswitch() )
				}	 
				state("checkFail") { //this:State
					action { //it:State
						println("checkFail")
					}
					 transition( edgeName="goto",targetState="stepFail", cond=doswitchGuarded({FoundObstacle}) )
					transition( edgeName="goto",targetState="doMoveForward", cond=doswitchGuarded({! FoundObstacle}) )
				}	 
				state("endDoMoveForward") { //this:State
					action { //it:State
						println("endDoMoveForward")
						forward("modelUpdate", "modelUpdate(robot,w)" ,"kb" ) 
						replyToCaller("stepOk", "stepOk(ok)")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
				state("stepFail") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="goBackFromFail", cond=doswitch() )
				}	 
				state("goBackFromFail") { //this:State
					action { //it:State
						println("goBackFromFail")
						delay(50) 
						if(ActualStep>0){ forward("internalRobotReq", "internalRobotReq(ss,$ActualStep,$WorkTime,$SleepTime)" ,"basicrobot" ) 
						 }
						else
						 { forward("internalRobotReq", "internalRobotReq(ss,1,10,100)" ,"basicrobot" ) 
						  }
					}
					 transition(edgeName="t042",targetState="endGoBackFormFail",cond=whenEvent("internalRobotRes"))
				}	 
				state("endGoBackFormFail") { //this:State
					action { //it:State
						println("endGoBackFormFail")
						forward("modelUpdate", "modelUpdate(robot,s)" ,"resourcemodel" ) 
						delay(50) 
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						replyToCaller("collision", " collision(RealWall) ")
						replyToCaller("stepFail", "stepFail(obstacle,$Duration) ")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
			}
		}
}
