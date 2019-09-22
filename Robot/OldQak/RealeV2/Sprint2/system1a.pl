%====================================================================================
% system1a description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmaitre, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
