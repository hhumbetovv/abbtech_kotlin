package lesson_6

import kotlin.math.abs

fun main() {
    val numbers = listOf(1,2,3,4,5,6,7,8,9,10)
    println(numbers / 3)
}

operator fun <T> List<T>.div(count: Int): ArrayList<List<T>> {
    if(count == 0) return arrayListOf(this)
    var fromIndex = 0
    val step = (this.size + abs(count) - 1) / abs(count)

    println("step: $step")

    val arrayList = arrayListOf<List<T>>()

    while(fromIndex < this.size) {
       val toIndex = minOf(fromIndex + step, this.size)

        arrayList.add(this.subList(fromIndex, toIndex))

        fromIndex = toIndex
    }

    while(arrayList.size != count) {
        val subArray = arrayList.last { sub -> sub.size != 1 }
        val divided = subArray / 2
        val index = arrayList.lastIndexOf(subArray)
        arrayList.remove(subArray)
        arrayList.addAll(index, divided)
    }

    return arrayList
}