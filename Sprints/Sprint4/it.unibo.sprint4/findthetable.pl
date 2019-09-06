%====================================================================================
% findthetable description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxfindthetable, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( basicrobot, ctxdummyformind, "external").
  qactor( tablefinder, ctxfindthetable, "it.unibo.tablefinder.Tablefinder").
  qactor( worker, ctxfindthetable, "it.unibo.worker.Worker").
