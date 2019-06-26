%====================================================================================
% mindbutler description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmindbutler, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( butler, ctxmindbutler, "it.unibo.butler.Butler").
  qactor( butlerstep, ctxmindbutler, "it.unibo.butlerstep.Butlerstep").
  qactor( sonarhandlerbutler, ctxmindbutler, "it.unibo.sonarhandlerbutler.Sonarhandlerbutler").
