%====================================================================================
% systemreal description   
%====================================================================================
mqttBroker("192.168.43.61", "1883").
context(ctxrobot, "localhost",  "MQTT", "0" ).
context(ctxdummy, "dummyhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( maitre, ctxdummy, "external").
  qactor( mindrobot, ctxrobot, "it.unibo.mindrobot.Mindrobot").
  qactor( basicrobot, ctxrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxrobot, "it.unibo.sonarhandler.Sonarhandler").
  qactor( onecellforward, ctxrobot, "it.unibo.onecellforward.Onecellforward").
  qactor( timer, ctxrobot, "it.unibo.timer.Timer").
  qactor( leds, ctxrobot, "it.unibo.leds.Leds").
  qactor( flusher, ctxrobot, "it.unibo.flusher.Flusher").
  qactor( realsonar, ctxrobot, "it.unibo.realsonar.Realsonar").
