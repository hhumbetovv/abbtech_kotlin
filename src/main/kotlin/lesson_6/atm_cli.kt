package lesson_6

import kotlin.random.Random

fun main() {
    val atm = ATM(
        listOf(
            Card(20.0, Currency.AZN, "0505005050", 5020),
            Card(0.0, Currency.EUR, "0777777777", 2456),
            Card(300.0, Currency.USD, "0555555555", 1234),
            Card(250.0, Currency.RUB, "0707007070", 9876),
            Card(13.0, Currency.AZN, "0515105151", 2457),
            Card(90.0, Currency.EUR, "0705007050", 3333),
            Card(0.0, Currency.USD, "0999999999", 4444),
            Card(45.0, Currency.RUB, "0909009090", 5555),
        )
    )

    atm.launchMenu()
}

data class ATM(
    private val database: List<Card>
) {
    private var selectedCard: Card? = null

    init {
        // Check pin and number duplicate
        database.forEachIndexed { index, current ->
            val dupIndex = database.indexOfLast { card ->
                card.checkCardDuplicate(current)
            }
            if (dupIndex != index) {
                throw Exception(
                    "Duplicate Pin or Number Detected at: $index & $dupIndex"
                )
            }
        }
    }

    // Options
    private val initialOptions = listOf(
        "1. Withdraw",
        "2. Check Balance",
        "3. Increase Balance",
        "4. Cash By Code",
        "5. Exit",
    )

    private val options = listOf(
        "1. Withdraw",
        "2. Check Balance",
        "3. Increase Balance",
        "4. Cash By Code",
        "5. Restart",
        "6. Exit",
    )

    private val cashByCodeOptions = listOf(
        "1. Send Amount",
        "2. Receive Amount",
        "3. Return the main menu"
    )

    // Input Checkers
    private fun checkAmount(value: String): String? {
        if (value.isEmpty()) return "Amount cannot be empty"
        if (value.toDouble() < 0) return "Amount cannot be negative"
        return null
    }

    private val amountChecker: (String) -> String? = { checkAmount(it) }

    private fun checkPin(value: String): String? {
        if (value.all { char -> !char.isDigit() }) return "Entered pin code bad formatted"
        if (value.length != 4) return "Pin code must be 4 digits"
        return null
    }

    private fun checkCardPin(value: String): String? {
        val err = checkPin(value)
        if(err != null) return err
        val cardExists = database.any { it.checkPinCode(value.toShort()) }
        if(!cardExists) return "There are no accounts for this pin"
        return null
    }
    private val cardPinChecker: (String) -> String? = {checkCardPin(it)}


    // CLI base function
    fun launchMenu() {
        val menuOptions = if (selectedCard == null) initialOptions else options
        val exitNumber = if (selectedCard == null) 5 else 6
        selectOption(menuOptions) { action ->
            if (action != exitNumber) {
                getCard {
                    when (action) {
                        1 -> withdraw()
                        2 -> checkBalance()
                        3 -> increaseBalance()
                        4 -> cashByCode()
                        5 -> if (selectedCard != null) restart()
                    }
                }
            } else {
                println("<--- CLI Stopping --->")
                selectedCard = null
            }
        }
    }

    // options
    private fun withdraw() {
        userInput("withdraw value", amountChecker) { amount ->
            selectedCard?.withdraw(amount.toDouble())
            launchMenu()
        }
    }

    private fun checkBalance() {
        print("Your current balance is: ${selectedCard?.getBalance()}\n")
        launchMenu()
    }

    private fun increaseBalance() {
        userInput("amount to increase", amountChecker) { amount ->
            selectedCard?.increaseBalance(amount.toDouble())
            launchMenu()
        }
    }

    private fun cashByCode() {
        selectedCard?.run {
            selectOption(cashByCodeOptions) { option ->
                when (option) {
                    1 -> sendAmount(this)
                    2 -> receiveAmount(this)
                    3 -> launchMenu()
                }
            }
        }
    }

    private fun sendAmount(card: Card) {
        fun checkNumber(value: String): String? {
            if(card.checkNumber(value)) return "You can't send amount yourself"
            val cardExists = database.any { it.checkNumber(value) }
            if(!cardExists) return "No account registered to this number"
            return null
        }

        val numberChecker: (String) -> String? = { checkNumber(it) }

        userInput("receiver number", numberChecker) { number ->
            val receiver = database.single { it.checkNumber(number) }
            userInput("amount to send", amountChecker) { amount ->
                val senderPin = Random.nextInt(1000, 10000).toShort()
                println("<--- The first pin given to you: $senderPin")
                receiver.receiveAmountRequest(senderPin, changeCurrency(
                    card.getCurrency(), receiver.getCurrency(), amount.toDouble()
                ))
                println("<--- Shipping successful")
                println("<--- $amount ${card.getCurrency().label} amount sent")
            }
        }
        cashByCode()
    }

    private fun receiveAmount(card: Card) {
        fun checkReceiverPin(pin: String): String? {
            val err = checkPin(pin)
            if(err != null) return err
            if(!card.checkReceiverPin(pin.toShort())) return "Sms pin is invalid"
            return null
        }
        val receiverPinChecker: (String) -> String? = { checkReceiverPin(it) }

        fun checkSenderPin(pin: String): String? {
            val err = checkPin(pin)
            if(err != null) return err
            if(!card.checkSenderPin(pin.toShort())) return "Sender pin is invalid"
            return null
        }
        val senderPinChecker: (String) -> String? = { checkSenderPin(it) }

        if(card.checkReceivedRequests()) {
            userInput("sms pin", receiverPinChecker) { receiverPin ->
                userInput("sender pin", senderPinChecker) { senderPin ->
                    val isSuccess = card.getReceivedAmount(
                        receiverPin.toShort(),
                        senderPin.toShort()
                    )
                    if(isSuccess) {
                        cashByCode()
                    } else {
                        receiveAmount(card)
                    }
                }
            }
        } else {
            cashByCode()
        }
    }

    private fun restart() {
        println("<--- Restarting --->")
        selectedCard = null
        launchMenu()
    }

    private fun selectOption(options: List<String>, handleOption: (Int) -> Unit) {
        println("<--- Select an option: --->")
        options.forEach { option -> println(option) }
        val selectedOption = readln().filter { it.isDigit() }
        val optionNumber = if (selectedOption.isNotEmpty()) {
            selectedOption.toInt()
        } else {
            0
        }
        if (optionNumber !in 1..options.size) {
            println("<!-- Invalid option number")
            selectOption(options, handleOption)
        } else {
            handleOption.invoke(optionNumber)
        }
    }

    private fun getCard(handleCard: () -> Unit) {
        if (selectedCard != null) {
            handleCard.invoke()
            return
        }
        userInput("the pin code", cardPinChecker) { pinCode ->
            val card = database.single { card ->
                card.checkPinCode(pinCode.toShort())
            }
            selectedCard = card
            handleCard.invoke()
        }
    }
}

fun userInput(
    label: String,
    checker: (String) -> String?,
    valueHandler: (String) -> Unit
) {
    print("<--- Enter the $label: ")
    val inputValue = readln().filter { it.isDigit() }
    if (inputValue.lowercase().trim() == "exit") return
    val err = checker.invoke(inputValue)
    if (err != null) {
        println("<!-- $err")
        userInput(label, checker, valueHandler)
    } else {
        valueHandler.invoke(inputValue)
    }
}

data class Card(
    private var balance: Double,
    private val currency: Currency,
    private val phoneNumber: String,
    private var pinCode: Short,
) {
    private val requests = mutableListOf<AmountRequest>()

    init {
        if (balance < 0) balance = 0.0
    }

    fun checkPinCode(pinCode: Short): Boolean {
        return this.pinCode == pinCode
    }

    fun checkCardDuplicate(card: Card): Boolean {
        val pinStatement = this.pinCode == card.pinCode
        val phoneStatement = this.phoneNumber == card.phoneNumber
        return pinStatement || phoneStatement
    }

    fun withdraw(value: Double) {
        if (value > balance) {
            println("<!-- Amount exceeds account balance")
        } else {
            balance -= value
            println("$value ${currency.label} amount withdrawn...")
            println("<--- Remaining amount: $balance ${currency.label}")
        }
    }

    fun getBalance(): String {
        return "$balance $currency"
    }

    fun increaseBalance(value: Double) {
        balance += value
        println("<--- Amount increased by $value ${currency.label}")
        println("<--- Current amount: $balance ${currency.label}")
    }

    fun checkNumber(value: String): Boolean {
        return value == phoneNumber.filter { it.isDigit() }
    }

    fun receiveAmountRequest(senderPin: Short, amount: Double) {
        var receiverPin = Random.nextInt(1000, 10000).toShort()
        while(requests.any { it.receiverPin == receiverPin }) {
            receiverPin = Random.nextInt(1000, 10000).toShort()
        }
        requests.add(AmountRequest(senderPin, receiverPin, amount))
    }

    fun checkReceivedRequests(): Boolean {
        println("You have ${requests.size} requests")
        requests.forEach { request ->
            println(request.receiverPin)
        }
        return requests.isNotEmpty()
    }

    fun checkReceiverPin(receiverPin: Short): Boolean {
        return requests.any { it.receiverPin == receiverPin }
    }

    fun checkSenderPin(senderPin: Short): Boolean {
        return requests.any {it.senderPin == senderPin}
    }

    fun getReceivedAmount(receiverPin: Short, senderPin: Short): Boolean {
        val request = requests.singleOrNull {request ->
            val isEqualSenderPin = request.senderPin == senderPin
            val isEqualReceiverPin = request.receiverPin == receiverPin
            isEqualReceiverPin && isEqualSenderPin
        }
        if(request == null) return false
        println("${request.amount} ${currency.label} was received")
        requests.remove(request)
        return true
    }

    fun getCurrency(): Currency {
        return currency
    }
}

data class AmountRequest(
    val senderPin: Short,
    val receiverPin: Short,
    val amount: Double
)

enum class Currency(val value: Double, val label: String) {
    AZN(1.7, "Manat"),
    EUR(0.93, "Euro"),
    USD(1.0, "Dollar"),
    RUB(92.50, "Rubl")
}

fun changeCurrency(from: Currency, to: Currency, amount: Double): Double {
    val result = amount * to.value / from.value
    return Math.round(result * 1000.0) / 1000.0
}