package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import itunibo.coap.modelResourceCoap

object resourceModelSupport{
lateinit var resourceCoap : modelResourceCoap
	
	fun setCoapResource( rescoap : modelResourceCoap ){
		resourceCoap = rescoap
	}
	
	fun updateRobotModel( actor: ActorBasic, content: String ){
 			actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			actor.solve(  "model( A, robot, STATE )" )
			val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelChanged" , "modelChanged(  robot,  $content)" )  //for the robotmind
				actor.emit( "modelContent" , "content( robot( $RobotState ) )" )
				resourceCoap.updateState( "robot( $RobotState )" )
  			}
	}	
	fun updateSonarRobotModel( actor: ActorBasic, content: String ){
 			actor.solve( "action( sonarRobot,  $content )" ) //change the robot state model
			actor.solve( "model( A, sonarRobot, STATE )" )
			val SonarState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateSonarRobotModel SonarState=$SonarState")
			actor.scope.launch{
 				actor.emit( "modelContent" , "content( sonarRobot( $SonarState ) )" )
				resourceCoap.updateState( "sonarRobot( $SonarState )" )
 			}	
	}
	fun updatePosRobotModel( actor: ActorBasic, content: String ){
 			actor.scope.launch{
 				actor.emit( "modelContent" , "posRobot( $content )" )
				resourceCoap.updateState( "posRobot( $content )" )
 			}	
	}
	
	fun updateFridgeModel( actor: ActorBasic, content: String ){
 			//actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			//actor.solve(  "model( A, robot, STATE )" )
			//val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelChanged" , "modelChanged(  fridge,  $content)" )  //for the robotmind
				//actor.emit( "modelContent" , "content( fridge( $RobotState ) )" )
				resourceCoap.updateState( content )
  			}
	}
	
	fun updatePantryModel( actor: ActorBasic, content: String ){
 			//actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			//actor.solve(  "model( A, robot, STATE )" )
			//val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelChanged" , "modelChanged(  pantry,  $content)" )  //for the robotmind
				//actor.emit( "modelContent" , "content( pantry( $RobotState ) )" )
				resourceCoap.updateState( content )
  			}
	}		
}

