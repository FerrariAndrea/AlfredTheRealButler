%====================================================================================
% analisi description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxtest, "localhost",  "MQTT", "0" ).
 qactor( tester, ctxtest,"it.unibo.tester.Tester").
  qactor( maitre, ctxtest,"it.unibo.maitre.Maitre").
  qactor( basicrobot, ctxtest,"it.unibo.basicrobot.Basicrobot").
  qactor( mindrobot, ctxtest,"it.unibo.mindrobot.Mindrobot").
  qactor( fridge, ctxtest,"it.unibo.fridge.Fridge").
  qactor( resourcemodel, ctxtest,"it.unibo.resourcemodel.Resourcemodel").
  qactor( kb, ctxtest,"it.unibo.kb.Kb").