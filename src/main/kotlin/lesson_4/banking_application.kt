package lesson_4

fun main() {
    val bankingApp = BankingApplication()

    bankingApp.addCustomers()

    bankingApp.printAllCustomers()
}

data class BankingApplication(
    val customers: ArrayList<Customer> = ArrayList()
) {

    fun printAllCustomers() {
        customers.forEach { customer -> println(customer) }
    }

    fun addCustomers() {
        var index = 1
        while(true) {
            println("<--- Customer $index. --->")
            val customer: Customer? = getCustomerInfo()
            if(customer == null) {
                println("<--- STOPPED --->")
                break
            }
            customers.add(customer)
            index++
        }
    }

    private fun getCustomerInfo(): Customer? {
        val name: String = getInputValue("Customer Name")
        if(name.lowercase() == "stop") return null

        val surname: String = getInputValue("Customer Surname")
        if(surname.lowercase() == "stop") return null

        val balance: String = getInputValue(label = "Customer Balance", expNumber = true)
        if(balance.lowercase() == "stop") return null

        return Customer(name,surname, balance.toDouble())
    }

    private fun getInputValue(label: String, expNumber: Boolean = false): String {
        print("Enter $label: ")
        val value: String? = readLine()?.trim()

        // If value is empty or null, throw Error Message
        if(value.isNullOrEmpty()) {
            println("<!!! ${label.uppercase()} CANT BE EMPTY !!!>")
            return getInputValue(label, expNumber)
        }

        // if the expected value is a number and the current value is not
        // then throw Error Message
        if(expNumber && !value.matches("-?\\d+(\\.\\d+)?".toRegex())) {
            println("<!!! ${label.uppercase()} MUST BE NUMBER")
            return getInputValue(label, true)
        }
        return value
    }
}

data class Customer(
    val name: String,
    val surname: String,
    var balance: Double?,
    val id: Int = getId()
) {
    companion object {
        private var id: Int = 0

        private fun getId(): Int {
            return id++
        }
    }
}