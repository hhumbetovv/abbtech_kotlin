package lesson_1

fun main() {
    println(justANameRepeaterFunction("Nameless Person"))
}

/**
 * What are u looking at? It isn't obvious?
 * It's just a function that returns the name it takes
 * @author the name repeater humans
 * @exception ...ohh, r u still reading? What exceptions do you think are possible here?
 * @see <a href="https://www.kotlinlang.org/docs/functions.html">Kotlin Functions</a>
 * @see JustANameRepeaterClass <== check this out too
 **/
fun justANameRepeaterFunction(name: String): String {
    return name
}

/**
 * Same as [justANameRepeaterFunction]
 *
 * but you can make it repeat forever if you want.
 *
 * just watch
 * @sample repeatForever
 **/
class JustANameRepeaterClass (val name: String) {
    fun repeatSlaveeeClass(): String {
        return this.name
    }

    fun repeatForever() {
        while (true) {
            repeatSlaveeeClass()
        }
    }

    fun repeatRecursive() {
        println(this.name)
        repeatRecursive()
    }
}