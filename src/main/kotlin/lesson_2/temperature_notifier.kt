package lesson_2

fun main() {
    temperatureNotifier(-51)
    temperatureNotifier(15)
    temperatureNotifier(41)
}

fun temperatureNotifier(temp: Int) {
    val status: String = when {
        temp <= -40 -> "Freezing"
        temp in -41 .. -20 -> "Very Cold"
        temp in -21 .. 0 -> "Cold"
        temp in 1 .. 20 -> "Mild"
        temp in 21 .. 40 -> "Warm"
        else -> "Dangerous"
    }
    println(status)
}