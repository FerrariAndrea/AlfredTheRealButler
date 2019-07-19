%====================================================================================
% system1d description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorer, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost1",  "MQTT", "0" ).
context(ctxcontrollerdummy, "dummyhost2",  "MQTT", "0" ).
 qactor( controller, ctxcontrollerdummy, "external").
  qactor( resourcemodel, ctxdummy, "external").
  qactor( explorer, ctxexplorer, "it.unibo.explorer.Explorer").
  qactor( onecellforward, ctxexplorer, "it.unibo.onecellforward.Onecellforward").
