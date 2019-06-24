%====================================================================================
% sprint1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmindsprint1, "localhost",  "MQTT", "0" ).
 qactor( butler, ctxmindsprint1, "it.unibo.butler.Butler").
  qactor( butlerstep, ctxmindsprint1, "it.unibo.butlerstep.Butlerstep").
  qactor( planner, ctxmindsprint1, "it.unibo.planner.Planner").
  qactor( resourcemodel, ctxmindsprint1, "it.unibo.resourcemodel.Resourcemodel").
  qactor( basicrobot, ctxmindsprint1, "it.unibo.basicrobot.Basicrobot").
