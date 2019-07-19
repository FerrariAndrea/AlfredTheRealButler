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


class Sprint1Test {
	var actor : ActorBasic?= null
	var testName = "Sprint2Test"
	var sleepTime = 1000L
	@Before
	fun systemSetUp() {
	
			GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()	//blocking
 			}
			delay(sleepTime)
		/*
			GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()	//blocking
 			}
			delay(2*sleepTime)
			
			println(" %%%%%%% $testName starts robot mind ")
  	 		GlobalScope.launch{
				it.unibo.ctxRobot.main()
 			}
  	 		delay(5*sleepTime)	//necessario, se no resources crascia perchè non trova robotminde
  	 		
			println(" %%%%%%% $testName starts resource model ")	
 			GlobalScope.launch{			
				it.unibo.ctxResourceModel.main()
 			}
			*/
			GlobalScope.launch{			
				it.unibo.ctxExplorer.main()
			
 			}
  	 		delay(10*sleepTime)
			actor = sysUtil.getActor("explorer")
		    //delay(60000)
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% $testName terminate ")
	}
 
	@Test
	fun allActorOnTest(){		
		println(" %%%%%%% $testName allActorOn")
		assertTrue("", null != sysUtil.getActor("explorer") )
		assertTrue("", null != sysUtil.getActor("onecellforward") )	
	}
	@Test
	fun goToFridge(){		
		println(" %%%%%%% $testName goToFridge")
		send(actor!!,"testCmd","testCmd(Start)");//avvio l'andata al frigo
		delay(6500)//do al robot il tempo di raggiungere il frigo-->posizione (6,0)
		//qui ci vuole l'assert
		send(actor!!,"testCmd","testCmd(Next)");//avvio l'andata al frigo
		delay(6500)		
		send(actor!!,"testCmd","testCmd(Next)");//avvio l'andata al frigo
		delay(6500)
	}
	fun delay( time : Long ){
		Thread.sleep( time )
	}

	//funzione che manda messaggi ad attori (prbabilmente solo nel cotesto del attore "actor")
	fun send( actor : ActorBasic,msg :String ,msg_and_payload : String){
		
		actor.scope.launch{
			println("--- send $msg -->$msg_and_payload")
			//actor.autoMsg("$dest","$msg($payload)")
  			MsgUtil.sendMsg("$msg","$msg_and_payload",actor)
 		}
  	}
}