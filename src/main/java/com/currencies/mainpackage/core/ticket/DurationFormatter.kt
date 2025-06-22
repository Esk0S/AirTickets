package com.currencies.mainpackage.core.ticket

import java.time.Duration
import org.springframework.stereotype.Component

@Component
class DurationFormatter {

    fun formatDuration(duration: Duration): String {
        val days = duration.toDays()
        val hours = duration.toHoursPart()
        val minutes = duration.toMinutesPart()

        val parts = mutableListOf<String>()

        if (days > 0) {
            val dayWord = when {
                days % 10 == 1L && days % 100 != 11L -> "день"
                days % 10 in 2..4 && (days % 100 !in 12..14) -> "дня"
                else -> "дней"
            }
            parts.add("$days $dayWord")
        }

        if (hours > 0) {
            val hourWord = when {
                hours % 10 == 1 && hours % 100 != 11 -> "час"
                hours % 10 in 2..4 && (hours % 100 !in 12..14) -> "часа"
                else -> "часов"
            }
            parts.add("$hours $hourWord")
        }

        if (minutes > 0) {
            val minuteWord = when {
                minutes % 10 == 1 && minutes % 100 != 11 -> "минута"
                minutes % 10 in 2..4 && (minutes % 100 !in 12..14) -> "минуты"
                else -> "минут"
            }
            parts.add("$minutes $minuteWord")
        }


        return parts.joinToString(" ")
    }

}