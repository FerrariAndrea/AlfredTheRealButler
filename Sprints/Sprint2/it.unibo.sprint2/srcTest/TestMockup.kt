package it.unibo.eclipse.qak.robotMinds19

import it.unibo.kactor.ActorBasic
import org.junit.Before
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import org.junit.After
import org.junit.Test

class TestMockup {
 	var resource : ActorBasic? = null
	
	@Before
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()		//blocking
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