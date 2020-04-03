package fr.cpe.audioplayer.model

import java.util.*

data class AudioFile(
    val filePath: String,
    val title: String,
    val artist: String,
    var album: String,
    var duration: Int = 0,
    var genre: String = ""

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