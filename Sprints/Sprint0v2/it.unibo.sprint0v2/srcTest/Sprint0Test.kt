package it.unibo.sprint0v2

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


class Sprint0Test {


	companion object {
		//----------------------var
		var testName = "SprintTest"
		var actor : ActorBasic?=null
		init {}

//-------------------------------------------------------UTILS for test
	fun delay( time : Long ){
		Thread.sleep( time )
	}

	

	//-----------------------------------------------------END utils for test
		
		//start all
		@BeforeClass @JvmStatic
		fun systemSetUp() {
			println("Start test as Maitre")
			delay(250)
			GlobalScope.launch{
				it.unibo.ctxMaitre.main()
				
			}
			delay(500)
			GlobalScope.launch{
				actor=sysUtil.getActor("maitre")
				println(actor!!.name)
				
			}
		
			
		}

	}



	
	@Test
	fun fridgeTest(){
		println("----->fridgeTest")
		GlobalScope.launch{
			println(actor!!.name)
		}
		 
		
	}

	@Test
	fun fluxTest(){
		println("----->fluxTest")
		GlobalScope.launch{
			println(actor!!.name)
		}
		var event :String = ""
		assertTrue("Flux0","w"==event)
		
		
	}



}