/* Generated by AN DISI Unibo */ 
package it.unibo.ctxDummy
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "dummyhost", this, "system9.pl", "sysRules.pl"
	)
}

