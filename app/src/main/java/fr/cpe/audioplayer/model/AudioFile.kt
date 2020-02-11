package fr.cpe.audioplayer.model

import java.util.*

data class AudioFile(
    val title: String,
    val filePath: String,
    val artist: String,
    val album: String,
    val genre: String,
    val year: Int,
    val duration: Int
) {
    val durationText: String
        get() {
            val seconds = duration % 60
            val durationInMinutes = (duration - seconds) / 60
            val minutes = durationInMinutes % 60
            val hours = (durationInMinutes - minutes) / 60

            if (hours > 0) {
                return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
            }

            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
}