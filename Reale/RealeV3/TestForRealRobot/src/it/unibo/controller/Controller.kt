/* Generated by AN DISI Unibo */ 
package it.unibo.controller

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Controller ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			
			var Tback      = 0L
			var StepTime   = 8L	//for real steps (*50ms)
			var RotateTime = 300L	//for real
			var PauseTime  = 250L 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start one step")
						delay(1000) 
					}
					 transition( edgeName="goto",targetState="oneStep", cond=doswitch() )
				}	 
				state("next") { //this:State
					action { //it:State
						println("next")
					}
				}	 
				state("oneStep") { //this:State
					action { //it:State
						forward("onestep", "onestep($StepTime)" ,"onecellforward" ) 
					}
					 transition(edgeName="t00",targetState="handleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t01",targetState="handleStepFail",cond=whenDispatch("stepFail"))
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						println("OK step :)")
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="next", cond=doswitch() )
				}	 
				state("handleStepFail") { //this:State
					action { //it:State
						println("Fail step :(")
					}
					 transition( edgeName="goto",targetState="next", cond=doswitch() )
				}	 
			}
		}
}
