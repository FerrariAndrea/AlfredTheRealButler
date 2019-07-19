%====================================================================================
% system3 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxexplorerdummy, "dummyhost1",  "MQTT", "0" ).
context(ctxcontroller, "localhost",  "MQTT", "0" ).
context(ctxresourcemodeldummy, "dummyhost2",  "MQTT", "0" ).
 qactor( kb, ctxresourcemodeldummy, "external").
  qactor( explorer, ctxexplorerdummy, "external").
  qactor( controller, ctxcontroller, "it.unibo.controller.Controller").
