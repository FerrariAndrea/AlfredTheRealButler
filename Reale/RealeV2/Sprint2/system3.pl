%====================================================================================
% system3 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorerdummy, "dummyhost",  "MQTT", "0" ).
context(ctxcontroller, "localhost",  "MQTT", "0" ).
 qactor( explorer, ctxexplorerdummy, "external").
  qactor( controller, ctxcontroller, "it.unibo.controller.Controller").
