package it.unibo.robots19.basic


public sealed class  RobotCmds(val cmd: String, val arg : Int=0, val id : String = RobotCmds.id){
    override fun toString() : String{ return "$id(${cmd})" }

    companion object{
        val id        = "robotCmd"

        val forward   = RobotCmds.MoveForward()
        val backward  = RobotCmds.MoveBackward()
        val stop      = RobotCmds.MoveStop()
        val turnLeft  = RobotCmds.MoveTurnLeft()
        val turnRight = RobotCmds.MoveTurnRight()

        val forwardStr   = "forward"
        val backwardStr  = "backward"
        val stopStr      = "stop"
        val turnLeftStr  = "turnLeft"
        val turnRightStr = "turnRight"

        val cmdMap = mutableMapOf<String, String>(
            forwardStr to "{'type': 'moveForward', 'arg': -1 }",
            backwardStr to "{'type': 'moveBackward', 'arg': -1 }",
            stopStr to "{'type': 'alarm', 'arg': 0 }",
            turnLeftStr to "{'type': 'turnLeft', 'arg': 400 }",
            turnRightStr to "{'type': 'turnRight', 'arg': 400 }"
        )

    }



    class MoveForward   : RobotCmds(forwardStr, -1 )    //forever
    class MoveBackward  : RobotCmds(backwardStr, -1 )  //forever
    class MoveStop      : RobotCmds(stopStr )
    class MoveTurnLeft  : RobotCmds(turnLeftStr, 400 )
    class MoveTurnRight : RobotCmds(turnRightStr, 400 )
}
