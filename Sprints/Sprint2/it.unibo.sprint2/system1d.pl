%====================================================================================
% system1d description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorer, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( explorer, ctxexplorer, "it.unibo.explorer.Explorer").
  qactor( onecellforward, ctxexplorer, "it.unibo.onecellforward.Onecellforward").
