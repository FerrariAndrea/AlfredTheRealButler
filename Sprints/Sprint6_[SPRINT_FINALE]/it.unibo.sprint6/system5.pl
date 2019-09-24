%====================================================================================
% system5 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxfridge, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
