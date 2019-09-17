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
		
			var ignoreFail : Boolean=true;
			//--------------------
			var stepCounter = 0 
			var Move = ""
			var StepTime :Int  = 350	//for virtual
			var RotateTime = 300L	//for virtual
			var PauseTime  = 250L 
			var Direction = ""
			//-------------------------------
			var mapEmpty    = false
			val mapname     = "roomBoundary" 
			var Tback       = 0
			var NumStep     = 0
		
			var mustStop : Boolean = false
			var RotateStepCount : Int =0
		
			var needExploreBound : Boolean =false
			var tableFound : Boolean =false
			var directionSud : Boolean =false
			//-------table
			var MapDimX =0;
			var MapDimY=0;
			var ActualTX =0;
			var ActualTY =0;
			var NeedAnotherStep =true;
			var CheckTableSizeStep =0;
			var StartOrientationCheckTable ="sud"
			//go to
			var GoToFailed =false;
			//-------FIX GO HOME
			var IsFixForHome = false;
			var NeedReplyForFixHome =false;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Actor: Explorer; State: initial")
						solve("consult('moves.pl')","") //set resVar	
						itunibo.coap.observer.resourceObserverCoapClient.create( "coap://localhost:5683/resourcemodel"  )
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.moveUtils.showCurrentRobotState(  )
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						println("Waiting for exploration cmd...")
						needExploreBound=false;IsFixForHome=false;NeedReplyForFixHome=false;
					}
					 transition(edgeName="t011",targetState="handeCmd",cond=whenDispatch("doExplor"))
					transition(edgeName="t012",targetState="goToPosition",cond=whenEvent("goTo"))
				}	 
				state("handeCmd") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("doExplor(TARGET)"), Term.createTerm("doExplor(TARGET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												needExploreBound = (payloadArg(0)=="bound")				
						}
					}
					 transition( edgeName="goto",targetState="exploreBounds", cond=doswitchGuarded({needExploreBound}) )
					transition( edgeName="goto",targetState="exploreTale", cond=doswitchGuarded({! needExploreBound}) )
				}	 
				state("exploreBounds") { //this:State
					action { //it:State
						println("Start explore bounds.")
						NumStep=0;RotateStepCount=0
					}
					 transition( edgeName="goto",targetState="detectPerimeter", cond=doswitch() )
				}	 
				state("rotateEast") { //this:State
					action { //it:State
						NumStep=0
						Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
					}
					 transition(edgeName="t013",targetState="detectPerimeter",cond=whenDispatch("rotationOk"))
				}	 
				state("detectPerimeter") { //this:State
					action { //it:State
						NumStep++;RotateStepCount=0
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition( edgeName="goto",targetState="goOneStepAhead", cond=doswitchGuarded({(NumStep<5)}) )
					transition( edgeName="goto",targetState="perimeterWalked", cond=doswitchGuarded({! (NumStep<5)}) )
				}	 
				state("goOneStepAhead") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.attemptTomoveAhead(myself ,StepTime, "onecellforward" )
					}
					 transition(edgeName="t014",targetState="handleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t015",targetState="checkingObject",cond=whenEvent("collision"))
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						NeedAnotherStep =true;
						if(needExploreBound){ itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
						 }
						else
						 { itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
						 	
						 				ActualTY++
						 				NeedAnotherStep= (ActualTY<MapDimY-1 && CheckTableSizeStep==0)
						 if(CheckTableSizeStep>0){ if((CheckTableSizeStep==3)){ CheckTableSizeStep=4
						  }
						 else
						  { CheckTableSizeStep=2
						   }
						  }
						  }
						delay(250) 
					}
					 transition( edgeName="goto",targetState="goOneStepAhead", cond=doswitchGuarded({NeedAnotherStep}) )
					transition( edgeName="goto",targetState="handleStepFailTable", cond=doswitchGuarded({! NeedAnotherStep}) )
				}	 
				state("checkingObject") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("collision(OBJECT)"), Term.createTerm("collision(OBJECT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val ObjName = payloadArg(0)
								println("OGGETTO IN COLLISIONE: $ObjName")
								if((ObjName.equals("pantry") || ObjName.equals("dishwasher") || ObjName.equals("fridge")|| ObjName.equals("table"))){ 
												val XTemp = itunibo.planner.plannerUtil.getPosX()
												val YTemp = itunibo.planner.plannerUtil.getPosY()	
												tableFound	=ObjName.equals("table")		
								forward("modelUpdateMap", "modelUpdateMap($ObjName,$XTemp,$YTemp)" ,"kb" ) 
								 }
						}
					}
					 transition( edgeName="goto",targetState="consumeStepFailBound", cond=doswitchGuarded({needExploreBound}) )
					transition( edgeName="goto",targetState="consumeStepFailTable", cond=doswitchGuarded({! needExploreBound}) )
				}	 
				state("consumeStepFailBound") { //this:State
					action { //it:State
					}
					 transition(edgeName="t016",targetState="handleStepFail",cond=whenDispatch("stepFail"))
				}	 
				state("consumeStepFailTable") { //this:State
					action { //it:State
						println("Part of table found, map:")
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition(edgeName="t017",targetState="handleStepFailTable",cond=whenDispatch("stepFail"))
				}	 
				state("handleStepFail") { //this:State
					action { //it:State
						delay(500) 
						if(RotateStepCount==0){ itunibo.planner.plannerUtil.wallFound(  )
						Move="d"
						forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						if(RotateStepCount==1){ val MapStr =  itunibo.planner.plannerUtil.getMapOneLine()
						forward("modelUpdate", "modelUpdate(roomMap,$MapStr)" ,"resourcemodel" ) 
						Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						if(RotateStepCount==2){ Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						RotateStepCount++
					}
					 transition(edgeName="t018",targetState="checkRotateStep1",cond=whenDispatch("rotationOk"))
				}	 
				state("checkRotateStep1") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="goOneStepAhead", cond=doswitchGuarded({(RotateStepCount==1)}) )
					transition( edgeName="goto",targetState="checkRotateStep2", cond=doswitchGuarded({! (RotateStepCount==1)}) )
				}	 
				state("checkRotateStep2") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="handleStepFail", cond=doswitchGuarded({(RotateStepCount==2)}) )
					transition( edgeName="goto",targetState="detectPerimeter", cond=doswitchGuarded({! (RotateStepCount==2)}) )
				}	 
				state("perimeterWalked") { //this:State
					action { //it:State
						println("FINAL MAP")
						itunibo.planner.moveUtils.showCurrentRobotState(  )
						itunibo.planner.plannerUtil.saveMap( mapname  )
						
											MapDimX = itunibo.planner.plannerUtil.getMapDimX()-1
											MapDimY = itunibo.planner.plannerUtil.getMapDimY()-1
						println("Perimeter completely walked. DimX: $MapDimX DimY: $MapDimY")
						replyToCaller("endExplor", "endExplor(ok)")
						forward("modelUpdate", "modelUpdate(metre,endExplorBoundOk)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("exploreTale") { //this:State
					action { //it:State
						println("Start explore table.")
						tableFound=false;ActualTX =0;ActualTY =0;CheckTableSizeStep=0;
					}
					 transition( edgeName="goto",targetState="goOneStepAhead", cond=doswitch() )
				}	 
				state("handleStepFailTable") { //this:State
					action { //it:State
						if(CheckTableSizeStep<2){ val MapStr =  itunibo.planner.plannerUtil.getMapOneLine()
						forward("modelUpdate", "modelUpdate(roomMap,$MapStr)" ,"resourcemodel" ) 
						if(tableFound){ itunibo.planner.plannerUtil.autoSetTablePos(  )
						CheckTableSizeStep=1
						 }
						 }
					}
					 transition( edgeName="goto",targetState="askOrientation", cond=doswitchGuarded({(CheckTableSizeStep<2)}) )
					transition( edgeName="goto",targetState="swithcerCheckTableDim1", cond=doswitchGuarded({! (CheckTableSizeStep<2)}) )
				}	 
				state("swithcerCheckTableDim1") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="rotateForTableDimY", cond=doswitchGuarded({(CheckTableSizeStep<3)}) )
					transition( edgeName="goto",targetState="swithcerCheckTableDim2", cond=doswitchGuarded({! (CheckTableSizeStep<3)}) )
				}	 
				state("swithcerCheckTableDim2") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="checkTableDimYRotateBefore", cond=doswitchGuarded({(CheckTableSizeStep<4)}) )
					transition( edgeName="goto",targetState="endOfJobTable", cond=doswitchGuarded({! (CheckTableSizeStep<4)}) )
				}	 
				state("askOrientation") { //this:State
					action { //it:State
						forward("modelRequest", "modelRequest(robot,location)" ,"kb" ) 
					}
					 transition(edgeName="t019",targetState="rotateBefore",cond=whenDispatch("modelRobotResponse"))
				}	 
				state("rotateForTableDimY") { //this:State
					action { //it:State
						if(StartOrientationCheckTable=="sud"){ Move="d"
						forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						if(StartOrientationCheckTable=="nord"){ Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						CheckTableSizeStep=3
					}
					 transition(edgeName="t020",targetState="goOneStepAhead",cond=whenDispatch("rotationOk"))
				}	 
				state("checkTableDimYRotateBefore") { //this:State
					action { //it:State
						if(StartOrientationCheckTable=="sud"){ Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						if(StartOrientationCheckTable=="nord"){ Move="d"
						forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
					}
					 transition(edgeName="t021",targetState="checkTableDimYmoveOne",cond=whenDispatch("rotationOk"))
				}	 
				state("checkTableDimYmoveOne") { //this:State
					action { //it:State
						delay(250) 
						itunibo.planner.moveUtils.attemptTomoveAhead(myself ,StepTime, "onecellforward" )
					}
					 transition(edgeName="t022",targetState="checkTableDimYRotateAfter",cond=whenDispatch("stepOk"))
					transition(edgeName="t023",targetState="checkingObject",cond=whenEvent("collision"))
				}	 
				state("checkTableDimYRotateAfter") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
						println("stand by the table, map:")
						itunibo.planner.plannerUtil.showMap(  )
						if(StartOrientationCheckTable=="sud"){ Move="d"
						forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
						if(StartOrientationCheckTable=="nord"){ Move="a"
						forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						 }
					}
					 transition(edgeName="t024",targetState="goOneStepAhead",cond=whenDispatch("rotationOk"))
				}	 
				state("rotateBefore") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("modelRobotResponse(X,Y,O)"), Term.createTerm("modelRobotResponse(X,Y,O)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								StartOrientationCheckTable = payloadArg(2)
								if(StartOrientationCheckTable=="sud"){ Move="a"
								forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
								itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
								 }
								if(StartOrientationCheckTable=="nord"){ Move="d"
								forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
								itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
								 }
						}
					}
					 transition(edgeName="t025",targetState="moveOne",cond=whenDispatch("rotationOk"))
				}	 
				state("moveOne") { //this:State
					action { //it:State
						delay(250) 
						itunibo.planner.moveUtils.attemptTomoveAhead(myself ,StepTime, "onecellforward" )
					}
					 transition(edgeName="t026",targetState="rotateAfter",cond=whenDispatch("stepOk"))
					transition(edgeName="t027",targetState="checkingObject",cond=whenEvent("collision"))
				}	 
				state("rotateAfter") { //this:State
					action { //it:State
						if(tableFound){ itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
						delay(250) 
						if(Move=="a"){ Move="d"
						forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						 }
						else
						 { Move="a"
						 forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						  }
						 }
						else
						 { itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
						 delay(250) 
						 if(Move=="a"){ forward("onerotationstep", "onerotationstep(a)" ,"onerotateforward" ) 
						  }
						 else
						  { forward("onerotationstep", "onerotationstep(d)" ,"onerotateforward" ) 
						   }
						  }
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						ActualTY=0;ActualTX++
					}
					 transition(edgeName="t028",targetState="goOneStepAhead",cond=whenDispatch("rotationOk"))
				}	 
				state("endOfJobTable") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.showCurrentRobotState(  )
						println("Map after explore table")
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.saveMap( mapname  )
						println("Table exploration end, starting fix-go-home")
					}
					 transition( edgeName="goto",targetState="startFixGoHome", cond=doswitch() )
				}	 
				state("startFixGoHome") { //this:State
					action { //it:State
						IsFixForHome=true;NeedReplyForFixHome=true;
						itunibo.planner.plannerUtil.setGoal( 0, 0  )
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="checkRotation", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
				}	 
				state("checkNord") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						Direction = itunibo.planner.plannerUtil.getDirection()
						println("Actor: Explorer; State: rotateSouth; Payload: $Direction")
					}
					 transition( edgeName="goto",targetState="rotateSouth", cond=doswitchGuarded({(Direction!="upDir")}) )
					transition( edgeName="goto",targetState="forceGoUp", cond=doswitchGuarded({! (Direction!="upDir")}) )
				}	 
				state("rotateNord") { //this:State
					action { //it:State
						Move="a"
						delay(PauseTime)
						forward("onerotationstep", "onerotationstep($Move)" ,"onerotateforward" ) 
					}
					 transition(edgeName="t029",targetState="checkRotation",cond=whenDispatch("rotationOk"))
				}	 
				state("forceGoUp") { //this:State
					action { //it:State
						forward("onestep", "onestep($StepTime)" ,"onecellforward" ) 
					}
					 transition(edgeName="t130",targetState="endFixGoHome",cond=whenDispatch("stepOk"))
					transition(edgeName="t131",targetState="endFixGoHome",cond=whenDispatch("stepFail"))
				}	 
				state("endFixGoHome") { //this:State
					action { //it:State
						IsFixForHome=false
					}
					 transition( edgeName="goto",targetState="checkRotation", cond=doswitch() )
				}	 
				state("goToPosition") { //this:State
					action { //it:State
						GoToFailed=false
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("goTo(X,Y)"), Term.createTerm("goTo(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var X = payloadArg(0).toInt()
												var Y = payloadArg(1).toInt()
												//var O = payloadArg(2)
								solve("direction(D)","") //set resVar	
								println("Actor: Explorer; State: goToPosition; Payload: direction at start: ${getCurSol("D").toString()}")
								itunibo.planner.plannerUtil.showMap(  )
								itunibo.planner.plannerUtil.setGoal( X, Y  )
								itunibo.planner.moveUtils.doPlan(myself)
						}
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="checkRotation", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
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
					transition( edgeName="goto",targetState="checkRotation", cond=doswitchGuarded({! (Move.length>0) }) )
				}	 
				state("doTheMove") { //this:State
					action { //it:State
						if(Move=="a" || Move=="d" ){ forward("onerotationstep", "onerotationstep($Move)" ,"onerotateforward" ) 
						 }
						else
						 { forward("onestep", "onestep($StepTime)" ,"onecellforward" ) 
						  }
					}
					 transition(edgeName="t032",targetState="handleStepOkGoTo",cond=whenDispatch("stepOk"))
					transition(edgeName="t033",targetState="handleStepFailGoTo",cond=whenDispatch("stepFail"))
					transition(edgeName="t034",targetState="handleStepOkGoTo",cond=whenDispatch("rotationOk"))
				}	 
				state("handleStepOkGoTo") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("handleStepFailGoTo") { //this:State
					action { //it:State
						println("GOTO->StepFail")
						if(ignoreFail){ itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						forward("modelUpdate", "modelUpdate(robot,w)" ,"kb" ) 
						 }
						delay(PauseTime)
						GoToFailed=true
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({ignoreFail}) )
					transition( edgeName="goto",targetState="rotateSouth", cond=doswitchGuarded({! ignoreFail}) )
				}	 
				state("rotateSouth") { //this:State
					action { //it:State
						Move="a"
						delay(PauseTime)
						forward("onerotationstep", "onerotationstep($Move)" ,"onerotateforward" ) 
					}
					 transition(edgeName="t035",targetState="checkRotation",cond=whenDispatch("rotationOk"))
				}	 
				state("checkSouth") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,Move )
						Direction = itunibo.planner.plannerUtil.getDirection()
						println("Actor: Explorer; State: rotateSouth; Payload: $Direction")
					}
					 transition( edgeName="goto",targetState="rotateSouth", cond=doswitchGuarded({(Direction!="downDir")}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! (Direction!="downDir")}) )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						if(NeedReplyForFixHome){ replyToCaller("endExplor", "endExplor(ok)")
						forward("modelUpdate", "modelUpdate(metre,endExplorTableOk)" ,"resourcemodel" ) 
						println("FixGoHome end.")
						 }
						else
						 { if(GoToFailed){ println("Actor: Explorer; State: handleStepFail; Payload: Fail step :(")
						 itunibo.planner.plannerUtil.showMap(  )
						 println("Actor: Explorer; State: handleStepFail; Payload: Replan and return at home.")
						 replyToCaller("goToFail", "goToFail(fail)")
						  }
						 else
						  { println("Explorer: on the target cell!")
						  replyToCaller("goToOk", "goToOk(ok)")
						   }
						  }
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("checkRotation") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="checkNord", cond=doswitchGuarded({IsFixForHome}) )
					transition( edgeName="goto",targetState="checkSouth", cond=doswitchGuarded({! IsFixForHome}) )
				}	 
			}
		}
}
