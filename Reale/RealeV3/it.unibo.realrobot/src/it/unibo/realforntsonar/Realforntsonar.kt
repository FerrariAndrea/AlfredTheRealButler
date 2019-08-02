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
			
				var ActualTimer : Long = 250
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						surpluss.pollingSonar.instance(  )
						println("Start realSonar")
						stateTimer = TimerActor("timer_s0", 
							scope, context!!, "local_tout_realforntsonar_s0", ActualTimer )
					}
					 transition(edgeName="t041",targetState="doNotifyAll",cond=whenTimeout("local_tout_realforntsonar_s0"))   
				}	 
				state("doNotifyAll") { //this:State
					action { //it:State
						val Distance = surpluss.pollingSonar.askToSonar().toInt()
						if(Distance>0){ emit("sonarRobot", "sonar($Distance)" ) 
						 }
						stateTimer = TimerActor("timer_doNotifyAll", 
							scope, context!!, "local_tout_realforntsonar_doNotifyAll", ActualTimer )
					}
					 transition(edgeName="t142",targetState="doNotifyAll",cond=whenTimeout("local_tout_realforntsonar_doNotifyAll"))   
				}	 
			}
		}
}
