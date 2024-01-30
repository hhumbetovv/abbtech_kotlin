package lesson_2

fun main() {
    printFibonacciNumbers(10)
}

fun printFibonacciNumbers(count: Int) {
    var prev = 0
    var curr = 1
    var i = 3
    println("1. 0")
    println("2. 1")
    while(i <= count) {
        val next: Int = prev + curr
        prev = curr
        curr = next
        println("$i. $next")
        i++
    }
}