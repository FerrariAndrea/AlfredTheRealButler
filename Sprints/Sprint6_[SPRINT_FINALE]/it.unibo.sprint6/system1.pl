%====================================================================================
% system1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobot, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( kb, ctxdummy, "external").
  qactor( resourcemodel, ctxdummy, "external").
  qactor( mindrobot, ctxrobot, "it.unibo.mindrobot.Mindrobot").
  qactor( basicrobot, ctxrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxrobot, "it.unibo.sonarhandler.Sonarhandler").
  qactor( onerotateforward, ctxrobot, "it.unibo.onerotateforward.Onerotateforward").
  qactor( onecellforward, ctxrobot, "it.unibo.onecellforward.Onecellforward").
