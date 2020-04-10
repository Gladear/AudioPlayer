package fr.cpe.audioplayer.utils

import java.util.*

object TimeFormatter {
    fun formatDuration(millis: Int): String {
        val duration = millis / 1000
        val seconds = duration % 60
        val durationInMinutes = duration / 60
        val minutes = durationInMinutes % 60
        val hours = durationInMinutes / 60

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        }

        return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)
    }
}