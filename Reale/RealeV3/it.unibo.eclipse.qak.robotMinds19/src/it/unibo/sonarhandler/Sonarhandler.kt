/* Generated by AN DISI Unibo */ 
package it.unibo.sonarhandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonarhandler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var LastSonarRobot : Int = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Start Sonarhandler")
					}
					 transition(edgeName="t03",targetState="handleSonar",cond=whenEvent("sonarRobot"))
				}	 
				state("handleSonar") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChange", "modelChange(sonarRobot,${payloadArg(0)})" ,"resourcemodel" ) 
								LastSonarRobot =  Integer.parseInt( payloadArg(0) )
								println("lastsonarrobot--> $LastSonarRobot")
						}
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChange", "modelChange(sonarLeft,${payloadArg(0)})" ,"resourcemodel" ) 
						}
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChange", "modelChange(sonarRigth,${payloadArg(0)})" ,"resourcemodel" ) 
						}
						if( checkMsgContent( Term.createTerm("internalReq(TARGET)"), Term.createTerm("internalReq(TARGET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("lastSonarRobot", "lastSonarRobot($LastSonarRobot)" ,"onecellforward" ) 
						}
					}
					 transition(edgeName="t04",targetState="handleSonar",cond=whenEvent("sonar"))
					transition(edgeName="t05",targetState="handleSonar",cond=whenEvent("sonarRobot"))
					transition(edgeName="t06",targetState="handleSonar",cond=whenEvent("sonarLeft"))
					transition(edgeName="t07",targetState="handleSonar",cond=whenEvent("sonarRigth"))
					transition(edgeName="t08",targetState="handleSonar",cond=whenEvent("internalReq"))
				}	 
			}
		}
}
