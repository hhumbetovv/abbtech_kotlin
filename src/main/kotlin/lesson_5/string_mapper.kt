package lesson_5

fun main() {
    mapper("aa,bb,cc")
}

fun mapper(input: String, pattern: String? = null): Map<Int, String> {
    // Split string with pattern
    val list = input.split(pattern ?: ",")
    val map = mutableMapOf<Int, String>()

    // Get Entries from list and add the map
    list.forEachIndexed { index, el ->
        map[index + 1] = el
    }

    println(map.toStr())

    return map
}

fun <K, T> Map<K, T>.toStr(): String {
    val list = map { (key, value) ->
        "$key to $value"
    }
    return list.joinToString(", ")
}