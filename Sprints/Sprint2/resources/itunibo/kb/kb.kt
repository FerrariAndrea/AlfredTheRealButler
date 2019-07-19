package itunibo.kb

import it.unibo.kactor.ActorBasic

 private var instance_actor : ActorBasic? = null

fun instance(actor : ActorBasic ){
	instance_actor= actor	
	instance_actor!!.solve("consult(\"robotPosResolver.pl\")")
}

fun updateRobotPos( move: String ){
		instance_actor!!.solve("retract( direction(_) )")		//remove old data
		instance_actor!!.solve("assert( direction($direction) )")		
}