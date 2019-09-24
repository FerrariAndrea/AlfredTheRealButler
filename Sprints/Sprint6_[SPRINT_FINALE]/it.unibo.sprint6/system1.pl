%====================================================================================
% system1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctx, "localhost",  "MQTT", "0" ).
 qactor( cliente, ctx, "it.unibo.cliente.Cliente").
  qactor( server, ctx, "it.unibo.server.Server").
