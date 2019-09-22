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

class TestRobotmindAlarm {
   	var resource : ActorBasic? = null
	
	@Before
	@Throws(Exception::class)
	fun systemSetUp() {
   	 		GlobalScope.launch{
 			    println(" %%%%%%% TestRobotmindAlarm starts robot mind ")
				it.unibo.ctxRobotMind.main()
 			}
			delay(5000)		//give the time to start
 			getActors()			
  	}
	protected fun getActors() {		
		resource = sysUtil.getActor("resourcemodel")
		println(" %%%%%%% TestRobotmindAlarm getActors  resource=${resource}")
 	}

	@After
	fun terminate() {
		println(" %%%%%%% TestRobotmindAlarm terminate ")
	}
 
 	@Test
	fun moveAlarmTest() {
		println(" %%%%%%% TestRobotmindAlarm  moveAlarmTest %%%")
		moveForward(1000)
		delay(300)
 		generateEnvCond( resource!!, "alarm(fire)" )
		delay(100)
 		solveCheckGoal( resource!!, "model( actuator, robot, state(stopped) )" )
 	}
	
	fun moveForward( time:Long ) {
		println(" %%%%%%% TestRobotmindAlarm  moveForward using resource%%%")
		moveRobotViaResource( resource!!, "w", time)			
 	}

	fun generateEnvCond( actor : ActorBasic, what : String ){
		actor.scope.launch{
			actor.emit("envCond","envCond( $what )" )
		}		
	}
	
	fun delay( time : Long ){
		Thread.sleep( time )
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
	
}