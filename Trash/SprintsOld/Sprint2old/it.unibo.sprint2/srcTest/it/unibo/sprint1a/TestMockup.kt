package it.unibo.sprint1a

import org.junit.Before
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import org.junit.After
//import org.junit.Test
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test;
import org.junit.After


class TestMockup {
 	var resource : ActorBasic? = null
	

	@Before
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()	//blocking
 			}	
  	 		GlobalScope.launch{
				println(" %%%%%%% TestMockup starts resource model ")
				it.unibo.ctxResourceModel.main()
 			}
  	 		GlobalScope.launch{
				println(" %%%%%%% TestMockup starts robot mind ")
				it.unibo.ctxRobot.main()
 			}
		
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("resourcemodel")	
		    println(" %%%%%%% TestMockup getActors resource=${resource}")
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% TestMockup terminate ")
	}
 
	@Test
	fun moveTest() {
		println(" %%%%%%% TestMockup  moveTest ")
 	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}
}