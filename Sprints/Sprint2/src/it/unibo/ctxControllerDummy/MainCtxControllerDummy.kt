/* Generated by AN DISI Unibo */ 
package it.unibo.ctxControllerDummy
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "dummyhost2", this, "system1d.pl", "sysRules.pl"
	)
}
