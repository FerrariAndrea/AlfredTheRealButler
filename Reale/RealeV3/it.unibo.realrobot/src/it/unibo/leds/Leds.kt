/* Generated by AN DISI Unibo */ 
package it.unibo.leds

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Leds ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var ActualCont : Long =0
				var BlinkDelay : Long = 0
				var BlinkNumber : Long = 0
				val MinBlinkingDelay : Long = 50
				var NeedBlink =false
				val OnTiming :Long = 50 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start leds")
						surpluss.ledManagerSupport.instance(  )
						delay(10) 
						surpluss.ledManagerSupport.frontLedOn(  )
						delay(1000) 
						surpluss.ledManagerSupport.frontLedOff(  )
					}
					 transition( edgeName="goto",targetState="waitingForCMD", cond=doswitch() )
				}	 
				state("waitingForCMD") { //this:State
					action { //it:State
					}
					 transition(edgeName="t025",targetState="handleSetLed",cond=whenEvent("setLed"))
				}	 
				state("handleSetLed") { //this:State
					action { //it:State
						NeedBlink = true
						if( checkMsgContent( Term.createTerm("setLed(BLINKN,BLINKDELAY)"), Term.createTerm("setLed(BLINKN,BLINKDELAY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												ActualCont=0
												BlinkNumber = payloadArg(0).toLong()
												BlinkDelay  = payloadArg(1).toLong()
								if(BlinkDelay<0 || BlinkNumber<0){ surpluss.ledManagerSupport.frontLedOff(  )
								NeedBlink = false
								 }
								if(BlinkDelay>=0 && BlinkDelay<MinBlinkingDelay){ surpluss.ledManagerSupport.frontLedOn(  )
								NeedBlink = false
								 }
								println("$BlinkNumber  $BlinkDelay")
						}
					}
					 transition( edgeName="goto",targetState="blinkOn", cond=doswitchGuarded({NeedBlink}) )
					transition( edgeName="goto",targetState="waitingForCMD", cond=doswitchGuarded({! NeedBlink}) )
				}	 
				state("blinkOn") { //this:State
					action { //it:State
						surpluss.ledManagerSupport.frontLedOn(  )
						ActualCont=ActualCont+1
						stateTimer = TimerActor("timer_blinkOn", 
							scope, context!!, "local_tout_leds_blinkOn", OnTiming )
					}
					 transition(edgeName="t026",targetState="blinkOff",cond=whenTimeout("local_tout_leds_blinkOn"))   
				}	 
				state("blinkOff") { //this:State
					action { //it:State
						surpluss.ledManagerSupport.frontLedOff(  )
						NeedBlink = (ActualCont<BlinkNumber)
					}
					 transition( edgeName="goto",targetState="needBlinkAgain", cond=doswitchGuarded({NeedBlink}) )
					transition( edgeName="goto",targetState="waitingForCMD", cond=doswitchGuarded({! NeedBlink}) )
				}	 
				state("needBlinkAgain") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_needBlinkAgain", 
							scope, context!!, "local_tout_leds_needBlinkAgain", BlinkDelay )
					}
					 transition(edgeName="t027",targetState="blinkOn",cond=whenTimeout("local_tout_leds_needBlinkAgain"))   
				}	 
			}
		}
}
