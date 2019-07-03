%====================================================================================
% sprint1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobot, "localhost",  "MQTT", "0" ).
context(ctxmaitre, "localhosta",  "MQTT", "0" ).
context(ctxresourcemodel, "localhostb",  "MQTT", "0" ).
 qactor( resourcemodel, ctxresourcemodel, "it.unibo.resourcemodel.Resourcemodel").
  qactor( mindrobot, ctxrobot, "it.unibo.mindrobot.Mindrobot").
  qactor( basicrobot, ctxrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( kb, ctxresourcemodel, "it.unibo.kb.Kb").
  qactor( sonarhandler, ctxrobot, "it.unibo.sonarhandler.Sonarhandler").
