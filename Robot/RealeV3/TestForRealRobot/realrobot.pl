%====================================================================================
% realrobot description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxcontroller, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( mindrobot, ctxdummy, "external").
  qactor( onecellforward, ctxdummy, "external").
  qactor( onerotateforward, ctxdummy, "external").
  qactor( controller, ctxcontroller, "it.unibo.controller.Controller").
