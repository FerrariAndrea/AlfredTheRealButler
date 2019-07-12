%====================================================================================
% roomexplore description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxroomexplore, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( roomexplorer, ctxroomexplore, "it.unibo.roomexplorer.Roomexplorer").
  qactor( onecellforward, ctxroomexplore, "it.unibo.onecellforward.Onecellforward").
