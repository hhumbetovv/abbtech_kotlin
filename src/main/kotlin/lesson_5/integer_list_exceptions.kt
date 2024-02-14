package lesson_5

fun main() {
    val numbers = listOf(1,2,3,4,-5, 13,-20, -15)

    try {
        checkNumbers(numbers)
        var sum = 0
        numbers.forEach { number ->
            sum += number
        }
        println("Sum of Number Set: $sum")

    } catch (e: DuplicatedIntegers) {
        println(e.message)
        print("Duplicate numbers: ")
        print(e.duplicates.joinToString(", "))

    } catch (e: NegativeIntegers) {
        println(e.message)
        print("You must delete the negatives in the given positions: ")
        e.indexes.forEach {index ->
            print("${index + 1} ")
        }
    }
}

fun checkNumbers(list: List<Int>) {
    // Indexes of negative numbers
    val indexes = ArrayList<Int>()
    val duplicates = ArrayList<Int>()

    list.forEach { number ->
        // if the number is negative, take its position and store
        if(number < 0) indexes.add(list.indexOf(number))

        // if from beginning to end and the end to beginning first positions
        // of a digit are different, this is a copy of the
        // same digit in two different places
        val hasDuplicate = list.indexOf(number) != list.lastIndexOf(number)

        // if the number has duplicate and not stored, then store it
        if(hasDuplicate && !duplicates.contains(number)) duplicates.add(number)
    }

    if(duplicates.size != 0) throw DuplicatedIntegers(duplicates)
    if(indexes.size != 0) throw NegativeIntegers(indexes)
}

class DuplicatedIntegers(
    val duplicates: List<Int>
) : Exception("The list must not contain duplicate digits")

class NegativeIntegers(
    val indexes: List<Int>
) : Exception("There should be no negative numbers in the list")
