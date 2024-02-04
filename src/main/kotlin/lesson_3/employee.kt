package lesson_3

fun main() {
    val developer: Developer = Developer(
        "Software Developer",
        1500.0,
        "Front-end Developer",
        "Single"
    )

    // public - employee name
    println(developer.getName())

    // internal - employee information available in the same module
    println(developer.workerDetails())

    // protected - comparison method via a method derived from a parent class
    println(developer.isTheSameRole("Back-end"))

    // private - a private method of parent class,
    // which only can be called with the public method of parent class
    println(developer.isItPossibleToBuy(2500.0))
}

open class Employee(
    private val name: String,
    private var salary: Double,
    private var roleDetails: String,
    private var workerDetails: String
) {
    /*
    * The name of an employee can be known even by someone
    * who does not work for the company, so it should be public
    * */
    fun getName(): String {
        return name
    }

    /*
    * As a matter of company policy,
    * no employee should know the salary of another employee
    * */
    private fun calculateSalary(): Double {
        return salary
    }

    /*
    * only people with the same type of
    * profession can know the details of the main profession
    * */
    protected fun getRoleDetails(): String {
        return roleDetails
    }

    /*
    * only other members within the company
    * can see detailed information about the employee,
    * especially HR
    * */
    internal fun workerDetails(): String {
        return workerDetails
    }

    /*
    * A public method to use private method
    * */
    fun isItPossibleToBuy(productValue: Double): Boolean {
        return calculateSalary() > productValue
    }
}

data class Developer(
    private val name: String,
    private val salary: Double,
    private val roleDetails: String,
    private val workerDetails: String,
): Employee(name, salary, roleDetails, workerDetails) {

    fun isTheSameRole(roleDetails: String): Boolean {
        return super.getRoleDetails() == roleDetails
    }
}