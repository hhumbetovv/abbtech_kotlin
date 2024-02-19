package lesson_6

import lesson_5.TripleEntry
import lesson_5.TripleMap
import lesson_5.tripleMapOf

fun main() {
    val entry = TripleEntry("Hi", false, 20)

    entry.mapper { map ->
        map.forEach { entry ->
            println(entry.first)
        }
        map.removeByThird(50)
    }
}

fun <T,R,K> TripleEntry<T,R,K>.mapper(
    func: (tripleMap: TripleMap<T,R,K>) -> Unit
) {
    val map = tripleMapOf(this)
    return func.invoke(map)
}