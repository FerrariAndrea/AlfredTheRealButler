%====================================================================================
% system9 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxdishwasher, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( dishwasher, ctxdishwasher, "it.unibo.dishwasher.Dishwasher").
