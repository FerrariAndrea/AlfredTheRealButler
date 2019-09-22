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


class Sprint2Test1 {


	companion object {
		//----------------------var
		var testName = "Sprint2Test1"
		var sleepTime = 1000L
		var testCount =0
		init {}

//-------------------------------------------------------UTILS for test
	fun delay( time : Long ){
		Thread.sleep( time )
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
			println(" %%%%%%% $testName starts ctxRobot ")
			GlobalScope.launch{
				it.unibo.ctxRobot.main()
			}
			delay(3*sleepTime)	//necessario, se no resources crascia perchè non trova robotminde
	
			println(" %%%%%%% $testName starts ctxResourceModel")	
			GlobalScope.launch{			
				it.unibo.ctxResourceModel.main()
			}
			delay(3*sleepTime)	//necessario, se no resources crascia perchè non trova robotminde
	
			println(" %%%%%%% $testName starts ctxExplorer")	 			
			GlobalScope.launch{
				it.unibo.ctxExplorer.main()							
			}
			delay(3*sleepTime)
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

	
	



}