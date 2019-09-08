package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import itunibo.coap.modelResourceCoap

object resourceModelSupport{
lateinit var resourceFridgeCoap : modelResourceCoap
	
	fun setCoapResource( rescoap : modelResourceCoap ){
		resourceFridgeCoap = rescoap
	}
	
	fun updateRobotModel( actor: ActorBasic, content: String ){
 			actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			actor.solve(  "model( A, robot, STATE )" )
			val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelChanged" , "modelChanged(  robot,  $content)" )  //for the robotmind
				actor.emit( "modelContent" , "content( robot( $RobotState ) )" )
				resourceFridgeCoap.updateState( "robot( $RobotState )" )
  			}
	}	
	fun updateSonarRobotModel( actor: ActorBasic, content: String ){
 			actor.solve( "action( sonarRobot,  $content )" ) //change the robot state model
			actor.solve( "model( A, sonarRobot, STATE )" )
			val SonarState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateSonarRobotModel SonarState=$SonarState")
			actor.scope.launch{
 				actor.emit( "modelContent" , "content( sonarRobot( $SonarState ) )" )
				resourceFridgeCoap.updateState( "sonarRobot( $SonarState )" )
 			}	
	}
	fun updatePosRobotModel( actor: ActorBasic, content: String ){
 			actor.scope.launch{
 				actor.emit( "modelContent" , "posRobot( $content )" )
				resourceFridgeCoap.updateState( "posRobot( $content )" )
 			}	
	}	
}

