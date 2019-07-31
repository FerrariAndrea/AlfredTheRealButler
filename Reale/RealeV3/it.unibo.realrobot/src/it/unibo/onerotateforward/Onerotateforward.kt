/* Generated by AN DISI Unibo */ 
package it.unibo.onerotateforward

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Onerotateforward ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val RotateTime = 50L
				val CompleteRotateTime = 300L
				val DelayForCompassReady=50L
				val ErroreConcesso = 2L
				//------------------------
				var RealMove = "a" 		
				var Orientation =0L
				var OrientationZero =0L	
				var Abs =0L
				var NeedRotate =false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start onerotateforward")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
				state("ready") { //this:State
					action { //it:State
					}
					 transition(edgeName="t09",targetState="checkFirst",cond=whenDispatch("onerotationstep"))
				}	 
				state("checkFirst") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("onerotationstep(MOVE)"), Term.createTerm("onerotationstep(ORIENTATION)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								RealMove = payloadArg(0)
						}
						forward("compassReq", "compassReq(0)" ,"compass" ) 
					}
					 transition(edgeName="t010",targetState="doRotationForward",cond=whenDispatch("compassRes"))
				}	 
				state("doRotationForward") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("compassRes(ORIENTATION)"), Term.createTerm("compassRes(ORIENTATION)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								Orientation = payloadArg(0).toLong()
								if(RealMove=="a"){ OrientationZero=Orientation-90
								 }
								if(RealMove=="d"){ OrientationZero=Orientation+90
								 }
								if(OrientationZero<0){ OrientationZero=360+OrientationZero
								 }
								if(OrientationZero>360){ OrientationZero=OrientationZero-360
								 }
						}
						
									 Abs = Math.abs(OrientationZero-Orientation)
									 NeedRotate = Abs>ErroreConcesso
					}
					 transition( edgeName="goto",targetState="bigRotation", cond=doswitchGuarded({NeedRotate}) )
					transition( edgeName="goto",targetState="correggi", cond=doswitchGuarded({! NeedRotate}) )
				}	 
				state("bigRotation") { //this:State
					action { //it:State
						if(RealMove=="a"){ forward("modelChange", "modelChange(robot,a)" ,"resourcemodel" ) 
						delay(CompleteRotateTime)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						println("bigRotation-->a finish")
						 }
						if(RealMove=="d"){ forward("modelChange", "modelChange(robot,d)" ,"resourcemodel" ) 
						delay(CompleteRotateTime)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						println("bigRotation-->d finish")
						 }
					}
					 transition( edgeName="goto",targetState="correggi", cond=doswitch() )
				}	 
				state("correggi") { //this:State
					action { //it:State
						delay(DelayForCompassReady)
						forward("compassReq", "compassReq(0)" ,"compass" ) 
					}
					 transition(edgeName="t011",targetState="handleCompassRes",cond=whenDispatch("compassRes"))
				}	 
				state("handleCompassRes") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("compassRes(ORIENTATION)"), Term.createTerm("compassRes(ORIENTATION)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								Orientation = payloadArg(0).toLong()
						}
						
									 Abs = Math.abs(OrientationZero-Orientation)
									 NeedRotate = Abs>ErroreConcesso
					}
					 transition( edgeName="goto",targetState="miniRotate", cond=doswitchGuarded({NeedRotate}) )
					transition( edgeName="goto",targetState="endDoRotationForward", cond=doswitchGuarded({! NeedRotate}) )
				}	 
				state("miniRotate") { //this:State
					action { //it:State
						
											var temp_d =0L
											var temp_a =0L	
						if(Orientation>OrientationZero){ 
											temp_a =Orientation-OrientationZero
											temp_d =360-temp_a			
						 }
						else
						 { 
						 					temp_d =OrientationZero-Orientation
						 					temp_a =360-temp_d			
						  }
						var Arotate=true
						if(temp_a>temp_d){ Arotate=false
						 }
						println("Arotate[$Arotate], ActualOrientation[$Orientation], needOrientation[$OrientationZero]")
						if(Arotate){ forward("modelChange", "modelChange(robot,ma)" ,"resourcemodel" ) 
						 }
						else
						 { forward("modelChange", "modelChange(robot,md)" ,"resourcemodel" ) 
						  }
						delay(RotateTime)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="correggi", cond=doswitch() )
				}	 
				state("endDoRotationForward") { //this:State
					action { //it:State
						if(RealMove=="a"){ forward("modelUpdate", "modelUpdate(robot,a)" ,"kb" ) 
						 }
						if(RealMove=="d"){ forward("modelUpdate", "modelUpdate(robot,d)" ,"kb" ) 
						 }
						replyToCaller("rotationOk", "rotationOk(0)")
					}
					 transition( edgeName="goto",targetState="ready", cond=doswitch() )
				}	 
			}
		}
}
