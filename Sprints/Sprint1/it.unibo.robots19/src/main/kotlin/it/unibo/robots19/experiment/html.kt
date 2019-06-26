package it.unibo.robots19.experiment

open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()

    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    override fun toString() =
        "<$name>${children.joinToString("")}</$name>"
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE : Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}
class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}
class TD : Tag("td")

/*
In the lambda passed to the table function, we can use
the tr function to create the <tr> tag.
Outside the lambda, the tr function would be unresolved.
The lambda passed to table has a receiver of type TABLE
which defines the tr method
 */
fun createTable() =
    table {
        tr {
            td {
            }
        }
    }
/*
EXPLIT RECEIVER
 */
fun createTable1() =
    table {
        (this@table).tr {
            (this@tr).td {
            }
        }
    }

fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {
            }
        }
    }
}

fun main(args: Array<String>) {
    println(createTable())
    println(createTable1())
    //println(createAnotherTable())
}
