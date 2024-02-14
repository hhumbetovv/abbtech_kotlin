package lesson_5

fun main() {
    val employees = listOf(
        Employee(finNumber = 543, isWorking = false),
        Employee(name = "Employee", isWorking = true),
        Employee(name = "Employee", finNumber = 785, isWorking = false),
        Employee(isWorking = true),
        Employee(finNumber = 754, isWorking = true),
        Employee(name = "Employee", isWorking = false),
        Employee(isWorking = true),
        Employee(name = "Employee", finNumber = 675, isWorking = false),
    )

    val workingEmployees = employees.filter { employee ->
        employee.getIsWorking()
    }

    println("<--- Database Info Updating --->")
    workingEmployees.forEach { employee ->
        employee.updateEmployeeInfo()
    }

    println("<--- Updated Database --->")
    workingEmployees.forEach { println(it) }
}

data class Employee(
    private var name: String? = null,
    private var finNumber: Int? = null,
    private var isWorking: Boolean = false,
) {
    fun getName(): String? {
        return name
    }

    fun getFinNumber(): Int? {
        return finNumber
    }

    fun getIsWorking(): Boolean {
        return isWorking
    }

    fun updateEmployeeInfo() {
        if(name == null) {
            name = getInputData("Name")
        }
        println("Employee name: $name - checked")
        if(finNumber == null) {
            finNumber = getInputData("finNumber", true).toInt()
        }
        println("Employee fin number: $finNumber - checked")
    }
}

fun getInputData(label: String, isNumeric: Boolean = false): String {
    print("Enter Employee $label: ")
    val inputData = readLine()
    if(inputData.isNullOrEmpty()) {
        println("$label can't be empty")
        return getInputData(label)
    }
    if(isNumeric && !inputData.matches("-?\\d+(\\.\\d+)?".toRegex())) {
        println("$label must be number")
        return getInputData(label, true)
    }
    return inputData
}