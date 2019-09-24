context(ctxRobotExecutor,"localhost", "TCP", 8010).

qactor( robot, ctxRobotExecutor, "it.unibo.robots19.basic.BasicRobotExecutor" ).
qactor( tcpClient, ctxRobotExecutor, "it.unibo.robots19.basic.clientWenvTcp" ).