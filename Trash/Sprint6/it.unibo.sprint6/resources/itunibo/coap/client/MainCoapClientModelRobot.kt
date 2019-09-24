/*
 MainCoapClientModelRobot.kt
 */
package itunibo.coap.client

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.Utils
import org.eclipse.californium.core.coap.MediaTypeRegistry
import itunibo.coap.observer.AsynchListener

	private lateinit var coapClient: CoapClient
	
	fun createClient(serverAddr: String, port: Int, resourceName: String?) {
		coapClient = CoapClient("coap://$serverAddr:" + port + "/" + resourceName)
		println("Client started")
	}

	fun synchGet() { //Synchronously send the GET message (blocking call)
		println("%%% synchGet ")
//		CoapResponse coapResp = coapClient.advanced(request);
		val coapResp = coapClient.get()
//The "CoapResponse" message contains the response.
 		//println(Utils.prettyPrint(coapResp))
		println(coapResp.responseText)
	}

	fun put(v: String) {
		val coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN)
//The "CoapResponse" message contains the response.
		println("%%% ANSWER put $v:")
		//println(Utils.prettyPrint(coapResp))
		println(coapResp.responseText)
	}

 	fun asynchGet() {
 		coapClient.get( AsynchListener );
	}


	fun main() {
		val hostAddr = "localhost" // "192.168.43.67 3"

		val resourceName = "resourcemodel"
		//createClient("localhost", 5683, resourceName)
		createClient(hostAddr, 5683, resourceName) 
		synchGet()
		put("w")
		Thread.sleep(1500)
		synchGet()
		put("s")
		synchGet()
		Thread.sleep(500)
		put("h")
		Thread.sleep(10)
		synchGet()
		put("a")
		Thread.sleep(500)
		synchGet()
		put("d")
		Thread.sleep(500)
		synchGet()
		put("h")
		Thread.sleep(100)
		synchGet()
		 
	}
