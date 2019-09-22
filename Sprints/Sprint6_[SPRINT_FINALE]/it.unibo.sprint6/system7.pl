%====================================================================================
% system7 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorerroom, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( onestepahead, ctxdummy, "external").
  qactor( onecellforward, ctxdummy, "external").
  qactor( onerotateforward, ctxdummy, "external").
  qactor( kb, ctxdummy, "external").
  qactor( roomexplorer, ctxexplorerroom, "it.unibo.roomexplorer.Roomexplorer").
