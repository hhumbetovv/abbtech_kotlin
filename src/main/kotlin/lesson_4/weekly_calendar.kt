package lesson_4

fun main() {
    Days.entries.forEach {day ->
        getActivity(day).let { ac ->
            println(ac)
            println(ac.interval)
        }
    }
}

fun getActivity(day: Days): WeeklyActivity {
    return when(day) {
        Days.MONDAY -> WeeklyActivity.WorkAndGym("10:00 - 17:00", "18:00 - 20:00")
        Days.THURSDAY -> WeeklyActivity.WorkAndGym("10:00 - 18:00", "19:00 - 21:00")
        Days.TUESDAY, Days.WEDNESDAY -> WeeklyActivity.Work("10:00 - 18:00")
        Days.FRIDAY -> WeeklyActivity.WorkAndWorship()
        else -> WeeklyActivity.Rest()
    }
}

sealed class WeeklyActivity(val interval: String) {

    data class Work(val workInterval: String) : WeeklyActivity(workInterval)

    data class WorkAndGym(
        val workInterval: String,
        val gymInterval: String
    ) : WeeklyActivity(
        "work: $workInterval\ngym: $gymInterval"
    )

    data class WorkAndWorship(
        val workInterval: String = "10:00 - 14:00",
        val worshipInterval: String = "15:00 - 17:00"
    ) : WeeklyActivity(
        "work: $workInterval\nworship: $worshipInterval"
    )

    class Rest(val restInterval: String = "All Day") : WeeklyActivity(restInterval)
}

enum class Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}