/* Generated by AN DISI Unibo */ 
package it.unibo.pantry

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Pantry ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
					var dishes = 100
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Pantry Started")
						itunibo.coap.modelResourceCoap.create(myself ,"pantry", 5685 )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="takingDish",cond=whenEvent("takeDish"))
				}	 
				state("takingDish") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("takeDish(X)"), Term.createTerm("takeDish(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								var X = payloadArg(0).toInt()
											  dishes = dishes - X
						}
					}
					 transition( edgeName="goto",targetState="updateModelPantry", cond=doswitch() )
				}	 
				state("updateModelPantry") { //this:State
					action { //it:State
						itunibo.robot.resourceModelSupport.updatePantryModel(myself ,"DISH: $dishes; " )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
