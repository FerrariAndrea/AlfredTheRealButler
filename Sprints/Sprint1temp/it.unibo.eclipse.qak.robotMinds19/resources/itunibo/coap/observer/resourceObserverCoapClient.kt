package itunibo.coap.observer
/*
 resourceObserverCoapClient 
*/
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapObserveRelation
import org.eclipse.californium.core.CoapResponse
import itunibo.outgui.outguiSupport
import java.awt.Color
 
object Listener : CoapHandler {

	val outDev = outguiSupport.create("Resource Coap OBSERVER", Color.green)
	
	override fun onLoad(response: CoapResponse?) {
		val content = response!!.getResponseText()
		outguiSupport.output("$content"  )
	}
	override fun onError() {
		outguiSupport.output("Listener Error")
	}
}

	fun main( ) {
		//val client = CoapClient("coap://localhost:5683/resourcemodel")
		val client = CoapClient("coap://192.168.43.67:5683/resourcemodel")
// observe
		println("CoapLedObserverClient.java: OBSERVE (press enter to exit)")
 
		val relation = client.observe(  Listener )  //CoapHandler
		 
// After you have setup your observe relation you need to make sure your program is still doing something
		// wait for user
		val br = BufferedReader(InputStreamReader(System.`in`))
		try {
			br.readLine()
			println("CoapLedObserverClient.java: CANCELLATION")
			relation!!.proactiveCancel()
		} catch (e: IOException) {
		}
	}
 