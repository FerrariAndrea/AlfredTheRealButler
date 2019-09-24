%====================================================================================
% system8 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxpantry, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( pantry, ctxpantry, "it.unibo.pantry.Pantry").
