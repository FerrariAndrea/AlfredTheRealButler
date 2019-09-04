package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import itunibo.coap.modelFridgeResourceCoap

object resourceFridgeModelSupport{
lateinit var resourceFridgeCoap : modelFridgeResourceCoap
	
	fun setCoapResource( rescoap : modelFridgeResourceCoap ){
		resourceFridgeCoap = rescoap
	}
	
	fun updateRobotModel( actor: ActorBasic, content: String ){
 			//actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			//actor.solve(  "model( A, robot, STATE )" )
			//val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				//actor.emit( "modelChanged" , "modelChanged(  robot,  $content)" )  //for the robotmind
				//actor.emit( "modelContent" , "content( robot( $RobotState ) )" )
				resourceFridgeCoap.updateState( content )
  			}
	}	
}

