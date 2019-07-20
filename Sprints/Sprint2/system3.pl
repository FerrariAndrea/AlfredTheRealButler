%====================================================================================
% system3 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
context(ctxcontroller, "localhost",  "MQTT", "0" ).
 qactor( kb, ctxdummy, "external").
  qactor( explorer, ctxdummy, "external").
  qactor( controller, ctxcontroller, "it.unibo.controller.Controller").
