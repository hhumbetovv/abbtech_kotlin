package lesson_6

fun main() {
    fetchUser<Any>(handler = resultHandler)
    fetchUser ("User One", resultHandler)
    fetchUser (20, resultHandler)
}

inline fun <reified T> fetchUser(data: T? = null, handler: (CallStatus) -> Unit) {
    when {
        data is String && data.isNotEmpty() -> {
            handler.invoke(CallStatus.Success(mapOf("userInfo" to data)))
        }
        data is Int -> handler.invoke(CallStatus.Success(mapOf("userId" to data)))
        data == null -> handler.invoke(CallStatus.Error("data is null"))
        else -> handler.invoke(CallStatus.Error("Invalid data type"))
    }
}

val resultHandler: (status: CallStatus) -> Unit = { status ->
    when(status) {
        is CallStatus.Success<*> -> println("Result Data: ${status.data}")
        is CallStatus.Error -> println("Error: ${status.message}")
    }
}

sealed class CallStatus {
    data class Success<T>(val data: T): CallStatus()
    data class Error(val message: String): CallStatus()
}