context(ctxProducer,"192.168.137.1", "TCP", 8010).
context(ctxConsumer1,"192.168.137.222", "TCP", 8020).
context(ctxConsumer2,"192.168.137.15", "TCP", 8020).

%%qactor( button,   ctxProducer, "it.unibo.qak.stream.ButtonGuiActork").
qactor( producer, ctxProducer, "lab7.Producer").
qactor( consumer1, ctxConsumer1, "lab7.Consumer").
qactor( consumer2, ctxConsumer2, "lab7.Consumer").

