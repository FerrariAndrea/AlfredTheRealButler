%====================================================================================
% analisi description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobot, "localhost",  "MQTT", "0" ).
context(ctxmaitre, "localhost",  "MQTT", "0" ).
context(ctxresourcemodel, "localhost",  "MQTT", "0" ).
 qactor( tester, ctxrobot, "it.unibo.tester.Tester").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( basicrobot, ctxrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( mindrobot, ctxrobot, "it.unibo.mindrobot.Mindrobot").
  qactor( fridge, ctxrobot, "it.unibo.fridge.Fridge").
  qactor( resourcemodel, ctxresourcemodel, "it.unibo.resourcemodel.Resourcemodel").
  qactor( kb, ctxresourcemodel, "it.unibo.kb.Kb").