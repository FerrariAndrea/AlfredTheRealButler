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

class TestRobotmindObstacle {
  	var resource : ActorBasic? = null
	
	@Before
 	fun systemSetUp() {
   	 		GlobalScope.launch{
 			    println(" %%%%%%% TestRobotmindObstacle starts robot mind ")
				it.unibo.ctxRobotMind.main()
 			}
			delay(5000)		//give the time to start
 			resource = sysUtil.getActor("resourcemodel")		
		    println(" %%%%%%% TestRobotmindObstacle resource=${resource}")
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% TestRobotmindObstacle terminate ")
	}
 
	@Test
	fun moveForwardWithObstaclePlanned(  ) {
		val time = 2000L
		println(" %%%%%%% TestRobotmindObstacle  moveForwardWithObstaclePlanned %%%")
	 		GlobalScope.launch{
				delay( time / 2 )
 			    println(" %%%%%%% TestRobotmindObstacle SIMULATES OBSTACLE")
				resource!!.emit("sonarRobot", "sonar(8)")
 			}
		moveRobotViaResource( resource!!, "w", time)			
		solveCheckGoal( resource!!, "model( actuator, robot, state(stopped) )" )
	}
		
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