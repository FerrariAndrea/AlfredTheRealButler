/* Generated by AN DISI Unibo */ 
package it.unibo.reallateralsonar

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Reallateralsonar ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
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
							scope, context!!, "local_tout_reallateralsonar_s0", ActualTimer )
					}
					 transition(edgeName="t047",targetState="doNotifyAll",cond=whenTimeout("local_tout_reallateralsonar_s0"))   
				}	 
				state("doNotifyAll") { //this:State
					action { //it:State
						val Distance_L = surpluss.pollingSonar.askToSonarLaterali(1).toInt()
						if(Distance_L>0){ emit("sonarLeft", "sonarLeft($Distance_L)" ) 
						 }
						val Distance_R = surpluss.pollingSonar.askToSonarLaterali(0).toInt()
						if(Distance_R>0){ emit("sonarRigth", "sonarRigth($Distance_R)" ) 
						 }
						stateTimer = TimerActor("timer_doNotifyAll", 
							scope, context!!, "local_tout_reallateralsonar_doNotifyAll", ActualTimer )
					}
					 transition(edgeName="t148",targetState="doNotifyAll",cond=whenTimeout("local_tout_reallateralsonar_doNotifyAll"))   
				}	 
			}
		}
}
