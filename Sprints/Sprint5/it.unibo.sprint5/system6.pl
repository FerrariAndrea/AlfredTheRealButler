%====================================================================================
% system6 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxcoapclient, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( fridge, ctxdummy, "external").
  qactor( pantry, ctxdummy, "external").
  qactor( coapclient, ctxcoapclient, "it.unibo.coapclient.Coapclient").
