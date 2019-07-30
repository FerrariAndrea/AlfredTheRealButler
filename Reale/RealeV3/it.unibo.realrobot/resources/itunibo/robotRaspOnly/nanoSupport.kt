package it.unibo.robotRaspOnly

import it.unibo.iot.baseRobot.hlmodel.BasicRobot
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeed
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeedValue
import it.unibo.iot.models.commands.baseRobot.BaseRobotStop
import it.unibo.iot.models.commands.baseRobot.BaseRobotForward
import it.unibo.iot.models.commands.baseRobot.BaseRobotBackward
import it.unibo.iot.models.commands.baseRobot.BaseRobotLeft
import it.unibo.iot.models.commands.baseRobot.BaseRobotRight
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import itunibo.robotRaspOnly.sonarHCSR04Support
import itunibo.robotRaspOnly.otherSonarsSupport
import itunibo.robotRaspOnly.sonarBelancerOnlySupport

 
object nanoSupport {
	
	val SPEED_LOW     = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW)
	val SPEED_MEDIUM  = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM)
	val SPEED_HIGH    = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH)
	 
	val basicRobot    = BasicRobot.getRobot()
	val robot         = basicRobot.getBaseRobot()
 	
	fun create(actor: ActorBasic, withSonar : Boolean = true){
		println("nanoSupport (MODDED) CREATING $robot")
		if(withSonar){
			//sonarHCSR04Support.create( actor, " ")
			//QUI IL SUPPORTO DEGLI ALTRI SONAR (non tativo)
			//otherSonarsSupport.create(actor,true,true,false)
			//sonarBelancerOnlySupport.create(actor)
		}
	} 
	
	fun move( cmd : String ){
		println( "nanoSupport move $cmd $robot" )
		var command : IBaseRobotCommand = BaseRobotStop(SPEED_LOW )
		when( cmd ){
			"msg(w)" -> command = BaseRobotForward( SPEED_LOW )
			"msg(s)" -> command = BaseRobotBackward(SPEED_LOW )
			"msg(a)" -> command = BaseRobotRight(SPEED_MEDIUM )//Ho dovuto invertire destra e sinistra
			"msg(d)" -> command = BaseRobotLeft(SPEED_MEDIUM )//Ho dovuto invertire destra e sinistra
			"msg(h)" -> command = BaseRobotStop(SPEED_LOW )
			
			"msg(sa)" -> command = BaseRobotRight(SPEED_HIGH )//Ho dovuto invertire destra e sinistra
			"msg(sd)" -> command = BaseRobotLeft(SPEED_HIGH )//Ho dovuto invertire destra e sinistra
			
		}
		robot.execute(command)
	}
	
}