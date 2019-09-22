package it.unibo.robots19.experiment

fun buildString0(
    builderAction: (StringBuilder) -> Unit ): String {
        val sb = StringBuilder()
        builderAction(sb)
        return sb.toString()
}

fun run0() {
    val s = buildString0 {
        it.append("Hello, ")
        it.append("World!")
    }
     println(s)     //Hello, World!
}
//------------------------------------------------
fun buildString1(
/*
    Use extension function type that describes a block of code
    that can be called as an extension function
*/
    builderAction: StringBuilder.() -> Unit ) : String {
        val sb = StringBuilder()
        sb.builderAction()
        return sb.toString()
}
fun run1() {
    val s = buildString1 {
        this.append("Hello, ")
        append("World!")
    }
    println(s)  //Hello, World!
}

//------------------------------------------------
val appendExcl : StringBuilder.() -> Unit =
    { this.append("!") }

fun run2(){
    val stringBuilder = StringBuilder("Hi")
    stringBuilder.appendExcl()
    println(stringBuilder)                  //hi!
    println(buildString1(appendExcl))       //!
}
//------------------------------------------------
fun run4(){
    val map = mutableMapOf(1 to "one")
    map.apply { this[2] = "two"}
    with (map) { this[3] = "three" }
    println(map)    //{1=one, 2=two, 3=three}
}
//------------------------------------------------

fun mybuildString (  //Use extension function type
        builderAction: StringBuilder.( s : String  ) -> Unit,
        v : String )
    : String {
        val sb = StringBuilder()
        sb.append(v)
        sb.builderAction(v)
        sb.append(v)
        return sb.toString()
    }


fun myrun() {
    val s = mybuildString({
        append("Hello, ")
        append("World")
    }, "|")
    println(s)  //Hello, World!
}

fun main(args: Array<String>) {
    run0()
    run1()
    run2()
    run4()
    myrun()
}
