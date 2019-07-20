package it.unibo.sprint1a

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


class Sprint2Test2 {


	companion object {
		//----------------------var
		var actor : ActorBasic?= null
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
		
			println(" %%%%%%% $testName starts ctxMaitre")	 			
			GlobalScope.launch{
				it.unibo.ctxMaitre.main()							
			}
		}

	}

	@After
	fun terminate() {
		println(" %%%%%%% $testName terminate N° $testCount")
		testCount++
	}

	
	@Test
	fun onecellforwardTest(){
		checkActorExist("onecellforward")
	}
	@Test
	fun explorerTest(){
		checkActorExist("explorer")
	}
	@Test
	fun mindrobotTest(){
		checkActorExist("mindrobot")
	}

	@Test
	fun basicrobotTest(){
		checkActorExist("basicrobot")
	}
	
	@Test
	fun sonarhandlerTest(){
		checkActorExist("sonarhandler")
	}
	@Test
	fun resourcemodelTest(){
		checkActorExist("resourcemodel")
	}
	@Test
	fun kbTest(){
		checkActorExist("kb")
	}

	
	

	
	@Test
	fun goToFridge(){
		var resourcesmodel :ActorBasic? = sysUtil.getActor("resourcemodel")
		println(" %%%%%%% $testName goToFridge")
		send(actor!!,"testCmd","testCmd(Start)");//avvio l'andata al frigo
		delay(6500)//do al robot il tempo di raggiungere il frigo-->posizione (6,0)
		var message: MqttMessage = request(resourcesmodel!!,"modelRequest","modelRequest(robot,location)","modelRobotResponse")
		delay(250)
		println("---->${message.toString()}")
		send(actor!!,"testCmd","testCmd(Next)");//avvio l'andata al frigo
		delay(6500)		
		send(actor!!,"testCmd","testCmd(Next)");//avvio l'andata al frigo
		delay(6500)
	}



}