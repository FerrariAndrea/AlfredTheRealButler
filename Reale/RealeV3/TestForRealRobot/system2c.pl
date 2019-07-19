%====================================================================================
% system2c description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobot, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( maitre, ctxdummy, "external").
  qactor( mindrobot, ctxrobot, "it.unibo.mindrobot.Mindrobot").
  qactor( basicrobot, ctxrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxrobot, "it.unibo.sonarhandler.Sonarhandler").
