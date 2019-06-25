%====================================================================================
% sprint1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmindsprint1, "localhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxmindsprint1, "external").
  qactor( butler, ctxmindsprint1, "it.unibo.butler.Butler").
  qactor( butlerstep, ctxmindsprint1, "it.unibo.butlerstep.Butlerstep").
  qactor( planner, ctxmindsprint1, "it.unibo.planner.Planner").
