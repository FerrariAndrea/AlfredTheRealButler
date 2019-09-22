%====================================================================================
% system6 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxcoaptester, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( fridge, ctxdummy, "external").
  qactor( pantry, ctxdummy, "external").
  qactor( dishwasher, ctxdummy, "external").
  qactor( coaptester, ctxcoaptester, "it.unibo.coaptester.Coaptester").
