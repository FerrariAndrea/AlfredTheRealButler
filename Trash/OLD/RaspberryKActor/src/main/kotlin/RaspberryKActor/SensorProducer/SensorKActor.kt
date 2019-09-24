package RaspberryKActor.SensorProducer


import it.unibo.kactor.*

import kotlinx.coroutines.delay


class SensorKActor( name : String ) : ActorBasic( name ) {
    var n = 1
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   MyControlActor $name |  receives msg= $msg ")
        when ( msg.msgId() ) {
            "start" -> {

                emit(d.id, d.item)
                n++
            }
            else -> println("   MyControlActor $name |  msg= $msg ")
        }
    }
}




