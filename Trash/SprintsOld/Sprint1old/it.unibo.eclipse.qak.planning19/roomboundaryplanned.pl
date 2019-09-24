%====================================================================================
% roomboundaryplanned description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxboundaryplanned, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( planex1, ctxboundaryplanned, "it.unibo.planex1.Planex1").
  qactor( onecellforward, ctxboundaryplanned, "it.unibo.onecellforward.Onecellforward").
