/* Generated by AN DISI Unibo */ 
package it.unibo.ctxprint2
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint2.pl", "sysRules.pl"
	)
}

