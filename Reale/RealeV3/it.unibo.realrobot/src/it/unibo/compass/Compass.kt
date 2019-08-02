/* Generated by AN DISI Unibo */ 
package it.unibo.compass

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Compass ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						surpluss.compassSupport.instance(  )
						println("Start compass")
					}
					 transition( edgeName="goto",targetState="calibration", cond=doswitch() )
				}	 
				state("calibration") { //this:State
					action { //it:State
					}
					 transition(edgeName="t042",targetState="handleCompass",cond=whenEvent("compassReq"))
				}	 
				state("handleCompass") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("compassReq(V)"), Term.createTerm("compassReq(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(payloadArg(0)=="fix"){ val Ris = surpluss.compassSupport.fixCompass()
								replyToCaller("compassRes", "compassRes($Ris)")
								 }
								else
								 { val Orientation = surpluss.compassSupport.askToCompass().toLong()
								 replyToCaller("compassRes", "compassRes($Orientation)")
								  }
						}
					}
					 transition(edgeName="t143",targetState="handleCompass",cond=whenEvent("compassReq"))
				}	 
			}
		}
}
