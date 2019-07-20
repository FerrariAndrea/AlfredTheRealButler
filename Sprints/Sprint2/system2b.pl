%====================================================================================
% system2b description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
context(ctxresourcemodel, "localhost",  "MQTT", "0" ).
 qactor( controller, ctxdummy, "external").
  qactor( mindrobot, ctxdummy, "external").
  qactor( resourcemodel, ctxresourcemodel, "it.unibo.resourcemodel.Resourcemodel").
  qactor( kb, ctxresourcemodel, "it.unibo.kb.Kb").
