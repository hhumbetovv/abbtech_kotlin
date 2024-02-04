package lesson_2

fun main() {
    printFibonacciNumbers(99)
}

fun printFibonacciNumbers(count: Int) {
    var prev: Long = 0
    var curr: Long = 1
    var i = 3
    println("1. 0")
    println("2. 1")
    while(i <= count) {
        val next: Long = prev + curr
        prev = curr
        curr = next
        println("$i. $next")
        i++
    }
}