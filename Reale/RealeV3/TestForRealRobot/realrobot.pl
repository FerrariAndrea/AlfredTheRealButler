%====================================================================================
% realrobot description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxcontroller, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( mindrobot, ctxdummy, "external").
  qactor( controller, ctxcontroller, "it.unibo.controller.Controller").
  qactor( timer, ctxcontroller, "it.unibo.timer.Timer").
  qactor( onecellforward, ctxcontroller, "it.unibo.onecellforward.Onecellforward").
