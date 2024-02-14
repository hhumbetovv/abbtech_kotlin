package lesson_5

fun main() {
    val map: TripleMap<String, Int, Boolean> = tripleMapOf(
        "Hi" addTo 20 addTo false,
        "Yes".addTo(30).addTo(true),
        "Natig".addTo(40).addTo(true),
    )

    map.add(TripleEntry("No", 50, false))

    println(map.size)

//    map.removeAt(0)
//    map.removeByFirst("No")
//    map.removeBySecond("30")
//    map.removeByThird(false)

    map.forEach { (first, _, _) ->
        println(first)
    }

    val filteredMap = map.filter { (_,second,_) -> second > 30 }

    println(filteredMap)
}

// Public function for Triple Map
fun <T,R,K> tripleMapOf(
    vararg entries: TripleEntry<T,R,K>
): TripleMap<T,R,K> {
    return TripleMap(
        list = entries.toMutableList()
    )
}

// Map and Entry Models
data class TripleMap<T,R,K>(
    private val list: MutableList<TripleEntry<T,R,K>> = mutableListOf()
) {
    val size: Int
        get() = list.size

    fun add(entry: TripleEntry<T,R,K>) {
        list.add(entry)
    }

    fun removeAt(index: Int) {
        list.removeAt(index)
    }

    fun removeByFirst(first: T) {
        list.removeIf { entry -> entry.first == first }
    }

    fun removeBySecond(second: R) {
        list.removeIf { entry -> entry.second == second }
    }

    fun removeByThird(third: K) {
        list.removeIf { entry -> entry.third == third }
    }

    fun forEach(predicate: (TripleEntry<T,R,K>) -> Unit) {
        for (entry in list) {
            predicate(entry)
        }
    }

    fun filter(predicate: (TripleEntry<T, R, K>) -> Boolean): TripleMap<T,R,K> {
        val filteredList = mutableListOf<TripleEntry<T,R,K>>()
        for (entry in list) {
            if(predicate(entry)) {
                filteredList.add(entry)
            }
        }
        return TripleMap(filteredList)
    }
}

data class TripleEntry<T,R,K>(
    val first: T,
    val second: R,
    val third: K,
)

// Infix Functions to create Pair and TripleEntries
infix fun <T, R> T.addTo(second: R): Pair<T, R> {
    return Pair(this, second)
}

infix fun <T,R,K> Pair<T, R>.addTo(third: K): TripleEntry<T,R,K> {
    return TripleEntry(this.first, this.second, third)
}