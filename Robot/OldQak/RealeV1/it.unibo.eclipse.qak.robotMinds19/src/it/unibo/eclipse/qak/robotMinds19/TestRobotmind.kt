package it.unibo.eclipse.qak.robotMinds19

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After
import org.junit.Before
import org.junit.Test
import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class TestRobotmind {
 	var resource : ActorBasic? = null
	
	@Before
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()		//blocking
 			}	
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestRobotmind starts robot mind ")
				it.unibo.ctxRobotMind.main()
 			}
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("resourcemodel")	
		    println(" %%%%%%% TestRobotmind getActors resource=${resource}")
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% TestRobotmind terminate ")
	}
 
	@Test
	fun moveTest() {
		println(" %%%%%%% TestRobotmind  moveTest ")
 		rotateRight(500)
 		rotateLeft(500)
 		moveForward(700)	//no obstacle assumed
 		moveBackward(700)	//no obstacle assumed
 		stoprobot(100)
 	}

	fun stoprobot(time:Long) {
		println(" %%%%%%% TestRobotmind  stoprobot usung robot %%%")
		moveRobotViaResource( resource!!, "h", time)			
		solveCheckGoal( resource!!,  "model( actuator, robot, state(stopped) )" )
	}
	fun moveForward( time:Long ) {
		println(" %%%%%%% TestRobotmind  moveForward using resource%%%")
		moveRobotViaResource( resource!!, "w", time)			
		solveCheckGoal( resource!!, "model( actuator, robot, state(movingForward) )" )
 	}
	
	fun moveBackward( time:Long ) {
		println(" %%%%%%% TestRobotmind  moveBackward %%%")
		moveRobotViaResource( resource!!, "s", time)			
		solveCheckGoal( resource!!, "model( actuator, robot, state(movingBackward) )" )
 	}
	 
	fun rotateLeft( time:Long ) {
		println(" %%%%%%% TestRobotmind  rotateLeft %%%")
		moveRobotViaResource( resource!!, "a", time)			
		solveCheckGoal( resource!!, "model( actuator, robot, state(rotateLeft) )" )
	}
	 
	fun rotateRight( time:Long ) {
		println(" %%%%%%% TestRobotmind  rotateRight %%%")
		moveRobotViaResource( resource!!, "d", time)			
		solveCheckGoal( resource!!, "model( actuator, robot, state(rotateRight) )" )
	}	

//----------------------------------------
	
	fun moveRobotViaResource( actor : ActorBasic, move : String, time : Long ){
		actor.scope.launch{
			println("--- moveRobotViaResource modelChange(robot,$move)")
  			MsgUtil.sendMsg("modelChange","modelChange(robot,$move)",actor)
 		}
		delay(time) //give time to do the move
  	}

 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue("", result == "success" )
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}