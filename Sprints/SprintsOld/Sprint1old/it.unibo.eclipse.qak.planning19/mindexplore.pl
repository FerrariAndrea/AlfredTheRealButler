%====================================================================================
% mindexplore description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmindexplore, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( explorer, ctxmindexplore, "it.unibo.explorer.Explorer").
  qactor( onecellforward, ctxmindexplore, "it.unibo.onecellforward.Onecellforward").
  qactor( sonarhandler, ctxmindexplore, "it.unibo.sonarhandler.Sonarhandler").
