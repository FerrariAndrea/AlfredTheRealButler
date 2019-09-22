%====================================================================================
% systemtest description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxtest, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( kb, ctxdummy, "external").
  qactor( explorer, ctxdummy, "external").
  qactor( resourcemodel, ctxdummy, "external").
  qactor( tester, ctxtest, "srcTest.Tester").
