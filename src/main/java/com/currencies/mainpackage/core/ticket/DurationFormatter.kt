package com.currencies.mainpackage.core.ticket

import java.time.Duration
import org.springframework.stereotype.Component

@Component
class DurationFormatter {

    fun formatDuration(duration: Duration): String {
        val days = duration.toDays()
        val hours = duration.toHoursPart()
        val minutes = duration.toMinutesPart()
        val seconds = duration.toSecondsPart()

        val parts = mutableListOf<String>()

        if (days > 0) {
            val dayWord = when {
                days % 10 == 1L && days % 100 != 11L -> "день"  // Для 1, 21, 31 и так далее
                days % 10 in 2..4 && (days % 100 !in 12..14) -> "дня"  // Для 2-4, 22-24, 32-34 и так далее
                else -> "дней"  // Для всех остальных случаев, включая 5-20, 25-30, 35-40 и т.д.
            }
            parts.add("$days $dayWord")
        }

        if (hours > 0) {
            val hourWord = when {
                hours % 10 == 1 && hours % 100 != 11 -> "час"  // Для 1, 21 и так далее
                hours % 10 in 2..4 && (hours % 100 !in 12..14) -> "часа"  // Для 2-4, 22-24 и так далее
                else -> "часов"  // Для всех остальных случаев
            }
            parts.add("$hours $hourWord")
        }

        if (minutes > 0) {
            val minuteWord = when {
                minutes % 10 == 1 && minutes % 100 != 11 -> "минута"  // Для 1, 21, 31 и так далее
                minutes % 10 in 2..4 && (minutes % 100 !in 12..14) -> "минуты"  // Для 2-4, 22-24 и так далее
                else -> "минут"  // Для всех остальных случаев
            }
            parts.add("$minutes $minuteWord")
        }

//        if (seconds > 0) {
//            val secondWord = when {
//                seconds % 10 == 1 && seconds % 100 != 11 -> "секунда"  // Для 1, 21, 31 и так далее
//                seconds % 10 in 2..4 && (seconds % 100 !in 12..14) -> "секунды"  // Для 2-4, 22-24 и так далее
//                else -> "секунд"  // Для всех остальных случаев
//            }
//            parts.add("$seconds $secondWord")
//        }

        return parts.joinToString(" ")  // Соединяем все части через пробел
    }

}