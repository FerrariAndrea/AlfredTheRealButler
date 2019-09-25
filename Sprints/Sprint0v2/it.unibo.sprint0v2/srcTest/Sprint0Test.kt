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
import it.unibo.tester.Tester


class Sprint0Test {


	companion object {
		//----------------------var
		var testName = "SprintTest"
		var actor : Tester?= null
		init {}

//-------------------------------------------------------UTILS for test
	fun delay( time : Long ){
		Thread.sleep( time )
	}

	

	//-----------------------------------------------------END utils for test
		
		//start all
		@BeforeClass @JvmStatic
		fun systemSetUp() {
			println("Start test, starting ctxs")
			delay(250)
			GlobalScope.launch{
				srcTest.main()					
			}
			delay(2000)
			println("Getting Tester actor")
			GlobalScope.launch{
				actor=sysUtil.getActor("tester") as Tester
			}
		}

	}


	fun waitNextFlux():String{
		while(actor!!.isReady()==0){
			delay(50)
		}
		val temp = actor!!.getFlux()!!
		return temp
	}

	@Test
	fun actorTest(){
		println("Start actor test")
		assertTrue("Check Actor tester.",(actor!!.name=="tester") )	
	}
	@Test
	fun fluxTsest(){
		println("Start flux test")
		assertTrue("Check msg flux 1",waitNextFlux().startsWith("resourcesModelChange",true))
		assertTrue("Check msg flux 2",waitNextFlux().startsWith("mindrobot",true))
		assertTrue("Check msg flux 3",waitNextFlux().startsWith("basicrobot",true))
		assertTrue("Check msg flux 4",waitNextFlux().startsWith("resourcesModelUpdate",true))
		assertTrue("Check msg flux 5",waitNextFlux().startsWith("KB",true))
	}
	

	


}