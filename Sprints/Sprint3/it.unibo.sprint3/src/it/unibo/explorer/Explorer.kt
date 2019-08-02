/* Generated by AN DISI Unibo */ 
package it.unibo.explorer

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Explorer ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			var fridge_position_x = 6;
			var fridge_position_y = 0;
			var goingHome  = false
			var stepCounter = 0 
			var Move = ""
			var StepTime   = 350L	//for virtual
			var RotateTime = 300L	//for virtual
			var PauseTime  = 250L 
			var Direction = ""
			var RepeatAction = 1
			var addingFood = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Actor: Explorer; State: initial")
						solve("consult('moves.pl')","") //set resVar	
						itunibo.planner.plannerUtil.initAI(  )
						println("Actor: Explorer; State: handleStepFail; Payload: INITIAL MAP")
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition( edgeName="goto",targetState="readyForAction", cond=doswitch() )
				}	 
				state("readyForAction") { //this:State
					action { //it:State
						println("Waiting for mission... Please send an action!")
					}
					 transition(edgeName="t00",targetState="goToPosition",cond=whenEvent("goTo"))
					transition(edgeName="t01",targetState="addingFood",cond=whenEvent("addFood"))
				}	 
				state("addingFood") { //this:State
					action { //it:State
						println("ADDING FOOD: Go to Fridge")
						addingFood = true
					}
					 transition( edgeName="goto",targetState="goToFridge", cond=doswitch() )
				}	 
				state("goToFridge") { //this:State
					action { //it:State
						solve("direction(D)","") //set resVar	
						println("Actor: Explorer; State: goToFridge; Payload: direction at start: ${getCurSol("D").toString()}")
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.setGoal( 5, 0  )
						goingHome=false
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
				}	 
				state("stopped") { //this:State
					action { //it:State
					}
				}	 
				state("goToPosition") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("goTo(X,Y)"), Term.createTerm("goTo(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var X = payloadArg(0).toInt()
												var Y = payloadArg(1).toInt()
												//var O = payloadArg(2)
								solve("direction(D)","") //set resVar	
								println("Actor: Explorer; State: goToPosition; Payload: direction at start: ${getCurSol("D").toString()}")
								itunibo.planner.plannerUtil.showMap(  )
								itunibo.planner.plannerUtil.setGoal( X, Y  )
								goingHome=false
								itunibo.planner.moveUtils.doPlan(myself)
						}
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
				}	 
				state("executePlannedActions") { //this:State
					action { //it:State
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) { Move = getCurSol("M").toString()
						 }
						else
						{ Move = ""
						 }
					}
					 transition( edgeName="goto",targetState="doTheMove", cond=doswitchGuarded({(Move.length>0) }) )
					transition( edgeName="goto",targetState="goalOk", cond=doswitchGuarded({! (Move.length>0) }) )
				}	 
				state("goalOk") { //this:State
					action { //it:State
						println("Explorer: on the target cell!")
					}
					 transition( edgeName="goto",targetState="goToTable", cond=doswitchGuarded({addingFood}) )
					transition( edgeName="goto",targetState="checkIfGoingHome", cond=doswitchGuarded({! addingFood}) )
				}	 
				state("checkIfGoingHome") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="atHome", cond=doswitchGuarded({goingHome}) )
					transition( edgeName="goto",targetState="goHome", cond=doswitchGuarded({! goingHome}) )
				}	 
				state("goToTable") { //this:State
					action { //it:State
						solve("direction(D)","") //set resVar	
						println("Actor: Explorer; State: goToTable; Payload: direction at start: ${getCurSol("D").toString()}")
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.setGoal( 5, 4  )
						addingFood=false
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
				}	 
				state("doTheMove") { //this:State
					action { //it:State
						if(Move=="a" || Move=="d" ){ forward("onerotationstep", "onerotationstep($Move)" ,"onerotateforward" ) 
						 }
						else
						 { forward("onestep", "onestep($StepTime)" ,"onecellforward" ) 
						  }
					}
					 transition(edgeName="t02",targetState="handleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t03",targetState="handleStepFail",cond=whenDispatch("stepFail"))
					transition(edgeName="t04",targetState="handleStepOk",cond=whenDispatch("rotationOk"))
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("handleStepFail") { //this:State
					action { //it:State
						println("Actor: Explorer; State: handleStepFail; Payload: Fail step :(")
						itunibo.planner.plannerUtil.showMap(  )
						println("Actor: Explorer; State: handleStepFail; Payload: Replan and return at home.")
						delay(500) 
					}
					 transition( edgeName="goto",targetState="goHome", cond=doswitch() )
				}	 
				state("goHome") { //this:State
					action { //it:State
						println("Actor: Explorer; State: backToHome")
						solve("direction(D)","") //set resVar	
						println("Actor: Explorer; State: backToHome; Payload: ${getCurSol("D").toString()}")
						println("Actor: Explorer; State: backToHome; Payload: MAP BEFORE backToHome")
						itunibo.planner.plannerUtil.showMap(  )
						solve("retractall(move(_))","") //set resVar	
						itunibo.planner.plannerUtil.setGoal( 0, 0  )
						goingHome=true
						itunibo.planner.moveUtils.doPlan(myself)
						itunibo.planner.moveUtils.existPlan(  )
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("atHome") { //this:State
					action { //it:State
						solve("direction(D)","") //set resVar	
						Direction = getCurSol("D").toString() 
						println(getCurSol("D").toString())
						
								val map = itunibo.planner.plannerUtil.getMap() 
								println(map)
						println("Actor: Explorer; State: atHome; Payload: direction at home: ${getCurSol("D").toString()}")
					}
					 transition( edgeName="goto",targetState="rotateSouth", cond=doswitch() )
				}	 
				state("rotateSouth") { //this:State
					action { //it:State
						Move="a"
						delay(StepTime)
						forward("onerotationstep", "onerotationstep($Move)" ,"onerotateforward" ) 
					}
					 transition(edgeName="t05",targetState="checkSouth",cond=whenDispatch("rotationOk"))
				}	 
				state("checkSouth") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						solve("direction(D)","") //set resVar	
						Direction = getCurSol("D").toString() 
						println("Actor: Explorer; State: rotateSouth; Payload: $Direction")
					}
					 transition( edgeName="goto",targetState="rotateSouth", cond=doswitchGuarded({(Direction!="downDir")}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! (Direction!="downDir")}) )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						println("Explorer: EndOfJob, go to ReadyForAction")
					}
					 transition( edgeName="goto",targetState="readyForAction", cond=doswitchGuarded({(RepeatAction > 0)}) )
					transition( edgeName="goto",targetState="end", cond=doswitchGuarded({! (RepeatAction > 0)}) )
				}	 
				state("end") { //this:State
					action { //it:State
						println("Explorer: END")
					}
				}	 
			}
		}
}
