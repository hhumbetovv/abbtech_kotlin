package lesson_1

fun main() {
    val calc = Calculator(store = 50)
    calc.runCLI()
}

/**
 * A minimalistic calculator app
 *
 * @author by Humbet Humbetov from ABB Tech Academy
 *
 * @property store property for saving results
 * @property runCLI function for using minimalistic CLI
 *
 * and other functionalities
 * @sample sum
 *
 */
class Calculator(store: Number = 0) {
    /**
    * This value used for storing results and calling later
    *
    * @sample sum
    * */
    var store: Number = store
        get() = field
        private set(value) {
            field = value
        }

    private val options: Array<String> = arrayOf(
        "1. Sum Digits",
        "2. Subtract Digits",
        "3. Multiply Digits",
        "4. Divide Digits",
        "5. Exit"
    )

    /**
     * CLI Entry Point
     *
     * using recursion, this function can perform calculations
     * on list of numbers according to the given [options]
     *
     * @see executeCLIOption
     * @see iterator
     * @see convert
     * */

    fun runCLI() {
        println("List of options:")
        options.forEach { option -> println(option) }
        val selectedOption: String? = readlnOrNull()?.filter{it.isDigit()}
        if(checkOption(selectedOption, options.size)) {
            executeCLIOption(selectedOption!!)
        } else {
            printOptionError()
            runCLI()
        }
    }


    /**
     * [executeCLIOption] The core part of the CLI, this method runs an [Operator] on the list of [Number]
     * it will receive according to the option entered, or it just outputs it
     *
     * @throws Throwable if an unexpected error occurs during execution.
     */
    private fun executeCLIOption(option: String) {
        // CLI stop option
        if(option == "5") {
            println("CLI stoped")
            return;
        }
        // Getting Numbers from User
        println("Please enter the numbers | numbers should be separated by space")
        val userInput: String? = readlnOrNull()?.replace(
            "\\s+".toRegex(), " " // Removes Duplicate Spaces
        )
        if(checkUserInput(userInput)) {
            // Convert Numbers
            val numbers: Array<Number> = userInput!!
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toDouble() }
                .toTypedArray()

            // Calculate according to option and get result
            val result = when(option) {
                "1" -> sumAll(*numbers)
                "2" -> subtractAll(*numbers)
                "3" -> multiplyAll(*numbers)
                "4" -> divideAll(*numbers)
                else -> Throwable("Some Error Ocurred")
            }
            println("Result - $result")
            // Use Recursion to restart CLI
            runCLI()
        } else {
            printInputError()
            executeCLIOption(option)
        }
    }

    /**
     * Unlike the other one ([checkOption]), this function only checks if it is empty
     * @see checkOption
     * @see executeCLIOption
     * */
    private fun checkUserInput(input: String?): Boolean {
        val filteredInput = input?.filter { it.isDigit() }
        return !filteredInput.isNullOrBlank()
    }

    /**
     * @param optionCount Max number of options
     * @see checkUserInput
     * @return False when the option given as am option is empty, or its number does not match the range
     * */
    private fun checkOption(option: String?, optionCount: Int): Boolean {
        if(option.isNullOrBlank()) return false
        if(option.toInt() == 0) return false
        return option.toInt() <= optionCount
    }

    /**
     * Use when the given option is not correct
     * @see runCLI
     * */
    private fun printOptionError() {
        println("Incorrect Option, Please try again...")
    }

    /**
     * Use when the given user input is not valid
     * @see executeCLIOption
     * */
    private fun printInputError() {
        println("Something is not correct with this input, Please try again...")
    }

    /**
     * This function converts a given [num] value to a minimal sized numerical type.
     *
     * @param num The number to be converted.
     * @return The value converted to the appropriate numerical type.
     * @sample sum
     */
    private fun convert(num: Double): Number {
        if (num % 1 != 0.0) return num
        val value = num.toInt()
        if (num >= -128 && num <= 127) return value.toByte()
        if (num >= -32768 && num <= 32767) return value.toShort()
        return value
    }

    // Primitive Math functions

    /**
     * a function that takes two parameters of the desired number type and adds them
     * @param store that decides whether result should be saved in memory or not
     * @see convert
     * */
    fun sum(a: Number, b: Number, store: Boolean = false): Number {
        val result = convert(a.toDouble() + b.toDouble())
        if (store) this.store = result
        return result
    }

    /**
     * a function that takes two parameters of the desired number type and subtracts them
     * @param store that decides whether result should be saved in memory or not
     * @see convert
     * */
    fun subtract(a: Number, b: Number, store: Boolean = false): Number {
        val result = convert(a.toDouble() - b.toDouble())
        if (store) this.store = result
        return result
    }

    /**
     * a function that takes two parameters of the desired number type and multiplies them
     * @param store that decides whether result should be saved in memory or not
     * @see convert
     * */
    fun multiply(a: Number, b: Number, store: Boolean = false): Number {
        val result = convert(a.toDouble() * b.toDouble())
        if (store) this.store = result
        return result
    }

    /**
     * a function that takes two parameters of the desired number type and divides them
     * @param store that decides whether result should be saved in memory or not
     * @see convert
     * */
    fun div(a: Number, b: Number, store: Boolean = false): Number {
        val result = convert(a.toDouble() / b.toDouble())
        if (store) this.store = result
        return result
    }

    // Functions, which operating on list of numbers

    /**
     * A function that using [iterator] and [Operator] to sum Numbers
     * @param params A bunch of numbers
     * @param store that decides whether result should be saved in memory or not
     * */
    fun sumAll(vararg params: Number, store: Boolean = false): Number {
        val result: Number = iterator(params, Operator.sum)
        if (store) this.store = result
        return result
    }

    /**
     * A function that using [iterator] and [Operator] to subtract Numbers
     * @param params A bunch of numbers
     * @param store that decides whether result should be saved in memory or not
     * */
    fun subtractAll(vararg params: Number, store: Boolean = false): Number {
        val result: Number = iterator(params, Operator.subtract)
        if (store) this.store = result
        return result
    }

    /**
     * A function that using [iterator] and [Operator] to multiply Numbers
     * @param params A bunch of numbers
     * @param store that decides whether result should be saved in memory or not
     * */
    fun multiplyAll(vararg params: Number, store: Boolean = false): Number {
        val result: Number = iterator(params, Operator.multiply)
        if (store) this.store = result
        return result
    }

    /**
     * A function that using [iterator] and [Operator] to divide Numbers
     * @param params A bunch of numbers
     * @param store that decides whether result should be saved in memory or not
     * */
    fun divideAll(vararg params: Number, store: Boolean = false): Number {
        val result: Number = iterator(params, Operator.divide)
        if (store) this.store = result
        return result
    }

    /**
     * [iterator] core function to perform [Operator] methods on a list of [Number]
     *
     * */
    private fun iterator(
        params: Array<out Number>, operator: Operator
    ): Number {
        var value: Double = when(operator) {
            Operator.multiply -> 1.0
            Operator.divide -> 1.0
            else -> 0.0
        }
        for(num in params) {
            value = operator.func(value, num.toDouble())
        }
        val result: Number = convert(value)
        return result
    }
}
/**
 * An enum class that includes
 * addition, subtraction, multiplication and division functions
 * */
enum class Operator(val func: (Double, Double) -> Double) {
    sum({a, b -> a + b}),
    subtract({a, b -> a - b}),
    multiply({a, b -> a * b}),
    divide({a, b -> a / b}),
}