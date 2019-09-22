package it.unibo.sprint2

import org.junit.Before
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import it.unibo.kactor.MsgUtil
import it.unibo.blsFramework.kotlin.fsm.forward
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.junit.BeforeClass
import it.unibo.kactor.MqttUtils
import it.unibo.kactor.ActorBasicFsm
import alice.tuprolog.Term
import srcTest.Tester

class Sprint2Test2 {


	companion object {
		//----------------------var
		var actor : Tester?= null
		var testName = "Sprint2Test2"
		var sleepTime = 1000L
		var testCount =0
		init {}

//-------------------------------------------------------UTILS for test
	fun delay( time : Long ){
		Thread.sleep( time )
	}

	//funzione che manda messaggi ad attori (prbabilmente solo nel cotesto del attore "actor")
	fun send( actor : ActorBasic,msg :String ,msg_and_payload : String){

		GlobalScope.launch{
			println("--- send $msg -->$msg_and_payload")
			//actor.autoMsg("$dest","$msg($payload)")
				MsgUtil.sendMsg("$msg","$msg_and_payload",actor)
		}
	}


	//funzione che attende una risposta ad un messaggio
	fun request( actor : ActorBasic,msg :String ,msg_and_payload : String,requestType : String ):MqttMessage{
		var message: MqttMessage=MqttMessage()
				actor.scope.launch{

		actor.messageArrived(requestType,message)
		println("--- send $msg -->$msg_and_payload")
		//actor.autoMsg("$dest","$msg($payload)")
					
		MsgUtil.sendMsg("$msg","$msg_and_payload",actor)
	}
		
	return message
	}
	fun checkActorExist(name: String){
		var actor : ActorBasic?=null
		GlobalScope.launch{
			actor=sysUtil.getActor(name)
		}
		delay(500)
		assertTrue("Check Actor $name", null != actor )
	}
	//-----------------------------------------------------END utils for test
		
		//start all
		@BeforeClass @JvmStatic
		fun systemSetUp() {
	
			GlobalScope.launch{ //activate an observer ...
				itunibo.coap.observer.main()	//blocking
			}
			delay(sleepTime)
		
			println(" %%%%%%% $testName start ctxTest")	 			
			GlobalScope.launch{
				srcTest.main()				
			}
			delay(sleepTime)
			GlobalScope.launch{
				actor=sysUtil.getActor("tester") as Tester
			}
		}

	}

	@After
	fun terminate() {
		println(" %%%%%%% $testName terminate N° $testCount")
		testCount++
	}


	

	
	@Test
	fun goToFridge(){
		assertTrue("Tester actor on",null!=actor)		
		GlobalScope.launch{		
			actor!!.forward("testCmd","testCmd(Next)","explorer")
		}
		delay(6500)//do al robot il tempo di raggiungere il frigo-->posizione (6,0)
		var result: String?=null
		GlobalScope.launch{		
			actor!!.forward("modelRequest", "modelRequest(robot,location)" ,"kb" ) 
			delay(150)
			//actor!!.checkMsgContent(checkMsgContent( Term.createTerm("modelRobotResponse(X,Y,O)"), Term.createTerm("modelRobotResponse(X,Y,O)"),  currentMsg.msgContent()) )
			result=actor!!.getRobotStatus()
		}
		delay(300)
		println("--->$result")
		assertTrue("Robot on (5,0,ovest)","5-0-ovest"==result ||"6-0-ovest"==result)
		GlobalScope.launch{		
			actor!!.forward("testCmd","testCmd(Next)","explorer")
		}
		delay(6500)
		result=null
		GlobalScope.launch{		
			actor!!.forward("modelRequest", "modelRequest(robot,location)" ,"kb" ) 
			delay(150)
			//actor!!.checkMsgContent(checkMsgContent( Term.createTerm("modelRobotResponse(X,Y,O)"), Term.createTerm("modelRobotResponse(X,Y,O)"),  currentMsg.msgContent()) )
			result=actor!!.getRobotStatus()
		}
		delay(300)
		println("--->$result")
		assertTrue("Robot on (0,0,est)","0-0-est"==result)
		GlobalScope.launch{		
			actor!!.forward("testCmd","testCmd(Next)","explorer")
		}
		delay(6500)
		result=null
		GlobalScope.launch{		
			actor!!.forward("modelRequest", "modelRequest(robot,location)" ,"kb" ) 
			delay(50)
			//actor!!.checkMsgContent(checkMsgContent( Term.createTerm("modelRobotResponse(X,Y,O)"), Term.createTerm("modelRobotResponse(X,Y,O)"),  currentMsg.msgContent()) )
			result=actor!!.getRobotStatus()
		}
		delay(300)
		println("--->$result")
		assertTrue("Robot on (0,0,sud)","0-0-sud"==result)
		
		println("test finish !!!")
	
	}



}