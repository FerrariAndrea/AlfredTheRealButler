%====================================================================================
% testprestazionale description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctx1, "dummyhost1",  "MQTT", "0" ).
context(ctx2, "dummyhost2",  "MQTT", "0" ).
 qactor( attore1, ctx1, "it.unibo.attore1.Attore1").
  qactor( attore2, ctx1, "it.unibo.attore2.Attore2").
