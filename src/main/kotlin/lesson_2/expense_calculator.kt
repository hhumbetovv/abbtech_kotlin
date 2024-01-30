package lesson_2

fun main() {
    println(expenseCalculator(200.0))
    println(expenseCalculator(300.0))
    println(expenseCalculator(400.0))
    println(expenseCalculator(534.0))
}

fun expenseCalculator(expendedPower: Double): String {
    var cost: Double = 0.0
    if(expendedPower < 200) {
        cost = expendedPower * 8
    } else if(expendedPower < 300) {
        cost = 1600 + (expendedPower - 200) * 9
    } else {
        cost = 2500 + (expendedPower - 300) * 13
    }
    return "Your Azeriisiq cost is - ${cost / 100} AZN"
}