%====================================================================================
% system1d description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorer, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( maitre, ctxdummy, "external").
  qactor( resourcemodel, ctxdummy, "external").
  qactor( onecellforward, ctxdummy, "external").
  qactor( explorer, ctxexplorer, "it.unibo.explorer.Explorer").
