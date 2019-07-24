package itunibo.planner

import java.util.ArrayList
import aima.core.agent.Action
import aima.core.search.framework.SearchAgent
import aima.core.search.framework.problem.GoalTest
import aima.core.search.framework.problem.Problem
import aima.core.search.framework.qsearch.GraphSearch
import aima.core.search.uninformed.BreadthFirstSearch
//import it.unibo.exploremap.stella.model.Box
import it.unibo.exploremap.stella.model.Functions
import it.unibo.exploremap.stella.model.RobotAction
import it.unibo.exploremap.stella.model.RobotState
//import it.unibo.exploremap.stella.model.RoomMap
import it.unibo.exploremap.stella.model.RobotState.Direction
import java.io.PrintWriter
import java.io.FileWriter
import java.io.ObjectOutputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.FileInputStream

object plannerUtil {
    private var initialState: RobotState? = null
	private var actions: List<Action>?    = null
/*
 * ------------------------------------------------
 * PLANNING
 * ------------------------------------------------
 */
    private var search: BreadthFirstSearch? = null
    var goalTest: GoalTest = Functions()		//init
    private var timeStart: Long = 0

    @Throws(Exception::class)
    fun initAI() {
        println("plannerUtil initAI")
        initialState = RobotState(0, 0, RobotState.Direction.DOWN)
        search       = BreadthFirstSearch(GraphSearch())
    }

    fun getActions() : List<Action>{
        return actions!!
    }
 
    @Throws(Exception::class)
    fun doPlan(): List<Action>? {
        //var actions: List<Action>?
        val searchAgent: SearchAgent
        //println("plannerUtil doPlan newProblem (A) " );
        val problem = Problem(initialState, Functions(), Functions(), goalTest, Functions())
        //println("plannerUtil doPlan newProblem (A) search " );
        searchAgent = SearchAgent(problem, search!!)
        actions     = searchAgent.actions
        if (actions == null || actions!!.isEmpty()) {
            println("plannerUtil doPlan NO MOVES !!!!!!!!!!!! $actions!!"   )
            if (!RoomMap.getRoomMap().isClean) RoomMap.getRoomMap().setObstacles()
            //actions = ArrayList()
            return null
        } else if (actions!![0].isNoOp) {
            println("plannerUtil doPlan NoOp")
            return null
        }
        println("plannerUtil doPlan actions=$actions")
        return actions
    }
	
    fun executeMoves( ) {
		if( actions == null ) return
        val iter = actions!!.iterator()
        while (iter.hasNext()) {
            plannerUtil.doMove(iter.next().toString())
        }
    }


/*
* ------------------------------------------------
* MAP UPDATE
* ------------------------------------------------
*/
	
    fun getPosX() : Int{ return initialState!!.x }
    fun getPosY() : Int{ return initialState!!.y }
     
    fun doMove(move: String) {
        val dir = initialState!!.direction
        val dimMapx = RoomMap.getRoomMap().dimX
        val dimMapy = RoomMap.getRoomMap().dimY
        val x = initialState!!.x 
        val y = initialState!!.y
        //println("plannerUtil: doMove move=$move  dir=$dir x=$x y=$y dimMapX=$dimMapx dimMapY=$dimMapy")
       try {
            when (move) {
                "w" -> {
                    RoomMap.getRoomMap().put(x, y, Box(false, false, false)) //clean the cell
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.FORWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "s" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.BACKWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "a","l" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "d","r" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNRIGHT)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "c"    //forward and  clean
                -> {
                    RoomMap.getRoomMap().put(x, y, Box(false, false, false))
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.FORWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
				//Box(boolean isObstacle, boolean isDirty, boolean isRobot)
                "rightDir" -> RoomMap.getRoomMap().put(x + 1, y, Box(true, false, false)) 
                "leftDir"  -> RoomMap.getRoomMap().put(x - 1, y, Box(true, false, false))
                "upDir"    -> RoomMap.getRoomMap().put(x, y - 1, Box(true, false, false))
                "downDir"  -> RoomMap.getRoomMap().put(x, y + 1, Box(true, false, false))
			           }//switch
        } catch (e: Exception) {
            println("plannerUtil doMove: ERROR:" + e.message)
        }

//        val newdir = initialState!!.direction.toString().toLowerCase() + "Dir"
//        val x1     = initialState!!.x
//        val y1     = initialState!!.y
        //update the kb
        //println("plannerUtil: doMove move=$move newdir=$newdir x1=$x1 y1=$y1")
    }

     
    fun showMap() {
        println(RoomMap.getRoomMap().toString())
    }
	
    fun saveMap(  fname : String) {		
        println("saveMap in $fname")
		val pw = PrintWriter( FileWriter(fname) )
		pw.print( RoomMap.getRoomMap().toString() )
		pw.close()
		
		val os = ObjectOutputStream( FileOutputStream("roomMap.bin") )
		os.writeObject(RoomMap.getRoomMap())
		os.flush()
		os.close()
    }
	
	fun loadRoomMap(   ) : Pair<Int,Int> {
	    var dimMapx = 0
	    var dimMapy = 0
		try{
			val inps = ObjectInputStream(FileInputStream("roomMap.bin"))
			val map  = inps.readObject() as RoomMap;
			println(map.toString())
	        dimMapx = map.getDimX()
	        dimMapy = map.getDimY()
			println("dimMapx = $dimMapx, dimMapy=$dimMapy")
		}catch(e:Exception){
			
		}
		return Pair(dimMapx,dimMapy)
	}
			
	fun getMap() : String{
		return RoomMap.getRoomMap().toString()
	}
/*
 * ---------------------------------------------------------
 */
    fun setGoalInit() {
        goalTest = Functions()
    }

	fun setGoal( x: String, y: String) {
		setGoal( Integer.parseInt(x), Integer.parseInt(y))
	}	

	//Box(boolean isObstacle, boolean isDirty, boolean isRobot)
    fun setGoal( x: Int, y: Int) {
        try {
            println("setGoal $x,$y while robot in cell: ${getPosX()},  ${getPosY()}")
            RoomMap.getRoomMap().put(x, y, Box(false, true, false))
            goalTest = GoalTest { state  : Any ->
                val robotState = state as RobotState
				(robotState.x == x && robotState.y == y)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
	fun resetGoal( x: String, y: String) {
		resetGoal( Integer.parseInt(x), Integer.parseInt(y))
	}	
    fun resetGoal( x: Int, y: Int) {
        try {
            println("resetGoal $x,$y while robot in cell: ${getPosX()},  ${getPosY()}")
            RoomMap.getRoomMap().put(x, y, Box(false, false, false))
            goalTest = GoalTest { state  : Any ->
                val robotState = state as RobotState
				(robotState.x == x && robotState.y == y)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startTimer() {
        timeStart = System.currentTimeMillis()
		//println("startTimer.StartTime-->$timeStart")
    }
	
    fun getDuration() : Int{
        val duration = (System.currentTimeMillis() - timeStart).toInt()
		//println("getDuration.EndTime-->${System.currentTimeMillis()}")
		//println("getDuration.StartTime-->$timeStart")
		//println("DURATION = $duration")
		return duration
    }
	
	fun getDirection() : String{
		//val direction = initialState!!.direction.toString()
		val direction = initialState!!.direction 
		when( direction ){
			Direction.UP    -> return "upDir"
			Direction.RIGHT -> return "rightDir"
			Direction.LEFT  -> return "leftDir"
			Direction.DOWN  -> return "downDir"
			else            -> return "unknownDir"
 		}
  	}

/*
 * Direction
 */
    fun rotateDirection() {
        //println("before rotateDirection: " + initialState.getDirection() );
        initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
        initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
        //println("after  rotateDirection: " + initialState.getDirection() );
        //update the kb
        val x = initialState!!.x
        val y = initialState!!.y
        val newdir = initialState!!.direction.toString().toLowerCase() + "Dir"
    }
}
